# Concept

The idea of this library is help anyone that want to interact with a vehicle through the OBD2 standard (available on every consumer vehicles since 1996 in USA, 2003 in Europe).

The goal is to have an Object Oriented way of interaction between your application and the vehicle.

## Main components

The library is based on 3 things:

 - A [commander](Commander.md): The _proxy_ between you and the OBD
 - A [command](Command.md): The data to send
 - A [response](Response.md): The received data

### Command

A command is composed by:

 - The request to send to the OBD
 - How to transform the raw result into a usable response

The idea is to have a Command for every request that an OBD can understand,
because it's easier to understand `IntakeAirTemperature` than `010F`, and the result is just some numbers that don't mean anything.

### Response

The main goal of a Response is to hold data from the request result and provide a nicer access of it.
It can perform some operation to easier to manipulate (ex: transformation into a Number) and to display.

The response also contains information about the Unit of the value (Temperature, Pressure, Distance, _etc._).
The Unit can be used to transform from metric unit (used in all the OBD standard) to imperial unit.

### Commander

The idea of the Commander is to handle all the communications between the program and the OBD.

It abstract the sending of the request, and the reading/transformation of the response.

#### Different Commander

The base commander (`io.github.macfja.obd2.Commander`) implement the interaction with an OBD as we directly talk to it.

The ELM327 commander (`io.github.macfja.obd2.elm327.Commander`) extends the base commander to integrate interaction with an ELM327 chip,
which is the most common chip used to communicate with OBD2.

The ObdSim commander (`io.github.macfja.obd2.commander.ObdSimCommander`) is a simple override of the ELM327 commander to use with the program [ObdSim](https://icculus.org/obdgpslogger/obdsim.html),
as the ObdSim have some difference in how output is generated

## The code

The code is written with developer in mind, all classes have a minimal Javadoc that explain what the class do.

The code also contains comment when the logic can't be understand just by reading the code.

The library is compatible with Java 7.

