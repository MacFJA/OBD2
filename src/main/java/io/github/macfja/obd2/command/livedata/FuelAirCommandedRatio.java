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

package io.github.macfja.obd2.command.livedata;

import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.Unit;
import io.github.macfja.obd2.command.LiveCommand;
import io.github.macfja.obd2.response.CalculatedResponse;

import javax.script.ScriptException;

/**
 * <p>This class is the OBD-II command for "01 44" (Mode 01, PID 0x44).</p>
 * <p>Description: Fuel–Air commanded equivalence ratio</p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>2 bytes</td></tr>
 * <tr><th>Unit</th>     <td>-</td></tr>
 * <tr><th>Min value</th><td>0</td></tr>
 * <tr><th>Max value</th><td>2</td></tr>
 * <tr><th>Equation</th> <td><pre>
 *   2
 * ----- * (256 * A + B)
 * 65536
 *     </pre></td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_01">Wikipedia</a>
 */
public class FuelAirCommandedRatio extends LiveCommand {
    @Override
    public String getCode() {
        return "44";
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return new CalculatedResponse(rawResult, "(2 / 65536) * (256 * A + B)") {
            @Override
            public Unit getUnit() {
                return Unit.NoUnit;
            }
        };
    }
}
