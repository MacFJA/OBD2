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
 * <p>This class is the ELM327 command for "AT CV dddd" and "AT CV 0000".</p>
 * <p>Description: <ul>
 * <li>Calibrate the Voltage to dd.dd volts</li>
 * <li>restore CV value to factory setting</li>
 * </ul></p>
 * <p>ELM327 version: <ul>
 * <li>{@code 1.0}</li>
 * <li>{@code 1.4}</li>
 * </ul></p>
 * <p>Response: {@code OK} in case of a success</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
public class CalibrateVoltage extends ATSetCommand {
    private int value;

    public CalibrateVoltage(int value) {
        if (value > 9999) {
            throw new ExcessiveLength();
        }
        this.value = value;
    }

    public static CalibrateVoltage createCalibration(float voltage) {
        return new CalibrateVoltage(new Float(voltage * 100).intValue());
    }

    public static CalibrateVoltage createResetCalibration() {
        return new CalibrateVoltage(0);
    }

    @Override
    protected String getCode() {
        return "CV " + String.format("%04d", value);
    }

    @Override
    public String minElmVersion() {
        if (value == 0) {
            return "1.4";
        }
        return "1.0";
    }
}
