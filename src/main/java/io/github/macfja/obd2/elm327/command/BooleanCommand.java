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
 * This enum contains ON/OFF ELM327 commands.
 */
public enum BooleanCommand implements ELMCommand {
    /**
     * Command for "AT E1":
     * Set Echo to On <b>(default)</b>
     */
    EchoOn("E", true),
    /**
     * Command for "AT E0":
     * Set Echo to Off
     */
    EchoOff("E", false),
    /**
     * Command for "AT L1":
     * Set Linefeed to On
     */
    LinefeedOn("L", true),
    /**
     * Command for "AT L0":
     * Set Linefeed to Off
     */
    LinefeedOff("L", false),
    /**
     * Command for "AT M1":
     * Set Memory to On
     */
    MemoryOn("M", true),
    /**
     * Command for "AT M0":
     * Set Memory to Off
     */
    MemoryOff("M", false),
    /**
     * Command for "AT H1":
     * Set Header to On
     */
    HeaderOn("H", true),
    /**
     * Command for "AT H0":
     * Set Header to Off <b>(default)</b>
     */
    HeaderOff("H", false),
    /**
     * Command for "AT R1":
     * Set Response to On <b>(default)</b>
     */
    ResponseOn("R", true),
    /**
     * Command for "AT R0":
     * Set Response to Off
     */
    ResponseOff("R", false),
    /**
     * Command for "AT S1":
     * Set Space to On <b>(default)</b>
     */
    SpaceOn("S", true),
    /**
     * Command for "AT S0":
     * Set Space to Off
     */
    SpaceOff("S", false),
    /**
     * Command for "AT KW1":
     * Set Key Words to On <b>(default)</b>
     */
    KeywordOn("KW", true),
    /**
     * Command for "AT KW0":
     * Set Key Words to Off
     */
    KeywordOff("KW", false),
    /**
     * Command for "AT CAF1":
     * Set Automatic Formatting to On <b>(default)</b>
     */
    AutomaticFormattingOn("CAF", true),
    /**
     * Command for "AT CAF0":
     * Set Automatic Formatting to Off
     */
    AutomaticFormattingOff("CAF", false),
    /**
     * Command for "AT CFC1":
     * Set Flow Control to On <b>(default)</b>
     */
    FlowControlOn("CFC", true),
    /**
     * Command for "AT CFC0":
     * Set Flow Control to Off
     */
    FlowControlOff("CFC", false),
    /**
     * Command for "AT CSM1":
     * Set Silence Monitoring to On <b>(default)</b>
     */
    SilenceMonitoringOn("CSM", true),
    /**
     * Command for "AT CSM0":
     * Set Silence Monitoring to Off
     */
    SilenceMonitoringOff("CSM", false),
    /**
     * Command for "AT D1":
     * Set Display of the DLC to On
     */
    DisplayDLCOn("D", true),
    /**
     * Command for "AT D0":
     * Set Display of the DLC to Off <b>(default)</b>
     */
    DisplayDLCOff("D", false),
    /**
     * Set Use of Variable DLC to On
     */
    DLCVariableOn("V", true),
    /**
     * Set Use of Variable DLC to Off <b>(default)</b>
     */
    DLCVariableOff("V", false),
    /**
     * Command for "AT JHF1":
     * Set Header Formatting to On <b>(default)</b>
     */
    HeaderFormattingOn("JHF", true),
    /**
     * Command for "AT JHF0":
     * Set Header Formatting to Off
     */
    HeaderFormattingOff("JHF", false);

    private final boolean onVersion;
    private final String prefix;

    BooleanCommand(String prefix, boolean onVersion) {
        this.onVersion = onVersion;
        this.prefix = prefix;
    }

    /**
     * Get a command by its prefix and its mode
     *
     * @param prefix    One of ("E", "L", "M", "H", "R", "S", "KW", "CAF", "CFC", "CSM", "D", "V" or "JHF")
     * @param onVersion {@code true} to enable the feature
     * @return The corresponding command, or {@code null} if the command don't exist
     */
    public static BooleanCommand getCommand(String prefix, boolean onVersion) {
        for (BooleanCommand command : values()) {
            if (command.prefix.equals(prefix) && command.onVersion == onVersion) {
                return command;
            }
        }
        return null;
    }

    /**
     * Get the flag prefix (One of "E", "L", "M", "H", "R", "S", "KW", "CAF", "CFC", "CSM", "D", "V" or "JHF")
     *
     * @return The prefix letter(s)
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Indicate if the command set the flag to {@code ON} or {@code OFF}
     *
     * @return {@code true} if it's the ON version, {@code false} otherwise
     */
    public boolean isOnVersion() {
        return onVersion;
    }

    /**
     * Get the command code (flag letter(s) + on/off)
     *
     * @return The code to put after "AT"
     */
    protected String getCode() {
        return prefix + (onVersion ? "1" : "0");
    }

    @Override
    public String getRequest() {
        return "AT " + getCode();
    }

    @Override
    public Response getResponse(byte[] rawResult) {
        return new ResponseOK(rawResult);
    }

    @Override
    public String minElmVersion() {
        switch (prefix) {
            case "E":
            case "L":
            case "M":
            case "H":
            case "R":
            case "CAF":
            case "CFC":
            case "D":
                return "1.0";
            case "S":
            case "V":
                return "1.3";
            case "KW":
                return "1.2";
            case "CSM":
            case "JHF":
                return "1.4b";
        }
        return "?";
    }
}
