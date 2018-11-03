# Simple GUI application

This example is a really simple application (Swing) to display:

 - The vehicle speed
 - The engine RPM
 - The engine coolant temperature
 - The list of all current diagnostic trouble code (DTC)
 - A button to clear trouble code

## Refresh rate

The vehicle speed, the engine RPM and the coolant temperature are refreshed every 2 seconds.
The list of trouble code is refreshed every 10 seconds.

## Usage

Currently the sample work with unix file stream (a file reader and a file writer are used to interact the OBD).

2 parameters can (must) be provide:

 1. The type of connector (`obdsim`, `serial`)
 1. The information about the connector (the id of the pseudo console for `obdsim`, the path to the file stream for `serial`)

### Example

In a console:
```
$ obsim -g Random
SimPort name: /dev/pts/11
Successfully initialised obdsim, entering main loop
```
In an other console:
```
java -classpath ${your-jre}:${obd2-jar} Application obdsim 11
```