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
 * <p>This class is the response of a {@link io.github.macfja.obd2.Command} that return a temperature.</p>
 * <p>Unit: Degree Celsius (°C)</p>
 *
 * @author MacFJA
 */
public class TemperatureResponse extends CalculatedResponse {

    public TemperatureResponse(byte[] raw) {
        super(raw, getIntValue(raw, 0) - 40);
    }

    public TemperatureResponse(byte[] raw, String equation) throws ScriptException {
        super(raw, equation);
    }

    public Unit getUnit() {
        return Unit.DegreeCelsius;
    }
}
