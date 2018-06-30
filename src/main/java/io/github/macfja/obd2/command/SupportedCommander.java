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

package io.github.macfja.obd2.command;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Commander;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.command.livedata.SupportedPid;
import io.github.macfja.obd2.command.oxygen_monitor.LeanToRichMonitorCommand;
import io.github.macfja.obd2.command.oxygen_monitor.RichToLeanMonitorCommand;
import io.github.macfja.obd2.command.oxygen_monitor.SupportedOxygenSensorMonitorCommand;
import io.github.macfja.obd2.exception.ExceptionResponse;
import io.github.macfja.obd2.exception.UnsupportedResponse;
import io.github.macfja.obd2.response.AvailableOxygenSensorMonitorResponse;
import io.github.macfja.obd2.response.AvailablePidResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A wrapper around a Commander to handle if a {@link Command} is available or not.</p>
 * <p>If a {@link Command} is not available, then a {@link UnsupportedResponse} is return as a {@link Response}.</p>
 *
 * @author MacFJA
 * @see Command
 * @see Response
 */
public class SupportedCommander extends Commander {
    private final Commander parent;
    private final Map<String, Response> availableResponse = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SupportedCommander(Commander parent) {
        this.parent = parent;
    }

    @Override
    public void setCommunicationInterface(OutputStream toOBDStream, InputStream fromOBDStream) {
        parent.setCommunicationInterface(toOBDStream, fromOBDStream);
    }

    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException, ExceptionResponse {
        String commandMode = command.getRequest().substring(0, 2);

        if (!Arrays.asList("01", "02", "05", "09").contains(commandMode)
                || command instanceof io.github.macfja.obd2.command.vehicle_info.SupportedPid
                || command.equals(SupportedPid.Range01To20)
                || command instanceof SupportedOxygenSensorMonitorCommand
                ) {
            return parent.sendCommand(command);
        }

        if ((commandMode.equals("01") || commandMode.equals("02")) && !isMode01PidSupported(command)) {
            logger.warn("The command {} is not support by the ECU", command.getRequest());
            return new UnsupportedResponse(command);
        }

        if (commandMode.equals("05") && !isMode05PidSupported(command)) {
            logger.warn("The command {} is not support by the ECU", command.getRequest());
            return new UnsupportedResponse(command);
        }

        if (commandMode.equals("09") && !isMode09PidSupported(command)) {
            logger.warn("The command {} is not support by the ECU", command.getRequest());
            return new UnsupportedResponse(command);
        }

        return parent.sendCommand(command);
    }

    /**
     * Check if a mode 09 command is supported (make any needed sub calls to check availability)
     *
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    protected boolean isMode09PidSupported(Command command) {
        String pid = command.getRequest().substring(command.getRequest().length() - 2);
        Response response = getIsSupportedResponse(new io.github.macfja.obd2.command.vehicle_info.SupportedPid());

        if (!(response instanceof AvailablePidResponse)) {
            return false;
        }

        return ((AvailablePidResponse) response).isPidSupported(pid);
    }

    /**
     * Lookup in the memory storage if the response of the command is not already existing
     *
     * @param command The command to lookup
     * @return The response of the command
     */
    private Response getIsSupportedResponse(Command command) {
        if (availableResponse.containsKey(command.getRequest())) {
            return availableResponse.get(command.getRequest());
        }
        Response response;
        try {
            response = sendCommand(command);
        } catch (IOException | ScriptException | ExceptionResponse e) {
            response = new ExceptionResponse(e.getLocalizedMessage().getBytes());
        }
        availableResponse.put(command.getRequest(), response);

        return response;
    }

    /**
     * Check if a mode 05 command is supported (make any needed sub calls to check availability)
     *
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    protected boolean isMode05PidSupported(Command command) {
        Response response = getIsSupportedResponse(new SupportedOxygenSensorMonitorCommand());

        if (!(response instanceof AvailableOxygenSensorMonitorResponse)) {
            return false;
        }

        AvailableOxygenSensorMonitorResponse castedResponse = (AvailableOxygenSensorMonitorResponse) response;

        if (command instanceof LeanToRichMonitorCommand) {
            LeanToRichMonitorCommand castedCommand = (LeanToRichMonitorCommand) command;
            return castedResponse.isLeanToRichSupported(castedCommand.getBank(), castedCommand.getSensor());
        }

        if (command instanceof RichToLeanMonitorCommand) {
            RichToLeanMonitorCommand castedCommand = (RichToLeanMonitorCommand) command;
            return castedResponse.isRichToLeanSupported(castedCommand.getBank(), castedCommand.getSensor());
        }

        return false;
    }

    /**
     * Check if a mode 01 or mode 02 command is supported (make any needed sub calls to check availability)
     *
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    protected boolean isMode01PidSupported(Command command) {
        Response response;
        String pid = command.getRequest().substring(command.getRequest().length() - 2);
        Integer numericPid = Integer.parseInt(pid, 16);
        if (numericPid <= Integer.parseInt("20", 16)) {
            response = getIsSupportedResponse(SupportedPid.Range01To20);
        } else if (numericPid <= Integer.parseInt("40", 16)) {
            response = getIsSupportedResponse(SupportedPid.Range21To40);
        } else if (numericPid <= Integer.parseInt("60", 16)) {
            response = getIsSupportedResponse(SupportedPid.Range41To60);
        } else if (numericPid <= Integer.parseInt("80", 16)) {
            response = getIsSupportedResponse(SupportedPid.Range61To80);
        } else if (numericPid <= Integer.parseInt("A0", 16)) {
            response = getIsSupportedResponse(SupportedPid.Range81ToA0);
        } else if (numericPid <= Integer.parseInt("C0", 16)) {
            response = getIsSupportedResponse(SupportedPid.RangeA1ToC0);
        } else if (numericPid <= Integer.parseInt("E0", 16)) {
            response = getIsSupportedResponse(SupportedPid.RangeC1ToE0);
        } else {
            return false;
        }

        if (response instanceof AvailablePidResponse) {
            return ((AvailablePidResponse) response).isPidSupported(pid);
        }
        return false;
    }
}
