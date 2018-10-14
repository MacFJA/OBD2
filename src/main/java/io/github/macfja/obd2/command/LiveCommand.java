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
 * Service 01 commands
 *
 * @author MacFJA
 */
@SuppressWarnings("OverlyCoupledClass")
public abstract class LiveCommand implements Command {
    /**
     * List of all command in the Service 01
     *
     * @return List of commands
     */
    public static Map<String, Command> getService01Commands() {
        Map<String, Command> service01 = new HashMap<>();

        service01.put("00", SupportedPid.Range01To20);
        service01.put("01", new StatusSinceLastClean());
        //service01.put("02", FreezeDiagnosticTroubleCode.class); - Service 02 only
        service01.put("03", new FuelSystemStatus());
        service01.put("04", new CalculatedEngineLoad());
        service01.put("05", new EngineCoolantTemperature());
        service01.put("06", FuelTrim.ShortTermBank1);
        service01.put("07", FuelTrim.LongTermBank1);
        service01.put("08", FuelTrim.ShortTermBank2);
        service01.put("09", FuelTrim.LongTermBank2);
        service01.put("0A", new FuelPressure());
        service01.put("0B", new IntakeManifoldAbsolutePressure());
        service01.put("0C", new EngineRPM());
        service01.put("0D", new VehicleSpeed());
        service01.put("0E", new TimingAdvance());
        service01.put("0F", new IntakeAirTemperature());
        service01.put("10", new AirFlowRate());
        service01.put("11", new ThrottlePosition());
        service01.put("12", new CommandedSecondaryAirStatus());
        service01.put("13", new OxygenSensorsPresent());
        service01.put("14", OxygenSensor.FuelTrimB1S1);
        service01.put("15", OxygenSensor.FuelTrimB1S2);
        service01.put("16", OxygenSensor.FuelTrimB1S3);
        service01.put("17", OxygenSensor.FuelTrimB1S4);
        service01.put("18", OxygenSensor.FuelTrimB2S1);
        service01.put("19", OxygenSensor.FuelTrimB2S2);
        service01.put("1A", OxygenSensor.FuelTrimB2S3);
        service01.put("1B", OxygenSensor.FuelTrimB2S4);
        service01.put("1C", new OBDStandards());
        service01.put("1D", new FuelAirRationSensorsPresent());
        service01.put("1E", new AuxilaryInputStatus());
        service01.put("1F", new RuntimeSinceStart());
        service01.put("20", SupportedPid.Range21To40);
        service01.put("21", new DistanceWithMIL());
        service01.put("22", new RelativeFuelRailPressure());
        service01.put("23", new FuelRailGaugePressure());
        service01.put("24", OxygenSensor.FuelAirVoltageB1S1);
        service01.put("25", OxygenSensor.FuelAirVoltageB1S2);
        service01.put("26", OxygenSensor.FuelAirVoltageB2S1);
        service01.put("27", OxygenSensor.FuelAirVoltageB2S2);
        service01.put("28", OxygenSensor.FuelAirVoltageB3S1);
        service01.put("29", OxygenSensor.FuelAirVoltageB3S2);
        service01.put("2A", OxygenSensor.FuelAirVoltageB4S1);
        service01.put("2B", OxygenSensor.FuelAirVoltageB4S2);
        service01.put("2C", new CommandedExhaustGasRecirculation());
        service01.put("2D", new ExhaustGasRecirculationError());
        service01.put("2E", new CommandedEvaporativePurge());
        service01.put("2F", new FuelLevel());
        service01.put("30", new WarmUpsSinceLastClean());
        service01.put("31", new DistanceSinceLastClean());
        //service01.put("32", ...);
        service01.put("33", new BarometricPressure());
        service01.put("34", OxygenSensor.FuelAirCurrentB1S1);
        service01.put("35", OxygenSensor.FuelAirCurrentB1S2);
        service01.put("36", OxygenSensor.FuelAirCurrentB2S1);
        service01.put("37", OxygenSensor.FuelAirCurrentB2S2);
        service01.put("38", OxygenSensor.FuelAirCurrentB3S1);
        service01.put("39", OxygenSensor.FuelAirCurrentB3S2);
        service01.put("3A", OxygenSensor.FuelAirCurrentB4S1);
        service01.put("3B", OxygenSensor.FuelAirCurrentB4S2);
        service01.put("3C", CatalystTemperature.Bank1Sensor1);
        service01.put("3D", CatalystTemperature.Bank2Sensor1);
        service01.put("3E", CatalystTemperature.Bank1Sensor2);
        service01.put("3F", CatalystTemperature.Bank2Sensor2);
        service01.put("40", SupportedPid.Range41To60);
        service01.put("41", new StatusSinceStart());
        service01.put("42", new ControlModuleVoltage());
        service01.put("43", new AbsoluteLoadValue());
        service01.put("44", new FuelAirCommandedRatio());
        service01.put("45", new RelativeThrottlePosition());
        service01.put("46", new AmbientAirTemperature());
        service01.put("47", AbsoluteThrottlePosition.ThrottleB);
        service01.put("48", AbsoluteThrottlePosition.ThrottleC);
        service01.put("49", AbsoluteThrottlePosition.ThrottleD);
        service01.put("4A", AbsoluteThrottlePosition.ThrottleE);
        service01.put("4B", AbsoluteThrottlePosition.ThrottleF);
        service01.put("4C", new CommandedThrottleActuator());
        service01.put("4D", new TimeWithMIL());
        service01.put("4E", new TimeSinceLastClean());
        service01.put("4F", new MaximumValues());
        service01.put("50", new MaximumAirFlowRate());
        service01.put("51", new FuelType());
        service01.put("52", new EthanolPercent());
        service01.put("53", new EvapSystemVaporAbsolutePressure());
        service01.put("54", new EvapSystemVaporPressure());
        service01.put("55", SecondaryOxygenSensorTrim.ShortTermB1B3);
        service01.put("56", SecondaryOxygenSensorTrim.LongTermB1B3);
        service01.put("57", SecondaryOxygenSensorTrim.ShortTermB2B4);
        service01.put("58", SecondaryOxygenSensorTrim.LongTermB2B4);
        service01.put("59", new FuelRailAbsolutePressure());
        service01.put("5A", new RelativeAcceleratorPosition());
        service01.put("5B", new HybridBatteryRemaining());
        service01.put("5C", new EngineOilTemperature());
        service01.put("5D", new FuelInjectionTiming());
        service01.put("5E", new EngineFuelRate());
        //service01.put("5F", ...);
        service01.put("60", SupportedPid.Range61To80);
        service01.put("61", new RequestedEngineTorque());
        service01.put("62", new ActualEngineTorque());
        service01.put("63", new EngineReferenceTorque());

        return service01;
    }

    /**
     * List of all command in the Mode 01
     *
     * @deprecated Since 1.1.0, replaced by {@link #getService01Commands()}
     * @return List of commands
     */
    @Deprecated
    public static Map<String, Command> getMode01Commands() {
        return getService01Commands();
    }

    /**
     * Get the PID code inside this service
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
