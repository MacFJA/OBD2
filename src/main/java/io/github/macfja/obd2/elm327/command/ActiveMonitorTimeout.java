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

package io.github.macfja.obd2.elm327.command;

import io.github.macfja.obd2.elm327.ExcessiveLength;

/**
 * <p>This class is the ELM327 command for "AT AMT hh".</p>
 * <p>Description: Set the Activity Mon Timeout to hh</p>
 * <p>ELM327 version: {@code 2.0}</p>
 * <p>Response: {@code OK} in case of a success</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public class ActiveMonitorTimeout extends ATSetCommand {
    private int value;

    public ActiveMonitorTimeout(int value) {
        if (value > 255) {
            throw new ExcessiveLength();
        }
        this.value = value;
    }

    /**
     * Create a "AT AMT hh" command for a specific duration
     *
     * @param seconds The duration (in seconds) to consider the request as time out
     * @return A configured "AT AMT hh" command
     */
    public static ActiveMonitorTimeout createWithSecond(float seconds) {
        Double value = (seconds / 0.65536) - 1;
        return new ActiveMonitorTimeout(value.intValue());
    }

    protected String getCode() {
        return "AMT " + Integer.toHexString(value);
    }

    @Override
    public String minElmVersion() {
        return "2.0";
    }
}
