# Response

A response is the representation of the result of a Command.

## Structure of a Response

A response is a object that hold the result of a command.
The idea is to have an object know what the data is, and how to render it.

## Type of response

The most of the OBD2 is reading value from sensor.
A sensor is a device that transform physic data into an electric signal.

The OBD2 only return the value of the signal.
It's the job of the Response to transform this value into the physic data that have been catch by the sensor.
So the response know what physic data have been catch (via the unit of the physic data) and how to transform the signal value into number to put in front of the unit.
With is two information, the Response object is capable to transform a raw result into a human readable information.

The number of type of sensor is fairly small, and to avoid to write several times the same logic to transform data, there an a number of predefined Response:

 - `io.github.macfja.obd2.response.TemperatureResponse`: Transform raw data into a temperature data (most of sensor measure data between -40°C and 215°C, so it's the default calculation)
 - `io.github.macfja.obd2.response.PressureResponse`: Transform raw data into a pressure data
 - `io.github.macfja.obd2.response.Percent`: Transform raw data into a variation/percentage

There are others responses that are binary data (<=> Yes or No):

 - `io.github.macfja.obd2.response.AuxilaryInputResponse`
 - `io.github.macfja.obd2.response.AvailableOxygenSensorMonitorResponse`
 - `io.github.macfja.obd2.response.AvailablePidResponse`
 - `io.github.macfja.obd2.response.SensorPresentResponse`

Some responses contains multiple data in one request:

 - `io.github.macfja.obd2.response.SecondaryOxygenSensorTrimResponse`
 - `io.github.macfja.obd2.response.OxygenSensorFuelTrimResponse`
 - `io.github.macfja.obd2.response.OxygenSensorFuelAirVoltageResponse`
 - `io.github.macfja.obd2.response.OxygenSensorFuelAirCurrentResponse`
 - `io.github.macfja.obd2.response.MaximumValuesResponse`
 - `io.github.macfja.obd2.response.FuelSystemStatusResponse`