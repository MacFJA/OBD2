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
import io.github.macfja.obd2.response.SecondaryOxygenSensorTrimResponse;

/**
 * <p>This class is the OBD-II command for multiple commands: "01 55" to "01 58".</p>
 * <p>The commands are: "01 55" (Mode 01, PID 0x55), "01 56" (Mode 01, PID 0x56),
 * "01 57" (Mode 01, PID 0x57) and "01 58" (Mode 01, PID 0x58).</p>
 * <p>Description: <ul>
 * <li>Short term secondary oxygen sensor trim, A: bank 1, B: bank 3</li>
 * <li>Long term secondary oxygen sensor trim, A: bank 1, B: bank 3</li>
 * <li>Short term secondary oxygen sensor trim, A: bank 2, B: bank 4</li>
 * <li>Long term secondary oxygen sensor trim, A: bank 2, B: bank 4</li>
 * </ul></p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>2 bytes</td></tr>
 * <tr><th>Unit</th>     <td>   % | %</td></tr>
 * <tr><th>Min value</th><td>-100 | -100</td></tr>
 * <tr><th>Max value</th><td>99.2 | 99.2</td></tr>
 * <tr><th>Equation</th> <td><pre>
 *  A          |   B
 * ---- - 100  |  ---- - 100
 * 1.28        |  1.28
 *     </pre></td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_01">Wikipedia</a>
 */
public enum SecondaryOxygenSensorTrim implements Command {
    ShortTermB1B3("55"),
    LongTermB1B3("56"),
    ShortTermB2B4("57"),
    LongTermB2B4("58");

    private final String code;

    SecondaryOxygenSensorTrim(String code) {
        this.code = code;
    }

    @Override
    public String getRequest() {
        return "01 " + code;
    }

    @Override
    public Response getResponse(byte[] rawResult) {
        return new SecondaryOxygenSensorTrimResponse(rawResult);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + super.toString();
    }
}
