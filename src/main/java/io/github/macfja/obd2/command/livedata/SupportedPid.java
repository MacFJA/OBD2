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
import io.github.macfja.obd2.PersistentCommand;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.response.AvailablePidResponse;

/**
 * <p>This class is the OBD-II command for multiple commands: "01 00" (Mode 01, PID 0x00), "01 20" (Mode 01, PID 0x20),
 * "01 40" (Mode 01, PID 0x40) and "01 60" (Mode 01, PID 0x60), "01 80" (Mode 01, PID 0x80),
 * "01 A0" (Mode 01, PID 0xA0), "01 C0" (Mode 01, PID 0xC0).</p>
 * <p>Description: <ul>
 * <li>PIDs supported [01 - 20]</li>
 * <li>PIDs supported [21 - 40]</li>
 * <li>PIDs supported [41 - 60]</li>
 * <li>PIDs supported [61 - 80]</li>
 * <li>PIDs supported [81 - A0]</li>
 * <li>PIDs supported [A1 - C0]</li>
 * <li>PIDs supported [C1 - E0]</li>
 * </ul></p>
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
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_01">Wikipedia</a>
 */
@PersistentCommand
public enum SupportedPid implements Command {
    Range01To20("00", "01"),
    Range21To40("20", "21"),
    Range41To60("40", "41"),
    Range61To80("60", "61"),
    Range81ToA0("80", "81"),
    RangeA1ToC0("A0", "A1"),
    RangeC1ToE0("C0", "C1");

    private final String code;
    private final String startAt;

    SupportedPid(String code, String startAt) {
        this.code = code;
        this.startAt = startAt;
    }

    @Override
    public String getRequest() {
        return "01 " + code;
    }

    @Override
    public Response getResponse(byte[] rawResult) {
        return new AvailablePidResponse(rawResult, startAt);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + super.toString();
    }
}
