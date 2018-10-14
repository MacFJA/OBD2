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

package io.github.macfja.obd2.commander;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.elm327.Commander;
import io.github.macfja.obd2.elm327.command.BooleanCommand;

import javax.script.ScriptException;
import java.io.IOException;

/**
 * Commander for {@code OBDSim}.
 * Based on the ELM327 {@link Commander}.
 * The OBDSim output is slightly different from a ELM327: The prompt char is not rendered at the same time.
 *
 * @author MacFJA
 * @see <a href="https://icculus.org/obdgpslogger/obdsim.html">OBDSim</a>
 */
public class ObdSimCommander extends Commander {
    private boolean firstRequest = true;
    private boolean alreadyReceiveNewLine = false;

    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException {
        if (firstRequest) {
            firstRequest = false;
            super.putBooleanCommand(BooleanCommand.EchoOn);
            super.sendCommand(BooleanCommand.EchoOff);
            super.sendCommand(BooleanCommand.LinefeedOn);
        }
        return super.sendCommand(command);
    }

    @Override
    protected ReadCharResult actionForChar(char read, StringBuilder context) {
        /*
         * ELM327 write the char ">" after the response in the output which is useful to find the end of the response,
         * But OBDSim write to the output the char ">" before the response so it can't be used to fin the end of the response
         *
         * To find the end of the response, we search for 2 consecutive "\n"
         */

        // When start, OBDSim has ECHO set to On
        // We know that the only time we set a ECHO (request) is on the first command, no need to check for others
        // So if the context is empty or equal to "AT E0" we ignore any new line
        if (context.length() == 0 || context.toString().equals("AT E0")) {
            alreadyReceiveNewLine = false;
        }
        // Change the behavior of the ELM327 implementation
        if (read == '>') {
            return ReadCharResult.Ignore;
        }

        if (read == '\n') {
            alreadyReceiveNewLine = !alreadyReceiveNewLine;

            return alreadyReceiveNewLine ? ReadCharResult.Ignore : ReadCharResult.IgnoreAndStop;
        }

        return super.actionForChar(read, context);
    }
}
