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
import io.github.macfja.obd2.command.vehicle_info.ECUName;
import io.github.macfja.obd2.command.vehicle_info.SupportedPid;
import io.github.macfja.obd2.command.vehicle_info.VINMessageCount;
import io.github.macfja.obd2.command.vehicle_info.VehicleIdentificationNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * Mode 09 commands
 *
 * @author MacFJA
 */
public abstract class VehicleInformationCommand {
    /**
     * List of all command in the Mode 09
     *
     * @return List of commands
     */
    public static Map<String, Command> getMode09Commands() {
        Map<String, Command> mode09 = new HashMap<>();

        mode09.put("00", new SupportedPid());
        mode09.put("01", new VINMessageCount());
        mode09.put("02", new VehicleIdentificationNumber());
        mode09.put("0A", new ECUName());

        return mode09;
    }
}
