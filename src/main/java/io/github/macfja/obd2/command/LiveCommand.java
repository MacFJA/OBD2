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

package io.github.macfja.obd2.command;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.command.livedata.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Mode 01 commands
 *
 * @author MacFJA
 */
@SuppressWarnings("OverlyCoupledClass")
public abstract class LiveCommand implements Command {
    /**
     * List of all command in the Mode 01
     *
     * @return List of commands
     */
    public static Map<String, Command> getMode01Commands() {
        Map<String, Command> mode01 = new HashMap<>();

        mode01.put("00", SupportedPid.Range01To20);
        mode01.put("01", new StatusSinceLastClean());
        //mode01.put("02", FreezeDiagnosticTroubleCode.class); - Mode 02 only
        mode01.put("03", new FuelSystemStatus());
        mode01.put("04", new CalculatedEngineLoad());
        mode01.put("05", new EngineCoolantTemperature());
        mode01.put("06", FuelTrim.ShortTermBank1);
        mode01.put("07", FuelTrim.LongTermBank1);
        mode01.put("08", FuelTrim.ShortTermBank2);
        mode01.put("09", FuelTrim.LongTermBank2);
        mode01.put("0A", new FuelPressure());
        mode01.put("0B", new IntakeManifoldAbsolutePressure());
        mode01.put("0C", new EngineRPM());
        mode01.put("0D", new VehicleSpeed());
        mode01.put("0E", new TimingAdvance());
        mode01.put("0F", new IntakeAirTemperature());
        mode01.put("10", new AirFlowRate());
        mode01.put("11", new ThrottlePosition());
        mode01.put("12", new CommandedSecondaryAirStatus());
        mode01.put("13", new OxygenSensorsPresent());
        mode01.put("14", OxygenSensor.FuelTrimB1S1);
        mode01.put("15", OxygenSensor.FuelTrimB1S2);
        mode01.put("16", OxygenSensor.FuelTrimB1S3);
        mode01.put("17", OxygenSensor.FuelTrimB1S4);
        mode01.put("18", OxygenSensor.FuelTrimB2S1);
        mode01.put("19", OxygenSensor.FuelTrimB2S2);
        mode01.put("1A", OxygenSensor.FuelTrimB2S3);
        mode01.put("1B", OxygenSensor.FuelTrimB2S4);
        mode01.put("1C", new OBDStandards());
        mode01.put("1D", new FuelAirRationSensorsPresent());
        mode01.put("1E", new AuxilaryInputStatus());
        mode01.put("1F", new RuntimeSinceStart());
        mode01.put("20", SupportedPid.Range21To40);
        mode01.put("21", new DistanceWithMIL());
        mode01.put("22", new RelativeFuelRailPressure());
        mode01.put("23", new FuelRailGaugePressure());
        mode01.put("24", OxygenSensor.FuelAirVoltageB1S1);
        mode01.put("25", OxygenSensor.FuelAirVoltageB1S2);
        mode01.put("26", OxygenSensor.FuelAirVoltageB2S1);
        mode01.put("27", OxygenSensor.FuelAirVoltageB2S2);
        mode01.put("28", OxygenSensor.FuelAirVoltageB3S1);
        mode01.put("29", OxygenSensor.FuelAirVoltageB3S2);
        mode01.put("2A", OxygenSensor.FuelAirVoltageB4S1);
        mode01.put("2B", OxygenSensor.FuelAirVoltageB4S2);
        mode01.put("2C", new CommandedExhaustGasRecirculation());
        mode01.put("2D", new ExhaustGasRecirculationError());
        mode01.put("2E", new CommandedEvaporativePurge());
        mode01.put("2F", new FuelLevel());
        mode01.put("30", new WarmUpsSinceLastClean());
        mode01.put("31", new DistanceSinceLastClean());
        //mode01.put("32", ...);
        mode01.put("33", new BarometricPressure());
        mode01.put("34", OxygenSensor.FuelAirCurrentB1S1);
        mode01.put("35", OxygenSensor.FuelAirCurrentB1S2);
        mode01.put("36", OxygenSensor.FuelAirCurrentB2S1);
        mode01.put("37", OxygenSensor.FuelAirCurrentB2S2);
        mode01.put("38", OxygenSensor.FuelAirCurrentB3S1);
        mode01.put("39", OxygenSensor.FuelAirCurrentB3S2);
        mode01.put("3A", OxygenSensor.FuelAirCurrentB4S1);
        mode01.put("3B", OxygenSensor.FuelAirCurrentB4S2);
        mode01.put("3C", CatalystTemperature.Bank1Sensor1);
        mode01.put("3D", CatalystTemperature.Bank2Sensor1);
        mode01.put("3E", CatalystTemperature.Bank1Sensor2);
        mode01.put("3F", CatalystTemperature.Bank2Sensor2);
        mode01.put("40", SupportedPid.Range41To60);
        mode01.put("41", new StatusSinceStart());
        mode01.put("42", new ControlModuleVoltage());
        mode01.put("43", new AbsoluteLoadValue());
        mode01.put("44", new FuelAirCommandedRatio());
        mode01.put("45", new RelativeThrottlePosition());
        mode01.put("46", new AmbientAirTemperature());
        mode01.put("47", AbsoluteThrottlePosition.ThrottleB);
        mode01.put("48", AbsoluteThrottlePosition.ThrottleC);
        mode01.put("49", AbsoluteThrottlePosition.ThrottleD);
        mode01.put("4A", AbsoluteThrottlePosition.ThrottleE);
        mode01.put("4B", AbsoluteThrottlePosition.ThrottleF);
        mode01.put("4C", new CommandedThrottleActuator());
        mode01.put("4D", new TimeWithMIL());
        mode01.put("4E", new TimeSinceLastClean());
        mode01.put("4F", new MaximumValues());
        mode01.put("50", new MaximumAirFlowRate());
        mode01.put("51", new FuelType());
        mode01.put("52", new EthanolPercent());
        mode01.put("53", new EvapSystemVaporAbsolutePressure());
        mode01.put("54", new EvapSystemVaporPressure());
        mode01.put("55", SecondaryOxygenSensorTrim.ShortTermB1B3);
        mode01.put("56", SecondaryOxygenSensorTrim.LongTermB1B3);
        mode01.put("57", SecondaryOxygenSensorTrim.ShortTermB2B4);
        mode01.put("58", SecondaryOxygenSensorTrim.LongTermB2B4);
        mode01.put("59", new FuelRailAbsolutePressure());
        mode01.put("5A", new RelativeAcceleratorPosition());
        mode01.put("5B", new HybridBatteryRemaining());
        mode01.put("5C", new EngineOilTemperature());
        mode01.put("5D", new FuelInjectionTiming());
        mode01.put("5E", new EngineFuelRate());
        //mode01.put("5F", ...);
        mode01.put("60", SupportedPid.Range61To80);
        mode01.put("61", new RequestedEngineTorque());
        mode01.put("62", new ActualEngineTorque());
        mode01.put("63", new EngineReferenceTorque());

        return mode01;
    }

    /**
     * Get the PID code inside this mode
     *
     * @return The two letter hex code
     */
    public abstract String getCode();

    @Override
    public String getRequest() {
        return "01 " + getCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
