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
 * <p>This class is the ELM327 command for "AT IB10", "AT IB12", "AT IB15", "AT IB48" and "AT IB96".</p>
 * <p>Description: <ul>
 * <li>set the ISO Baud rate to 10400*</li>
 * <li>set the ISO Baud rate to 12500</li>
 * <li>set the ISO Baud rate to 15625</li>
 * <li>set the ISO Baud rate to 4800</li>
 * <li>set the ISO Baud rate to 9600</li>
 * </ul></p>
 * <p>ELM327 version: <ul>
 * <li>{@code 1.0}</li>
 * <li>{@code 2.2}</li>
 * <li>{@code 2.2}</li>
 * <li>{@code 1.4}</li>
 * <li>{@code 1.0}</li>
 * </ul></p>
 * <p>Response: {@code OK} in case of a success</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public class BaudRate extends ATSetCommand {
    private final int speed;

    protected BaudRate(int speed) {
        this.speed = speed;
    }

    /**
     * Create a "AT BI10" command to set the ISO Baud rate to 10400.
     * (For protocols ISO 9141-2 and ISO 14230-4)
     *
     * @return A configured "AT BIdd" command
     */
    public static BaudRate create10400() {
        return new BaudRate(10);
    }

    /**
     * Create a "AT BI12" command to set the ISO Baud rate to 12500.
     * (For protocols ISO 9141-2 and ISO 14230-4)
     *
     * @return A configured "AT BIdd" command
     */
    public static BaudRate create12500() {
        return new BaudRate(12);
    }

    /**
     * Create a "AT BI15" command to set the ISO Baud rate to 15625.
     * (For protocols ISO 9141-2 and ISO 14230-4)
     *
     * @return A configured "AT BIdd" command
     */
    public static BaudRate create15625() {
        return new BaudRate(15);
    }

    /**
     * Create a "AT BI48" command to set the ISO Baud rate to 4800.
     * (For protocols ISO 9141-2 and ISO 14230-4)
     *
     * @return A configured "AT BIdd" command
     */
    public static BaudRate create4800() {
        return new BaudRate(48);
    }

    /**
     * Create a "AT BI96" command to set the ISO Baud rate to 9600.
     * (For protocols ISO 9141-2 and ISO 14230-4)
     *
     * @return A configured "AT BIdd" command
     */
    public static BaudRate create9600() {
        return new BaudRate(96);
    }

    /**
     * Create a command to set the ISO Baud rate to the default value (10400)
     *
     * @return A configured "AT BIdd" command
     */
    public static BaudRate createDefault() {
        return create10400();
    }

    @Override
    protected String getCode() {
        return "IB " + speed;
    }

    @Override
    public String minElmVersion() {
        switch (speed) {
            case 10:
                // no break
            case 96:
                return "1.0";
            case 12:
            case 15:
                return "2.2";
            case 48:
                return "1.4";
            default:
                return "?";
        }
    }
}
