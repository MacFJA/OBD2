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
import io.github.macfja.obd2.response.TemperatureResponse;

import javax.script.ScriptException;

/**
 * <p>This class is the OBD-II command for multiple commands: "01 3C" to "01 0F".</p>
 * <p>The commands are: "01 3C" (Mode 01, PID 0x3C), "01 3D" (Mode 01, PID 0x3D),
 * "01 3E" (Mode 01, PID 0x3E) and "01 3F" (Mode 01, PID 0x3F).</p>
 * <p>Description: <ul>
 * <li>Catalyst Temperature: Bank 1, Sensor 1</li>
 * <li>Catalyst Temperature: Bank 2, Sensor 1</li>
 * <li>Catalyst Temperature: Bank 1, Sensor 2</li>
 * <li>Catalyst Temperature: Bank 2, Sensor 2</li>
 * </ul></p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>2 bytes</td></tr>
 * <tr><th>Unit</th>     <td>°C</td></tr>
 * <tr><th>Min value</th><td>-40</td></tr>
 * <tr><th>Max value</th><td>6,513.5</td></tr>
 * <tr><th>Equation</th> <td><pre>
 * 256 * A + B
 * ----------- - 40
 *     10
 *     </pre></td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_01">Wikipedia</a>
 */
public enum CatalystTemperature implements Command {
    /**
     * Catalyst temperature command for the Sensor 1 of the Bank 1
     */
    Bank1Sensor1("3C"),
    /**
     * Catalyst temperature command for the Sensor 1 of the Bank 2
     */
    Bank2Sensor1("3D"),
    /**
     * Catalyst temperature command for the Sensor 2 of the Bank 1
     */
    Bank1Sensor2("3E"),
    /**
     * Catalyst temperature command for the Sensor 2 of the Bank 2
     */
    Bank2Sensor2("3F");

    private final String code;

    CatalystTemperature(String code) {
        this.code = code;
    }

    @Override
    public String getRequest() {
        return "01 " + code;
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return new TemperatureResponse(
                rawResult,
                "(256 * A + B ) / 10 - 40"
        );
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + super.toString();
    }
}
