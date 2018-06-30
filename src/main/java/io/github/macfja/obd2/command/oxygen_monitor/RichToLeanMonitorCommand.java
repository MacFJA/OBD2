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
 * <p>This class is the OBD-II command for multiple commands: "05 0101" to "05 0110".</p>
 * <p>The commands are: "05 0101" (Mode 05, PID 0x0101), "05 0102" (Mode 05, PID 0x0102),
 * "05 0103" (Mode 05, PID 0x0103), "05 0104" (Mode 05, PID 0x0104), "05 0105" (Mode 05, PID 0x0105),
 * "05 0106" (Mode 05, PID 0x0106), "05 0107" (Mode 05, PID 0x0107), "05 0108" (Mode 05, PID 0x0108),
 * "05 0109" (Mode 05, PID 0x0109), "05 010A" (Mode 05, PID 0x010A), "05 010B" (Mode 05, PID 0x010B),
 * "05 010C" (Mode 05, PID 0x010C), "05 010D" (Mode 05, PID 0x010D), "05 010E" (Mode 05, PID 0x010E),
 * "05 010F" (Mode 05, PID 0x010F) and "05 0110" (Mode 05, PID 0x0110).</p>
 * <p>Description: <ul>
 * <li>O2 Sensor Monitor Bank 1 Sensor 1 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 1 Sensor 2 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 1 Sensor 3 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 1 Sensor 4 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 1 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 2 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 3 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 2 Sensor 4 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 1 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 2 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 3 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 3 Sensor 4 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 1 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 2 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 3 - Rich to lean sensor voltage</li>
 * <li>O2 Sensor Monitor Bank 4 Sensor 4 - Rich to lean sensor voltage</li>
 * </ul></p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>1 byte</td></tr>
 * <tr><th>Unit</th>     <td>V</td></tr>
 * <tr><th>Min value</th><td>0</td></tr>
 * <tr><th>Max value</th><td>1.275</td></tr>
 * <tr><th>Equation</th> <td><pre>A * 0.005</pre></td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_05">Wikipedia</a>
 */
public enum RichToLeanMonitorCommand implements Command {
    Bank1Sensor1("0101"),
    Bank1Sensor2("0102"),
    Bank1Sensor3("0103"),
    Bank1Sensor4("0104"),
    Bank2Sensor1("0105"),
    Bank2Sensor2("0106"),
    Bank2Sensor3("0107"),
    Bank2Sensor4("0108"),
    Bank3Sensor1("0109"),
    Bank3Sensor2("010A"),
    Bank3Sensor3("010B"),
    Bank3Sensor4("010C"),
    Bank4Sensor1("010D"),
    Bank4Sensor2("010E"),
    Bank4Sensor3("010F"),
    Bank4Sensor4("0110"),;

    private final String code;

    RichToLeanMonitorCommand(String code) {
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
