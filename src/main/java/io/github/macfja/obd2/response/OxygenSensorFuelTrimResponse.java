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

import io.github.macfja.obd2.Unit;

/**
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.OxygenSensor} command.</p>
 *
 * @author MacFJA
 */
public class OxygenSensorFuelTrimResponse extends RawResponse {

    public OxygenSensorFuelTrimResponse(byte[] rawResult) {
        super(rawResult);
    }

    @Override
    public String getFormattedString() {
        return getFormattedVoltage() + " / " + getFormattedShortTermFuelTrim();
    }

    public String getFormattedVoltage() {
        return getCalculatedVoltage() + Unit.Volt.getSymbol();
    }

    public Number getCalculatedVoltage() {
        return CalculatedResponse.getIntValue(getRawResult(), 0) / 200;
    }

    public String getFormattedShortTermFuelTrim() {
        return getCalculatedShortTermFuelTrim() + Unit.Percent.getSymbol();
    }

    public Number getCalculatedShortTermFuelTrim() {
        return CalculatedResponse.getIntValue(getRawResult(), 1) / 1.28 - 100;
    }

    @Override
    public Unit getUnit() {
        return Unit.Multiple;
    }
}
