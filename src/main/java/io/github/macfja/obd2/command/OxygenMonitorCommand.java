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
import io.github.macfja.obd2.command.oxygen_monitor.LeanToRichMonitorCommand;
import io.github.macfja.obd2.command.oxygen_monitor.RichToLeanMonitorCommand;
import io.github.macfja.obd2.command.oxygen_monitor.SupportedOxygenSensorMonitorCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Mode 05 commands
 *
 * @author MacFJA
 */
public abstract class OxygenMonitorCommand {
    /**
     * List of all command in the Mode 05
     *
     * @return List of commands
     */
    public static Map<String, Command> getMode05Commands() {
        Map<String, Command> mode05 = new HashMap<>();

        mode05.put("0100", new SupportedOxygenSensorMonitorCommand());
        mode05.put("0101", RichToLeanMonitorCommand.Bank1Sensor1);
        mode05.put("0102", RichToLeanMonitorCommand.Bank1Sensor2);
        mode05.put("0103", RichToLeanMonitorCommand.Bank1Sensor3);
        mode05.put("0104", RichToLeanMonitorCommand.Bank1Sensor4);
        mode05.put("0105", RichToLeanMonitorCommand.Bank2Sensor1);
        mode05.put("0106", RichToLeanMonitorCommand.Bank2Sensor2);
        mode05.put("0107", RichToLeanMonitorCommand.Bank2Sensor3);
        mode05.put("0108", RichToLeanMonitorCommand.Bank2Sensor4);
        mode05.put("0109", RichToLeanMonitorCommand.Bank3Sensor1);
        mode05.put("010A", RichToLeanMonitorCommand.Bank3Sensor2);
        mode05.put("010B", RichToLeanMonitorCommand.Bank3Sensor3);
        mode05.put("010C", RichToLeanMonitorCommand.Bank3Sensor4);
        mode05.put("010D", RichToLeanMonitorCommand.Bank4Sensor1);
        mode05.put("010E", RichToLeanMonitorCommand.Bank4Sensor2);
        mode05.put("010F", RichToLeanMonitorCommand.Bank4Sensor3);
        mode05.put("0110", RichToLeanMonitorCommand.Bank4Sensor4);
        mode05.put("0201", LeanToRichMonitorCommand.Bank1Sensor1);
        mode05.put("0202", LeanToRichMonitorCommand.Bank1Sensor2);
        mode05.put("0203", LeanToRichMonitorCommand.Bank1Sensor3);
        mode05.put("0204", LeanToRichMonitorCommand.Bank1Sensor4);
        mode05.put("0205", LeanToRichMonitorCommand.Bank2Sensor1);
        mode05.put("0206", LeanToRichMonitorCommand.Bank2Sensor2);
        mode05.put("0207", LeanToRichMonitorCommand.Bank2Sensor3);
        mode05.put("0208", LeanToRichMonitorCommand.Bank2Sensor4);
        mode05.put("0209", LeanToRichMonitorCommand.Bank3Sensor1);
        mode05.put("020A", LeanToRichMonitorCommand.Bank3Sensor2);
        mode05.put("020B", LeanToRichMonitorCommand.Bank3Sensor3);
        mode05.put("020C", LeanToRichMonitorCommand.Bank3Sensor4);
        mode05.put("020D", LeanToRichMonitorCommand.Bank4Sensor1);
        mode05.put("020E", LeanToRichMonitorCommand.Bank4Sensor2);
        mode05.put("020F", LeanToRichMonitorCommand.Bank4Sensor3);
        mode05.put("0210", LeanToRichMonitorCommand.Bank4Sensor4);

        return mode05;
    }
}
