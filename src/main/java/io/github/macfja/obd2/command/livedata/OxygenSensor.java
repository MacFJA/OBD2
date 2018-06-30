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

package io.github.macfja.obd2.command.livedata;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.exception.ExceptionResponse;
import io.github.macfja.obd2.response.OxygenSensorFuelAirCurrentResponse;
import io.github.macfja.obd2.response.OxygenSensorFuelAirVoltageResponse;
import io.github.macfja.obd2.response.OxygenSensorFuelTrimResponse;

/**
 * <p>This class is the OBD-II command for multiple commands: "01 14" to "01 1B", "01 24" to "01 2B" and "01 34" to "01 3B".</p><p>The commands are: <ul>
 * <li>"01 14" (Mode 01, PID 0x14), "01 15" (Mode 01, PID 0x15),
 * "01 16" (Mode 01, PID 0x16), "01 17" (Mode 01, PID 0x17), "01 18" (Mode 01, PID 0x18), "01 19" (Mode 01, PID 0x19),
 * "01 1A" (Mode 01, PID 0x1A), "01 1B" (Mode 01, PID 0x1B)</li>
 * <li>"01 24" (Mode 01, PID 0x24), "01 25" (Mode 01, PID 0x25),
 * "01 26" (Mode 01, PID 0x26), "01 27" (Mode 01, PID 0x27), "01 28" (Mode 01, PID 0x28), "01 29" (Mode 01, PID 0x29),
 * "01 2A" (Mode 01, PID 0x2A), "01 2B" (Mode 01, PID 0x2B)</li>
 * <li>"01 34" (Mode 01, PID 0x34), "01 35" (Mode 01, PID 0x35),
 * "01 36" (Mode 01, PID 0x36), "01 37" (Mode 01, PID 0x37), "01 38" (Mode 01, PID 0x38), "01 39" (Mode 01, PID 0x39),
 * "01 3A" (Mode 01, PID 0x3A), "01 3B" (Mode 01, PID 0x3B)</li>
 * </ul></p>
 * <p>Description: <ul>
 * <li>Oxygen Sensor 1<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 2<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 3<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 4<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 5<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 6<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 7<br />A: Voltage<br />B: Short term fuel trim</li>
 * <li>Oxygen Sensor 8<br />A: Voltage<br />B: Short term fuel trim</li>
 * <p>
 * <li>Oxygen Sensor 1<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 2<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 3<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 4<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 5<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 6<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 7<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <li>Oxygen Sensor 8<br />AB: Fuel–Air Equivalence Ratio<br />CD: Voltage</li>
 * <p>
 * <li>Oxygen Sensor 1<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 2<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 3<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 4<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 5<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 6<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 7<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * <li>Oxygen Sensor 8<br />AB: Fuel–Air Equivalence Ratio<br />CD: Current</li>
 * </ul></p>
 * <p>The response:
 * <table border="1">
 * <tr><th>Size</th>     <td>2 bytes, 4 bytes, 4 bytes</td></tr>
 * <tr><th>Unit</th>     <td>    V | %,    - | V, - | mA</td></tr>
 * <tr><th>Min value</th><td>    0 | -100, 0 | 0, 0 | -128</td></tr>
 * <tr><th>Max value</th><td>1.275 | 99.2, 2 | 8, 2 | 128</td></tr>
 * <tr><th>Equation</th> <td><ul>
 * <li><pre>
 *  A   |  100
 * ---  |  --- * B - 100
 * 200  |  128
 *         </pre></li>
 * <li><pre>
 *   2                    |    8
 * ----- * (256 * A + B)  |  ----- * (256 * C + D)
 * 65536                  |  65536
 *         </pre></li>
 * <li><pre>
 *   2                    |       D
 * ----- * (256 * A + B)  |  C + --- - 128
 * 65536                  |      256
 *         </pre></li>
 * </ul></td></tr>
 * </table></p>
 *
 * @author MacFJA
 * @see <a href="https://en.wikipedia.org/wiki/OBD-II_PIDs#Mode_01">Wikipedia</a>
 */
public enum OxygenSensor implements Command {
    FuelTrimB1S1("14", 1, 1, Type.FuelTrim),
    FuelTrimB1S2("15", 1, 2, Type.FuelTrim),
    FuelTrimB1S3("16", 1, 3, Type.FuelTrim),
    FuelTrimB1S4("17", 1, 4, Type.FuelTrim),
    FuelTrimB2S1("18", 2, 1, Type.FuelTrim),
    FuelTrimB2S2("19", 2, 2, Type.FuelTrim),
    FuelTrimB2S3("1A", 2, 3, Type.FuelTrim),
    FuelTrimB2S4("1B", 2, 4, Type.FuelTrim),

    FuelAirVoltageB1S1("24", 1, 1, Type.FuelAirVoltage),
    FuelAirVoltageB1S2("25", 1, 2, Type.FuelAirVoltage),
    FuelAirVoltageB2S1("26", 2, 1, Type.FuelAirVoltage),
    FuelAirVoltageB2S2("27", 2, 1, Type.FuelAirVoltage),
    FuelAirVoltageB3S1("28", 3, 1, Type.FuelAirVoltage),
    FuelAirVoltageB3S2("29", 3, 2, Type.FuelAirVoltage),
    FuelAirVoltageB4S1("2A", 4, 1, Type.FuelAirVoltage),
    FuelAirVoltageB4S2("2B", 4, 2, Type.FuelAirVoltage),

    FuelAirCurrentB1S1("34", 1, 1, Type.FuelAirCurrent),
    FuelAirCurrentB1S2("35", 1, 2, Type.FuelAirCurrent),
    FuelAirCurrentB2S1("36", 2, 1, Type.FuelAirCurrent),
    FuelAirCurrentB2S2("37", 2, 1, Type.FuelAirCurrent),
    FuelAirCurrentB3S1("38", 3, 1, Type.FuelAirCurrent),
    FuelAirCurrentB3S2("39", 3, 2, Type.FuelAirCurrent),
    FuelAirCurrentB4S1("3A", 4, 1, Type.FuelAirCurrent),
    FuelAirCurrentB4S2("3B", 4, 2, Type.FuelAirCurrent);

    private final String code;
    private final int bank;
    private final int sensor;
    private final Type type;

    OxygenSensor(String code, int bank, int sensor, Type type) {
        this.code = code;
        this.bank = bank;
        this.sensor = sensor;
        this.type = type;
    }

    public static OxygenSensor get(int bank, int sensor, Type type) {
        for (OxygenSensor oxygenSensor : OxygenSensor.values()) {
            if (oxygenSensor.bank == bank && oxygenSensor.sensor == sensor && oxygenSensor.type == type) {
                return oxygenSensor;
            }
        }
        return null;
    }

    @Override
    public String getRequest() {
        return "01 " + code;
    }

    @Override
    public Response getResponse(byte[] rawResult) {
        switch (type) {
            case FuelAirCurrent:
                return new OxygenSensorFuelAirCurrentResponse(rawResult);
            case FuelAirVoltage:
                return new OxygenSensorFuelAirVoltageResponse(rawResult);
            case FuelTrim:
                return new OxygenSensorFuelTrimResponse(rawResult);
            default:
                return new ExceptionResponse(rawResult);
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "." + super.toString();
    }

    enum Type {
        FuelAirVoltage,
        FuelAirCurrent,
        FuelTrim
    }
}
