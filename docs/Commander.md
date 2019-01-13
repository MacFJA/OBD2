# Commander

The principe of the commander is: You give it a Command, and you receive a Response.
Nothing more, nothing less.

The Commander hide all communication part between you and the OBD2.

## Different Commander

There are currently 5 commanders:

 - The OBD commander (`io.github.macfja.obd2.Commander`)
 - The ELM commander (`io.github.macfja.obd2.elm327.Commander`)
 - The ObdSim commander (`io.github.macfja.obd2.commander.ObdSimCommander`)
 - The OBD Supported commander (`io.github.macfja.obd2.command.SupportedCommander`)
 - The ELM Supported commander (`io.github.macfja.obd2.elm327.SupportedCommander`)

The supported commanders are decorators that add a custom logic to check if a command is supported or not.

### OBD commander

This is the base commander, it set all the needed part to send and receive data.
But it doesn't know anything particular about command except if they are persistent or not.

### ELM commander

This commander all the compatibility layer with the ELM327 chip. The ELM327 chip is the most used chip for communicating with a vehicle through OBD2 standard.

This commander add the need logic to handle response of ELM327 (headers, spaces, custom text response, _etc._).
It also provide a method to reduce the quantity of data in both sending and receiving.
It handle the case of repetitive command.

### ObdSim commander

This commander is a simple override of the ELM327 commander to use with the program [ObdSim](https://icculus.org/obdgpslogger/obdsim.html).
The program ObdSim simulate the behavior of an ELM, but some difference in how output is generated.

### OBD Supported commander

This decorator add the ability to use OBD Supported PID commands (0100, 0120, 0140, 0160, 0180, 01A0, 01C0)
to check is an OBD command is supported by the vehicle before trying to send it.

### ELM Supported commander

This decorator add the ability to check if an ELMCommand is supported by the ELM327 chip.

## Examples

### A full featured commander

```java
io.github.macfja.obd2.commander.CommanderInterface commander = new io.github.macfja.obd2.elm327.SupportedCommander(
    new io.github.macfja.obd2.command.SupportedCommander(
        new io.github.macfja.obd2.elm327.Commander()
    )
);
commander.setCommunicationInterface(..., ...);
Response response = commander.sendCommand(CatalystTemperature.Bank1Sensor1);
// Will first call SupportedPid.Range01To20
// Then call SupportedPid.Range21To40 if it's supported
// Then call CatalystTemperature.Bank1Sensor1 if it's supported
if (response instanceof TemperaturResponse) {
    System.out.println(response.getFormattedString());
} else if (response instanceof UnsupportedResponse) {
    System.err.println("The command is not supported by your vehicle");
} else {
    System.err.println(response.getFormattedString());
}

// The next time you send the command CatalystTemperature.Bank1Sensor1,
// not additionnal call to SupportedPid.Range01To20 and SupportedPid.Range21To40 will be made.
```

### A ObdSim commander

```java
io.github.macfja.obd2.commander.CommanderInterface commander = io.github.macfja.obd2.commander.ObdSimCommander();
commander.setCommunicationInterface(
        new FileOutputStream("/dev/pts/7"), // if ObdSim is connected on /dev/pts/7
        new FileInputStream("/dev/pts/7") // if ObdSim is connected on /dev/pts/7
);
Response response = commander.sendCommand(new EngineRPM());
if (response instanceof CalculatedResponse) {
    System.out.println(response.getFormattedString());
} else {
    System.err.println(response.getFormattedString());
}
```