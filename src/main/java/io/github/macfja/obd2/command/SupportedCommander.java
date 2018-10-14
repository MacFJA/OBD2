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
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.command.livedata.SupportedPid;
import io.github.macfja.obd2.command.oxygen_monitor.LeanToRichMonitorCommand;
import io.github.macfja.obd2.command.oxygen_monitor.RichToLeanMonitorCommand;
import io.github.macfja.obd2.command.oxygen_monitor.SupportedOxygenSensorMonitorCommand;
import io.github.macfja.obd2.commander.AbstractSupportedCommanderDecorator;
import io.github.macfja.obd2.commander.CommanderInterface;
import io.github.macfja.obd2.exception.ExceptionResponse;
import io.github.macfja.obd2.exception.UnsupportedResponse;
import io.github.macfja.obd2.response.AvailableOxygenSensorMonitorResponse;
import io.github.macfja.obd2.response.AvailablePidResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A wrapper around a {@link CommanderInterface} to handle if a OBD {@link Command} is available or not.</p>
 * <p>If a {@link Command} is not available, then a {@link UnsupportedResponse} is return as a {@link Response}.</p>
 *
 * @author MacFJA
 * @see Command
 * @see Response
 */
public class SupportedCommander extends AbstractSupportedCommanderDecorator {
    private final Map<String, Response> availableResponse = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SupportedCommander(CommanderInterface parent) {
        super(parent);
    }

    /**
     * Check if a service 09 command is supported (make any needed sub calls to check availability)
     *
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    protected boolean isService09PidSupported(Command command) {
        String pid = command.getRequest().substring(command.getRequest().length() - 2);
        Response response = getIsSupportedResponse(new io.github.macfja.obd2.command.vehicle_info.SupportedPid());

        if (!(response instanceof AvailablePidResponse)) {
            return false;
        }

        return ((AvailablePidResponse) response).isPidSupported(pid);
    }

    /**
     * Check if a mode 09 command is supported (make any needed sub calls to check availability)
     *
     * @deprecated Since 1.1.0, replaced by {@link #isService09PidSupported(Command)}
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    @Deprecated
    protected boolean isMode09PidSupported(Command command) {
        return isService09PidSupported(command);
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
     * Check if a service 05 command is supported (make any needed sub calls to check availability)
     *
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    protected boolean isService05PidSupported(Command command) {
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
     * Check if a mode 05 command is supported (make any needed sub calls to check availability)
     *
     * @deprecated Since 1.1.0, replaced by {@link #isService05PidSupported(Command)}
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    @Deprecated
    protected boolean isMode05PidSupported(Command command) {
        return isService05PidSupported(command);
    }

    /**
     * Check if a service 01 or service 02 command is supported (make any needed sub calls to check availability)
     *
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    protected boolean isService01PidSupported(Command command) {
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

    /**
     * Check if a mode 01 or mode 02 command is supported (make any needed sub calls to check availability)
     *
     * @deprecated Since 1.1.0, replaced by {@link #isService01PidSupported(Command)}
     * @param command The command to check
     * @return {@code true} if the command is supported, {@code false} otherwise
     */
    @Deprecated
    protected boolean isMode01PidSupported(Command command) {
        return isService01PidSupported(command);
    }

    @Override
    protected boolean doIsCommandSupported(Command command) {
        String commandService = command.getRequest().substring(0, 2);

        // Only check OBDII service commands and by design supported commands,
        // suppose that others commands have already be check or will be check after
        if (!Arrays.asList("01", "02", "05", "09").contains(commandService)
                || command instanceof io.github.macfja.obd2.command.vehicle_info.SupportedPid
                || command.equals(SupportedPid.Range01To20)
                || command instanceof SupportedOxygenSensorMonitorCommand
                ) {
            return true;
        }

        if ((commandService.equals("01") || commandService.equals("02")) && !isService01PidSupported(command)) {
            logger.warn("The command {} is not support by the ECU", command.getRequest());
            return false;
        }

        if (commandService.equals("05") && !isService05PidSupported(command)) {
            logger.warn("The command {} is not support by the ECU", command.getRequest());
            return false;
        }

        if (commandService.equals("09") && !isService09PidSupported(command)) {
            logger.warn("The command {} is not support by the ECU", command.getRequest());
            return false;
        }

        return true;
    }
}
