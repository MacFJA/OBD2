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

package io.github.macfja.obd2.response;

import java.lang.reflect.Array;

/**
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.CommandedSecondaryAirStatus} command.</p>
 *
 * @author MacFJA
 */
public class SecondaryAirStatusResponse extends RawResponse {
    public SecondaryAirStatusResponse(byte[] rawResult) {
        super(rawResult);
    }

    @Override
    public String getFormattedString() {
        return getStatus().getDescription();
    }

    public Status getStatus() {
        for (SecondaryAirStatusResponse.Status status : SecondaryAirStatusResponse.Status.values()) {
            if (status.getCode() == Array.getByte(getRawResult(), 2)) {
                return status;
            }
        }
        return SecondaryAirStatusResponse.Status.Unknown;

    }

    /**
     * This enum contains the list of possible status of the secondary air system
     *
     * @author MacFJA
     */
    public enum Status {
        Unknown(0, ""),
        Upstream(1, "Upstream"),
        Downstream(2, "Downstream of catalytic converter"),
        OutsideOrOff(4, "From the outside atmosphere or off"),
        Pump(8, "Pump commanded on for diagnostics;");

        private final int code;
        private final String description;

        Status(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }
}
