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
 * <p>This class is the ELM327 command for "AT TP h" and "AT TP Ah".</p>
 * <p>Description: <ul>
 * <li>Try Protocol h</li>
 * <li>Try Protocol h with Auto</li>
 * </ul></p>
 * <p>ELM327 version: {@code 1.0}</p>
 * <p>Response: ?</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public class TryProtocol extends ATCommand {
    private final SetProtocol protocol;
    private final boolean automatic;

    public TryProtocol(SetProtocol protocol) {
        this(protocol, false);
    }

    private TryProtocol(SetProtocol protocol, boolean automatic) {
        this.protocol = protocol;
        this.automatic = automatic;
    }

    public static TryProtocol createAutomatic(SetProtocol protocol) {
        return new TryProtocol(protocol, true);
    }

    protected String getCode() {
        return "TP " + (automatic ? "A" : "") + protocol.getCode();
    }

    @Override
    public String minElmVersion() {
        return "1.0";
    }
}
