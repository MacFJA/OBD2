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

import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.elm327.ELMCommand;
import io.github.macfja.obd2.elm327.response.ResponseOK;

/**
 * <p>This class is the ELM327 command for "AT SP h" and "AT SP 00".</p>
 * <p>Description: <ul>
 * <li>Set Protocol to h and save it</li>
 * <li>Erase the Stored Protocol</li>
 * </ul></p>
 * <p>ELM327 version: <ul>
 * <li>{@code 1.0}</li>
 * <li>{@code 1.3}</li>
 * </ul></p>
 * <p>Response: {@code OK} in case of a success</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public enum SetProtocol implements ELMCommand {
    /**
     * Auto select protocol and save.
     */
    AUTO('0'),

    /**
     * 41.6 kbaud
     */
    SAE_J1850_PWM('1'),

    /**
     * 10.4 kbaud
     */
    SAE_J1850_VPW('2'),

    /**
     * 5 baud init
     */
    ISO_9141_2('3'),

    /**
     * 5 baud init
     */
    ISO_14230_4_KWP('4'),

    /**
     * Fast init
     */
    ISO_14230_4_KWP_FAST('5'),

    /**
     * 11 bit ID, 500 kbaud
     */
    ISO_15765_4_CAN('6'),

    /**
     * 29 bit ID, 500 kbaud
     */
    ISO_15765_4_CAN_B('7'),

    /**
     * 11 bit ID, 250 kbaud
     */
    ISO_15765_4_CAN_C('8'),

    /**
     * 29 bit ID, 250 kbaud
     */
    ISO_15765_4_CAN_D('9'),

    /**
     * 29 bit ID, 250 kbaud (user adjustable)
     */
    SAE_J1939_CAN('A'),

    /**
     * 11 bit ID (user adjustable), 125 kbaud (user adjustable)
     */
    USER1_CAN('B'),

    /**
     * 11 bit ID (user adjustable), 50 kbaud (user adjustable)
     */
    USER2_CAN('C');

    private final char code;

    SetProtocol(char code) {
        this.code = code;
    }

    /**
     * Create a "AT SP 00" command
     *
     * @return A configured "AT SP 00" command
     */
    public static ATSetCommand createEraseStoreProtocol() {
        return new ATSetCommand() {
            @Override
            public String minElmVersion() {
                return "1.3";
            }

            @Override
            protected String getCode() {
                return "SP 00";
            }
        };
    }

    protected String getCode() {
        return String.valueOf(code);
    }

    public String getRequest() {
        return "AT SP " + getCode();
    }

    @Override
    public Response getResponse(byte[] rawResult) {
        return new ResponseOK(rawResult);
    }

    @Override
    public String minElmVersion() {
        return "1.0";
    }
}
