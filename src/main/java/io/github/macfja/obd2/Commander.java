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

package io.github.macfja.obd2;

import io.github.macfja.obd2.commander.CommanderInterface;
import io.github.macfja.obd2.exception.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Send OBD command and return command result.
 *
 * @author MacFJA
 * @see Command
 * @see Response
 */
public class Commander implements CommanderInterface {
    private final Map<String, byte[]> persistentResult = new HashMap<>();
    private InputStream inputStream;
    private OutputStream outputStream;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void setCommunicationInterface(OutputStream toOBDStream, InputStream fromOBDStream) {
        this.outputStream = toOBDStream;
        this.inputStream = fromOBDStream;
    }

    /**
     * Send the command to the OBD interface and get its response
     *
     * @param command The command to send
     * @return The command response
     * @throws IOException     If an error occurs during communication with the OBD interfaces
     * @throws ScriptException If the conversion equation is wrong
     */
    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException, ExceptionResponse {
        String request = command.getRequest();
        logger.info("Sending command: {}", request);

        // If we already have the command result, use it directly
        if (persistentResult.containsKey(request)) {
            logger.debug("Already persisted command, return stored response");
            logger.debug("stored response: {}", persistentResult.get(request));
            return command.getResponse(persistentResult.get(request));
        }

        outputStream.write((dataToSend(command)).getBytes());
        outputStream.flush();
        byte[] rawResult = read();

        if (command.getClass().getAnnotation(PersistentCommand.class) != null) {
            persistCommand(request, rawResult);
        }

        return command.getResponse(rawResult);
    }

    @Override
    public void clearPersistentResponseStorage() {
        logger.info("Clear persistent response storage");
        persistentResult.clear();
    }

    /**
     * <p>Remove a persistent command result</p>
     * <p>Can be use if an error occurs, or if the data corrupted or invalid</p>
     *
     * @param request The request code of the command ({@link Command#getRequest()})
     */
    @Override
    public void unpersistCommand(String request) {
        if (persistentResult.containsKey(request)) {
            logger.info("Remove command {} from persistent storage", request);
            persistentResult.remove(request);
        }
    }

    @Override
    public void persistCommand(String request, byte[] rawResponse) {
        logger.info("Persistent command ({}), store result", request);
        persistentResult.put(request, rawResponse);
    }

    /**
     * Read data from the input stream
     *
     * @return The list of bytes reads
     * @throws IOException If an error occurs during communication with the OBD interface
     */
    protected byte[] read() throws IOException, ExceptionResponse {
        byte b;
        StringBuilder resultString = new StringBuilder();

        // read until the end of stream reached
        char c;
        // -1 if the end of the stream is reached
        while (((b = (byte) inputStream.read()) > -1)) {
            c = (char) b;
            ReadCharResult action = actionForChar(c, resultString);

            if (ReadCharResult.Save.equals(action) || ReadCharResult.SaveAndStop.equals(action)) {
                resultString.append(c);
            }
            if (ReadCharResult.IgnoreAndStop.equals(action) || ReadCharResult.SaveAndStop.equals(action)) {
                break;
            }
        }

        logger.debug("Received response: {}", resultString.toString());
        return resultString.toString().getBytes();
    }

    /**
     * Get the data to send to the OBD interface
     *
     * @param command The command to send
     * @return The data
     */
    protected String dataToSend(Command command) {
        return command.getRequest();
    }

    /**
     * The action to do for a specific read char
     *
     * @param read    The "in read" char
     * @param context The data already read and saved
     * @return The action to do with this char
     */
    protected ReadCharResult actionForChar(char read, StringBuilder context) {
        return ReadCharResult.Save;
    }

    public long getBinaryChecksum(long number, int wordSize) {
        String binNumber = Long.toBinaryString(number);
        long sum = 0;
        int loop = 0;
        int max = (int) Math.ceil(binNumber.length() / wordSize);
        for (loop = 0; loop <= max; loop++) {
            String subBin = binNumber.substring(Math.max(0, binNumber.length() - (loop + 1) * wordSize), binNumber.length() - (loop) * wordSize);
            sum += Long.parseLong(subBin, 2);
        }
        String checksum = Long.toBinaryString(sum);

        if (checksum.length() <= wordSize) {
            return sum;
        }

        return Long.parseLong(checksum.substring(checksum.length() - wordSize), 2);
    }

    /**
     * The list of possible action for a char
     *
     * @author MacFJA
     */
    protected enum ReadCharResult {
        /**
         * Ignore the char and continue the reading.
         * The char will not be added to the command result
         */
        Ignore,
        /**
         * Save the char and continue reading.
         * The char will be added to the command result
         */
        Save,
        /**
         * Save the char and end the reading
         * The char will be added to the command result
         */
        SaveAndStop,
        /**
         * Ignore the char and end the reading.
         * The char will not be added to the command result
         */
        IgnoreAndStop
    }
}
