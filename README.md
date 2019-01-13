# OBD for JAVA

## Supported commands

Although any commands can be send with this lib, the list of pure object command does not cover all existing commands.

Some commands are missing, because I didn't find documentation about it or because my car is pretty old and doesn't support many OBD-II commands.

Here are the list of all known command and there status (implemented or not):

 - [For ELM327 interface](src/main/java/io/github/macfja/obd2/elm327/command/Commands.md)
 - [For OBD](src/main/java/io/github/macfja/obd2/command/Commands.md)

## Usage

### Basic example

```java
import io.github.macfja.obd2.elm327.Commander;
import io.github.macfja.obd2.elm327.command.*;
import io.github.macfja.obd2.command.livedata.*;

public class Example {
    Commander commander = new Commander();

    public static void main(String[] args) {
        new Example();
    }

    public Example() {
        commander.setCommunicationInterface(OBD2.getOutputStream(), OBD2.getInputStream());
        System.out.println(commander.sendCommand(new DeviceDescription()));
        // Should print something like "OBDII to RS232 Interpreter"
        System.out.println(commander.sendCommand(new EngineRPM()));
        // Should print something like "875rpm"
    }
}
```

### Sending custom command

```java
import io.github.macfja.obd2.response.RawResponse;
import io.github.macfja.obd2.Commander;
import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.Unit;
import io.github.macfja.obd2.SimpleCommands;

Commander commander;

// Setup the `commander`
// [...]

Command customCommand = new Command() {
    @Override
    public String getRequest() {
        return "MyCode";
    }

    @Override
    public Response getResponse(byte[] rawResult) throws ScriptException {
        return new RawResponse(rawResult) {};
    }
};
commander.sendCommand(customCommand);

// OR

Command customCommand2 = SimpleCommands.create("My Code");
commander.sendCommand(customCommand2);
```

## Installation<a id="installation"></a>

### From the sources

Clone the project:
```
git clone https://github.com/MacFJA/OBD2.git
```
Install the project into your local Maven repository:
```
cd OBD2/
mvn clean
mvn install
```
Remove the source:
```
cd ..
rm -r OBD2/
```
Add the dependency in your Maven project:
```xml
<project>
    <!-- ... -->
    <dependencies>
        <!-- ... -->
        <dependency>
            <groupId>io.github.macfja</groupId>
            <artifactId>obd2</artifactId>
            <version>1.1.0</version>
        </dependency>
        <!-- ... -->
    </dependencies>
    <!-- ... -->
</project>
```

### From a release

Go to the [realase page](https://github.com/MacFJA/OBD2/releases), and download the **jar**.

Next add the **jar** in your project classpath.

### With JitPack

The library is available online for Gradle, Maven, Sbt and Leiningen with the help of [JitPack](https://jitpack.io/).

See installation instruction here: [![](https://jitpack.io/v/MacFJA/OBD2.svg)](https://jitpack.io/#MacFJA/OBD2) (Spoiler: it's easy)
