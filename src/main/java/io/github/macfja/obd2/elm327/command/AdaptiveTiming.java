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

/**
 * <p>This class is the ELM327 command for "AT AT0", "AT AT1" and "AT AT2".</p>
 * <p>Description: Adaptive Timing off, auto1*, auto2</p>
 * <p>ELM327 version: {@code 1.2}</p>
 * <p>Response: {@code OK} in case of a success</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public class AdaptiveTiming extends ATSetCommand {
    private int type;

    public AdaptiveTiming(int type) {
        if (type < 0 || type > 2) {
            throw new IllegalArgumentException();
        }
        this.type = type;
    }

    public static AdaptiveTiming createDisabled() {
        return new AdaptiveTiming(0);
    }

    public static AdaptiveTiming createFast() {
        return new AdaptiveTiming(2);
    }

    public static AdaptiveTiming createNormal() {
        return new AdaptiveTiming(1);
    }

    protected String getCode() {
        return "AT" + String.valueOf(type);
    }

    @Override
    public String minElmVersion() {
        return "1.2";
    }
}
