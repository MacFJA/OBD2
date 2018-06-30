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
import io.github.macfja.obd2.command.livedata.FreezeDiagnosticTroubleCode;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

/**
 * Get the equivalent Mode 01 ({@link LiveCommand}) command in the freeze frame. (State when the MIL was turn on)
 *
 * @author MacFJA
 */
public class FrozeCommand implements Command {
    private Command mode01Command;

    /**
     * @param mode01Command The Mode 01 command
     * @throws IllegalArgumentException If the command is not a Mode 01 command or if the command is "01 01"
     */
    public FrozeCommand(Command mode01Command) throws IllegalArgumentException {
        if (!mode01Command.getRequest().startsWith("01")) {
            throw new IllegalArgumentException("The command must be a live data (Mode 01) command");
        }
        if (mode01Command.getRequest().endsWith("01")) {
            throw new IllegalArgumentException("The command '01 01' is not available in froze mode (Mode 02)");
        }
        this.mode01Command = mode01Command;
    }

    /**
     * Get the list of all command that are in or compatible with Mode 02
     *
     * @return List of commands
     */
    public static Map<String, FrozeCommand> getMode02Commands() {
        Map<String, Command> commands = LiveCommand.getMode01Commands();
        commands.remove("01"); // Mode 01 only
        commands.put("02", new FreezeDiagnosticTroubleCode()); // Mode 02 only

        Map<String, FrozeCommand> frozeCommands = new HashMap<>();

        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            frozeCommands.put(entry.getKey(), new FrozeCommand(entry.getValue()));
        }

        return frozeCommands;
    }

    @Override
    public String getRequest() {
        return "02" + mode01Command.getRequest().substring(2);
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return mode01Command.getResponse(rawResult);
    }

    @Override
    public String toString() {
        return mode01Command.toString();
    }
}
