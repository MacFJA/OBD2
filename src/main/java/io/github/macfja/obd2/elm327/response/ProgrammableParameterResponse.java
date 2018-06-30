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

package io.github.macfja.obd2.elm327.response;

import io.github.macfja.obd2.elm327.command.ProgrammableParameter;
import io.github.macfja.obd2.response.RawResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the response of the command {@link io.github.macfja.obd2.elm327.command.ProgrammableParameterSummary}.
 *
 * @author MacFJA
 */
public class ProgrammableParameterResponse extends RawResponse {
    private final Map<Integer, ParameterValue> parameters = new HashMap<>();

    public ProgrammableParameterResponse(byte[] rawResult) {
        super(rawResult);

        Pattern pattern = Pattern.compile(
                "(?<code>[a-z0-9]{2}):(?<value>[a-z0-9]{2}) (?<status>[N|F])",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
        );

        String allResult = getFormattedString();
        Matcher regex = pattern.matcher(allResult);

        while (regex.find()) {
            ParameterValue parameter = new ParameterValue(
                    Integer.parseInt(regex.group("code"), 16),
                    Integer.parseInt(regex.group("value"), 16),
                    regex.group("status").equals("N")
            );
            parameters.put(parameter.getCode(), parameter);
        }
    }

    public boolean isParameterActive(int code) {
        if (!parameters.containsKey(code)) {
            return false;
        }
        return parameters.get(code).isActive();
    }

    public boolean isParameterActive(ProgrammableParameter.Parameter parameter) {
        return isParameterActive(parameter.getCode());
    }

    public int getParameterValue(ProgrammableParameter.Parameter parameter) {
        return getParameterValue(parameter.getCode());
    }

    public int getParameterValue(int code) {
        if (!parameters.containsKey(code)) {
            return 0;
        }
        return parameters.get(code).getValue();
    }

    private class ParameterValue {
        private final int code;
        private final int value;
        private final boolean active;

        public ParameterValue(int code, int value, boolean active) {
            this.code = code;
            this.value = value;
            this.active = active;
        }

        public int getCode() {
            return code;
        }

        public int getValue() {
            return value;
        }

        public boolean isActive() {
            return active;
        }
    }
}
