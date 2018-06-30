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

import javax.script.ScriptException;

/**
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.OxygenSensor} command.</p>
 *
 * @author MacFJA
 */
public class OxygenSensorFuelAirCurrentResponse extends RawResponse {

    public OxygenSensorFuelAirCurrentResponse(byte[] rawResult) {
        super(rawResult);
    }

    @Override
    public String getFormattedString() {
        try {
            return getFormattedCurrent() + " / " + getFormattedFuelAirEquivalenceRatio();
        } catch (ScriptException e) {
            return "ERROR";
        }
    }

    public String getFormattedCurrent() throws ScriptException {
        return getCalculatedCurrent() + Unit.Milliampere.getSymbol();
    }

    public Number getCalculatedCurrent() throws ScriptException {
        return CalculatedResponse.calculateFromEquation(getRawResult(), "(256 * C + D) / 256 - 128");
    }

    public String getFormattedFuelAirEquivalenceRatio() throws ScriptException {
        return getCalculatedFuelAirEquivalenceRatio() + Unit.NoUnit.getSymbol();
    }

    public Number getCalculatedFuelAirEquivalenceRatio() throws ScriptException {
        return CalculatedResponse.calculateFromEquation(getRawResult(), "(2 / 65536) * (256 * A + B)");
    }

    @Override
    public Unit getUnit() {
        return Unit.Multiple;
    }
}
