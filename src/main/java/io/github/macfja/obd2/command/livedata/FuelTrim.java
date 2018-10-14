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

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.response.CalculatedResponse;
import io.github.macfja.obd2.response.PercentResponse;

import javax.script.ScriptException;

/**
 * <p>This class is the OBD-II command for multiple commands: "01 06" to "01 08".</p>
 * <p>The commands are: "01 06" (Service 01, PID 0x06), "01 07" (Service 01, PID 0x07),
 * "01 07" (Service 01, PID 0x07) and "01 08" (Service 01, PID 0x08).</p>
 * <p>Description: <ul>
 * <li>Short term fuel trim—Bank 1</li>
 * <li>Long term fuel trim—Bank 1</li>
 * <li>Short term fuel trim—Bank 2</li>
 * <li>Long term fuel trim—Bank 2</li>
 * </ul></p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>1 byte</td></tr>
 * <tr><th>Unit</th>     <td>%</td></tr>
 * <tr><th>Min value</th><td>-100</td></tr>
 * <tr><th>Max value</th><td>99.2</td></tr>
 * <tr><th>Equation</th> <td><pre>
 *  A
 * ---- - 100
 * 1.28
 *     </pre></td></tr>
 * <tr><th>Class</th>    <td>{@link PercentResponse}</td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Service_01">Wikipedia</a>
 */
public enum FuelTrim implements Command {
    ShortTermBank1("06"),
    ShortTermBank2("08"),
    LongTermBank1("07"),
    LongTermBank2("09");

    private final String code;

    FuelTrim(String code) {
        this.code = code;
    }

    @Override
    public String getRequest() {
        return "01 " + code;
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return new PercentResponse(
                rawResult,
                CalculatedResponse.calculateFromEquation(rawResult, "A / 1.28 - 100")
        );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + super.toString();
    }
}
