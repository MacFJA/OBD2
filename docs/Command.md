# Command

## Structure of a Command

A command is a fairly simple object, it contains 2 things:

 - What to send
 - How to transform the raw result of the request into a Response

### What to send

To communicate with the OBD2 (and therefor your vehicle) you need to send data that can be easily understand and don't cost to much to transfers and interpret.
With that in mind the OBD2 standard define a list of code (hexadecimal number) to send to the OBD2 that represent an action (in most of case, the state of a part of your vehicle)

The command wrap this code into a class that is easier to manipulate and read for humans.

The EML327 (the most common chip use to communicate with an OBD2) add other codes (all prefixed by `AT`, and so, not numbers) to communicate with the chip.
This allow to change some behaviors or to get other data that doesn't involve the OBD (and you vehicle).

### Result transformation

The result of a OBD command is a number, but behind it there are a lots of different meaning:

 - It can be a numeric data, with ou without an unit
 - It can be a string data
 - It can be a list of flag
 - _and so on..._

As each request can have a different type of data in the result, the command know how to transform the raw result into a usable object (a Response).

## Behavior of a command

You can change how a command will be handled by the commander

There are 2 annotations that can change how the commander behave with a command:

 - `io.github.macfja.obd2.PersistentCommand`
 - `io.github.macfja.obd2.elm327.DontFilterResponse`

### PersistentCommand

This annotation inform the Commander that the result won't change in the future.

When the commander receive the result of the command, if the command is successful, it store the result in its internal memory.
The next time the command is executed, the result is directly return, avoid any unnecessary communication with the OBD.

### DontFilterResponse

This annotation inform the ELM Commander (`io.github.macfja.obd2.elm327.Commander`) to not remove space from the result whatever the state of `ATSP(0/1)` is.
This is useful for `AT` command that return a string