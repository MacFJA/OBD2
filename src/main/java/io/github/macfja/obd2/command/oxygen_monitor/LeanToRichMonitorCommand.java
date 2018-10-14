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
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.Unit;
import io.github.macfja.obd2.response.CalculatedResponse;

import javax.script.ScriptException;

/**
 * <p>This class is the OBD-II command for multiple commands: "05 0201" to "05 0210".</p>
 * <p>The commands are: "05 0201" (Service 05, PID 0x0201), "05 0202" (Service 05, PID 0x0202),
 * "05 0203" (Service 05, PID 0x0203), "05 0204" (Service 05, PID 0x0204), "05 0205" (Service 05, PID 0x0205),
 * "05 0206" (Service 05, PID 0x0206), "05 0207" (Service 05, PID 0x0207), "05 0208" (Service 05, PID 0x0208),
 * "05 0209" (Service 05, PID 0x0209), "05 020A" (Service 05, PID 0x020A), "05 020B" (Service 05, PID 0x020B),
 * "05 020C" (Service 05, PID 0x020C), "05 020D" (Service 05, PID 0x020D), "05 020E" (Service 05, PID 0x020E),
 * "05 020F" (Service 05, PID 0x020F) and "05 0210" (Service 05, PID 0x0210).</p>
 * <p>Description: <ul>
 * <li>O2 Sensor Monitor Bank 1 Sensor 1 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 1 Sensor 2 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 1 Sensor 3 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 1 Sensor 4 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 1 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 2 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 3 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 4 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 1 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 2 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 3 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 4 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 1 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 2 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 3 - Lean to rich sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 4 - Lean to rich sensor voltage</li>
 * </ul></p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>1 byte</td></tr>
 * <tr><th>Unit</th>     <td>V</td></tr>
 * <tr><th>Min value</th><td>0</td></tr>
 * <tr><th>Max value</th><td>1.275</td></tr>
 * <tr><th>Equation</th> <td><pre>A * 0.005</pre></td></tr>
 * <tr><th>Class</th>    <td>{@link CalculatedResponse}</td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Service_05">Wikipedia</a>
 */
public enum LeanToRichMonitorCommand implements Command {
    Bank1Sensor1("0201"),
    Bank1Sensor2("0202"),
    Bank1Sensor3("0203"),
    Bank1Sensor4("0204"),
    Bank2Sensor1("0205"),
    Bank2Sensor2("0206"),
    Bank2Sensor3("0207"),
    Bank2Sensor4("0208"),
    Bank3Sensor1("0209"),
    Bank3Sensor2("020A"),
    Bank3Sensor3("020B"),
    Bank3Sensor4("020C"),
    Bank4Sensor1("020D"),
    Bank4Sensor2("020E"),
    Bank4Sensor3("020F"),
    Bank4Sensor4("0210"),;

    private final String code;

    LeanToRichMonitorCommand(String code) {
        this.code = code;
    }

    @Override
    public String getRequest() {
        return "05 " + code;
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return new CalculatedResponse(rawResult, "A * 0.005") {
            @Override
            public Unit getUnit() {
                return Unit.Volt;
            }
        };
    }

    public int getBank() {
        String name = super.toString();
        return Integer.parseInt(name.substring(4, 5));
    }

    public int getSensor() {
        String name = super.toString();
        return Integer.parseInt(name.substring(11, 12));
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + super.toString();
    }
}
