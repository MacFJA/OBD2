/*
  Copyright (c) 2018 MacFJA

  Permission is hereby granted, free of charge,
  to any person obtaining a copy of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge,
  publish, distribute, sublicense, and/or sell copies of the Software,
  and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
  Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.macfja.obd2.elm327;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.elm327.command.BooleanCommand;
import io.github.macfja.obd2.elm327.response.NoDataResponse;
import io.github.macfja.obd2.elm327.response.ResponseOK;
import io.github.macfja.obd2.exception.ExceptionResponse;
import io.github.macfja.obd2.exception.UnexpectedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The commander (send and receive) to work with Elm327.
 *
 * @author MacFJA
 */
public class Commander extends io.github.macfja.obd2.Commander {
    private final Map<String, Boolean> outputInfo = new HashMap<>();
    private String currentRequest;
    private Command currentCommand;
    private String lastRequest;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Commander() {
        super();
        initState(Arrays.asList(
                BooleanCommand.EchoOn,
                BooleanCommand.HeaderOff,
                BooleanCommand.LinefeedOff,
                BooleanCommand.MemoryOff,
                BooleanCommand.ResponseOn,
                BooleanCommand.SpaceOn,
                BooleanCommand.DLCVariableOff,
                BooleanCommand.AutomaticFormattingOn,
                BooleanCommand.FlowControlOn
        ));
    }

    /**
     * <p>Define the state of the ELM327 interface.</p>
     * <p>Can be used to restore the state of the ELM327 interface between session</p>
     *
     * @param states The list of option that define how ELM327 is configured
     */
    protected void initState(List<BooleanCommand> states) {
        for (BooleanCommand command : states) {
            putBooleanCommand(command);
        }
    }

    /**
     * Get the list of options ({@link BooleanCommand}) of the current ELM327
     *
     * @return List of options
     */
    public List<BooleanCommand> getState() {
        List<BooleanCommand> state = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : outputInfo.entrySet()) {
            state.add(BooleanCommand.getCommand(entry.getKey(), entry.getValue()));
        }

        return state;
    }

    /**
     * Register a {@link BooleanCommand} to reflect its action on command and response later
     *
     * @param command The command to save
     */
    protected void putBooleanCommand(BooleanCommand command) {
        outputInfo.put(command.getPrefix(), command.isOnVersion());
    }

    /**
     * {@inheritDoc}
     * <p>>Handle state, error response ({@link UnexpectedResponse}, {@link NoDataResponse})</p>
     */
    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException {
        if (command instanceof BooleanCommand) {
            putBooleanCommand((BooleanCommand) command);
        }
        currentRequest = command.getRequest();
        currentCommand = command;
        Response response;
        try {
            response = super.sendCommand(command);
        } catch (ExceptionResponse exceptionResponse) {
            logger.warn("The response is not a normal response: {}", exceptionResponse.getLocalizedMessage());
            response = exceptionResponse;
        }
        lastRequest = currentRequest;

        if (response instanceof ResponseOK && ((ResponseOK) response).isError()) {
            logger.warn("The command did not succeed: {}", response.getRawResult());
            response = new UnexpectedResponse(response.getRawResult());
        }

        if (new String(response.getRawResult()).equals("NO DATA") || new String(response.getRawResult()).equals("NODATA")) {
            logger.warn("The ELM327 return a 'NO DATA'");
            response = new NoDataResponse();
        }

        if (response instanceof ExceptionResponse) {
            logger.warn("The command wasn't successful, remove it from the persistent storage");
            unpersistCommand(command.getRequest());
        }

        return response;
    }

    protected boolean isCurrent(BooleanCommand command) {
        if (!outputInfo.containsKey(command.getPrefix())) {
            return false;
        }
        return outputInfo.get(command.getPrefix()).equals(command.isOnVersion());
    }

    @Override
    protected byte[] read() throws IOException, ExceptionResponse {
        byte[] raw = super.read();
        boolean dontFilter = currentCommand.getClass().getAnnotation(DontFilterResponse.class) != null;

        if (isCurrent(BooleanCommand.ResponseOff)) {
            return new byte[0];
        }

        String rawString = new String(raw);

        if (rawString.contains("NO DATA\r")) {
            throw new NoDataResponse();
        }

        if (isCurrent(BooleanCommand.HeaderOn)) {
            // Each line have the following format :
            // PP RR TT DD DD DD DD DD DD DD CC
            // With "PP"=Priority, "RR"=Receiver ID, "TT"=Transmitter ID, "DD"=Data and "CC"=Checksum
            Pattern pattern = Pattern.compile("^(?<message>(?<header>(?<priority>[a-f0-9]{2}) ?(?<receiver>[a-f0-9]{2}) ?(?<transmitter>[a-f0-9]{2})) ?(?<data>[a-f0-9 ]{2,})) ?(?<checksum>[a-f0-9]{2})$", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(rawString);

            StringBuilder newRawString = new StringBuilder();

            while (matcher.find()) {
                long data = Long.parseLong(matcher.group("message").replace(" ", ""), 16);
                long checksum = getBinaryChecksum(data, 8);
                if (checksum != Long.parseLong(matcher.group("checksum"))) {
                    throw new UnexpectedResponse(rawString.getBytes());
                }
                newRawString.append(matcher.group("data")).append("\r");
            }

            if (newRawString.length() == 0) {
                throw new UnexpectedResponse(rawString.getBytes());
            }

            rawString = newRawString.toString();
        }

        if (isCurrent(BooleanCommand.EchoOn)) {
            rawString = rawString.replaceFirst(Pattern.quote(currentRequest), "");
            rawString = rawString.replaceAll("^\\s+", "");
        }

        String responseHeader = Integer.toHexString(4 + Integer.parseInt(currentRequest.substring(0, 1)))
                + currentRequest.substring(1);

        if (rawString.startsWith(responseHeader)) {
            rawString = rawString.substring(responseHeader.length());
        }

        if (isCurrent(BooleanCommand.SpaceOn) && !dontFilter) {
            Pattern space = Pattern.compile("([^a-z0-9\\n\\r])", Pattern.CASE_INSENSITIVE);
            rawString = space.matcher(rawString).replaceAll("");
        }
        if (isCurrent(BooleanCommand.LinefeedOn)) {
            rawString = rawString.replaceAll("\\r\\n", "\r");
        }

        rawString = rawString.replaceAll("\\r+", "\r");
        rawString = rawString.replaceAll("\\r$", "");

        if (rawString.equals("NODATA")) {
            throw new NoDataResponse();
        }

        logger.info("Clean response of the command: {}", rawString);
        return rawString.getBytes();
    }

    @Override
    protected ReadCharResult actionForChar(char read, StringBuilder context) {
        if (read == '>') {
            logger.debug("Receive char >, stop reading");
            return ReadCharResult.IgnoreAndStop;
        }
        return super.actionForChar(read, context);
    }

    @Override
    protected String dataToSend(Command command) {
        if (command.getRequest().equals(lastRequest)) {
            return "\r";
        }
        String data = super.dataToSend(command) + "\r";

        if (isCurrent(BooleanCommand.SpaceOff)) {
            return data.replaceAll(" ", "");
        }

        return data;
    }

    public void reduceCommunicationSize() throws IOException, ScriptException {
        logger.info("Reducing communication by turning off 'Echo', 'Line Feed', 'Space' and 'Header'");
        sendCommand(BooleanCommand.EchoOff);
        sendCommand(BooleanCommand.LinefeedOff);
        sendCommand(BooleanCommand.SpaceOff);
        sendCommand(BooleanCommand.HeaderOff);
    }
}
