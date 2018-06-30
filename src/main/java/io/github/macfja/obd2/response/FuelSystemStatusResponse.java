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
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.FuelSystemStatus} command.</p>
 *
 * @author MacFJA
 */
public class FuelSystemStatusResponse extends RawResponse {
    public FuelSystemStatusResponse(byte[] rawResult) {
        super(rawResult);
    }

    @Override
    public String getFormattedString() {
        Status system1 = getStatus();
        Status system2 = getSecondaryStatus();

        if (system2.equals(Status.Unknown)) {
            return system1.getDescription();
        }

        return system1.getDescription() + System.lineSeparator() + system2.getDescription();
    }

    public Status getStatus() {
        for (Status status : Status.values()) {
            if (status.getCode() == Array.getByte(getRawResult(), 2)) {
                return status;
            }
        }
        return Status.Unknown;
    }

    public Status getSecondaryStatus() {
        for (Status status : Status.values()) {
            if (status.getCode() == Array.getByte(getRawResult(), 3)) {
                return status;
            }
        }
        return Status.Unknown;
    }

    /**
     * This enum contains ile list of possible fuel system status
     *
     * @author MacFJA
     */
    public enum Status {
        Unknown(0, ""),
        // Description from Wikipedia (December 2017)
        OpenForLowTemperature(1, "Open loop due to insufficient engine temperature"),
        CloseForSearchFuelMix(2, "Closed loop, using oxygen sensor feedback to determine fuel mix"),
        OpenForLoadOrDeceleration(4, "Open loop due to engine load OR fuel cut due to deceleration"),
        OpenForFailure(8, "Open loop due to system failure"),
        CloseForFallback(16, "Closed loop, using at least one oxygen sensor but there is a fault in the feedback system");

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
