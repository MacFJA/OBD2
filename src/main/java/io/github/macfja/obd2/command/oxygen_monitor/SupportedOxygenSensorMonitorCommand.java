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

package io.github.macfja.obd2.command.oxygen_monitor;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.PersistentCommand;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.response.AvailableOxygenSensorMonitorResponse;

import javax.script.ScriptException;

/**
 * <p>This class is the OBD-II command for "05 0100" (Mode 05, PID 0x0100).</p>
 * <p>Description: OBD Monitor IDs supported ($01 – $20)</p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>4 bytes</td></tr>
 * <tr><th>Unit</th>     <td>-</td></tr>
 * <tr><th>Min value</th><td>-</td></tr>
 * <tr><th>Max value</th><td>-</td></tr>
 * <tr><th>Equation</th> <td>Bit encoded</td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_05">Wikipedia</a>
 */
@PersistentCommand
public class SupportedOxygenSensorMonitorCommand implements Command {
    @Override
    public String getRequest() {
        return "05 0100";
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return new AvailableOxygenSensorMonitorResponse(rawResult);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
