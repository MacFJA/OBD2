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
 * <p>This class is the ELM327 command for "PP xx OFF", "PP FF OFF", "PP xx ON", "PP FF ON" and "PP xx SV yy".</p>
 * <p>Description: <ul>
 * <li>disable Prog Parameter xx</li>
 * <li>all Prog Parameters disabled</li>
 * <li>enable Prog Parameter xx</li>
 * <li>all Prog Parameters enabled</li>
 * <li>for PP xx, Set the Value to yy</li>
 * </ul></p>
 * <p>ELM327 version: {@code 1.1} (Note that some parameters only available on version {@code 1.2})</p>
 * <p>Response: ?</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public class ProgrammableParameter extends ATCommand {
    private Mode mode;
    private int identifier;
    private Integer value;

    private ProgrammableParameter(Mode mode, int identifier, Integer value) {
        if (identifier > 255) {
            throw new ExcessiveLength();
        }
        if (value != null && value > 255) {
            throw new ExcessiveLength();
        }
        this.mode = mode;
        this.identifier = identifier;
        this.value = value;
    }

    private ProgrammableParameter(int identifier, Integer value) {
        this(Mode.Set, identifier, value);
    }

    private ProgrammableParameter(Mode mode, int identifier) {
        this(mode, identifier, null);
    }

    /**
     * Create a "AT PP xx ON" command
     *
     * @param code The code of the parameter
     * @return A configured "AT PP xx ON" command
     */
    public static ProgrammableParameter createActivate(int code) {
        return new ProgrammableParameter(Mode.On, code);
    }

    /**
     * Create a "AT PP xx ON" command
     *
     * @param parameter The parameter to activate
     * @return A configured "AT PP xx ON" command
     */
    public static ProgrammableParameter createActivate(Parameter parameter) {
        return createActivate(parameter.getCode());
    }

    /**
     * Create a "AT PP FF ON" command
     *
     * @return A configured "AT PP FF ON" command
     */
    public static ProgrammableParameter createActivateAll() {
        return new ProgrammableParameter(Mode.On, 255);
    }

    /**
     * Create a "AT PP xx OFF" command
     *
     * @param code The code of the parameter
     * @return A configured "AT PP xx OFF" command
     */
    public static ProgrammableParameter createDeactivate(int code) {
        return new ProgrammableParameter(Mode.Off, code);
    }

    /**
     * Create a "AT PP xx OF" command
     *
     * @param parameter The parameter to disable
     * @return A configured "AT PP xx OFF" command
     */
    public static ProgrammableParameter createDeactivate(Parameter parameter) {
        return createDeactivate(parameter.getCode());
    }

    /**
     * Create a "AT PP FF OFF" command
     *
     * @return A configured "AT PP FF OFF" command
     */
    public static ProgrammableParameter createDeactivateAll() {
        return new ProgrammableParameter(Mode.Off, 255);
    }

    /**
     * Create a "AT PP xx SV yy" command
     *
     * @param code  The code of the parameter
     * @param value The value to set
     * @return A configured "AT PP xx SV yy" command
     */
    public static ProgrammableParameter createSetValue(int code, int value) {
        return new ProgrammableParameter(code, value);
    }

    /**
     * Create a "AT PP xx SV yy" command
     *
     * @param parameter The parameter to change
     * @param value     The value to set
     * @return A configured "AT PP xx SV yy" command
     */
    public static ProgrammableParameter createSetValue(Parameter parameter, int value) {
        return createSetValue(parameter.getCode(), value);
    }

    @Override
    protected String getCode() {
        return mode.getCode(identifier, value);
    }

    @Override
    public String minElmVersion() {
        return "1.1";
    }

    /**
     * List of modes
     */
    private enum Mode {
        /**
         * Enable a parameter
         */
        On("PP %s ON"),
        /**
         * Disable a parameter
         */
        Off("PP %s OFF"),
        /**
         * Set the value of a parameter
         */
        Set("PP %s SV %s");

        /**
         * The printf like format
         */
        private final String pattern;

        Mode(String pattern) {
            this.pattern = pattern;
        }

        public String getCode(int parameterNumber, Integer parameterValue) {
            if (parameterValue == null) {
                return String.format(pattern, parameterNumber);
            }
            return String.format(pattern, parameterNumber, parameterValue);
        }
    }

    /**
     * List of all programmable parameter
     */
    public enum Parameter {
        DefaultPrintingHeader(1, "1.1"),
        DefaultAllowLongMessage(2, "1.1"),
        DefaultTimeout(3, "1.1"),
        DefaultEcho(9, "1.1"),
        Linefeed(10, "1.1"),
        CarriageReturn(13, "1.1"),
        DefaultBaudRate(22, "1.1"),
        DefaultWakeup(23, "1.1"),
        MonitorAllMessageAtStart(0, "1.2"),
        DefaultAdaptiveTiming(4, "1.2"),
        LastAutomaticTryProtocol(7, "1.2");

        private final int code;
        private final String elmVersion;

        Parameter(int code, String elmVersion) {
            this.code = code;
            this.elmVersion = elmVersion;
        }

        public int getCode() {
            return code;
        }

        public String getMinElmVersion() {
            return elmVersion;
        }
    }
}
