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

package io.github.macfja.obd2.response;

/**
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.StatusSinceStart} command
 * or a {@link io.github.macfja.obd2.command.livedata.StatusSinceLastClean} command.</p>
 *
 * @author MacFJA
 */
public class MILResponse extends BinaryResponse {
    public MILResponse(byte[] rawResult) {
        super(rawResult);
    }

    public boolean isMILOn() {
        return isOn("A7");
    }

    public int getEmissionCodeCount() {
        return getNumber("A0", "A6");
    }

    public IgnitionType getIgnitionType() {
        return isOn("B3") ? IgnitionType.Compression : IgnitionType.Spark;
    }

    public OnBoardTest getComponentsTest() {
        return getTestFor("B2", "B6");
    }

    public OnBoardTest getFuelSystemTest() {
        return getTestFor("B1", "B5");
    }

    public OnBoardTest getMisfireTest() {
        return getTestFor("B0", "B4");
    }

    public OnBoardTest getExhaustGasRecirculationSystemTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C7", "D7");
    }

    public OnBoardTest getOxygenSensorHeaterTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C6", "D6");
    }

    public OnBoardTest getOxygenSensorTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C5", "D5");
    }

    public OnBoardTest getAirConditioningRefrigerantTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C4", "D4");
    }

    public OnBoardTest getSecondaryAirSystemTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C3", "D3");
    }

    public OnBoardTest getEvaporativeSystemTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C2", "D2");
    }

    public OnBoardTest getHeatedCatalystTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C1", "D1");
    }

    public OnBoardTest getCatalystTest() {
        if (getIgnitionType().equals(IgnitionType.Compression)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C0", "D0");
    }

    public OnBoardTest getExhaustGasRecirculationVVTSystemTest() {
        if (getIgnitionType().equals(IgnitionType.Spark)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C7", "D7");
    }

    public OnBoardTest getPMFilterMonitoringTest() {
        if (getIgnitionType().equals(IgnitionType.Spark)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C6", "D6");
    }

    public OnBoardTest getExhaustGasSensorTest() {
        if (getIgnitionType().equals(IgnitionType.Spark)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C5", "D5");
    }

    public OnBoardTest getBoostPressureTest() {
        if (getIgnitionType().equals(IgnitionType.Spark)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C3", "D3");
    }

    public OnBoardTest getNOxSCRMonitorTest() {
        if (getIgnitionType().equals(IgnitionType.Spark)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C1", "D1");
    }

    public OnBoardTest getNMHCCatalystTest() {
        if (getIgnitionType().equals(IgnitionType.Spark)) {
            return OnBoardTest.NotAvailable;
        }
        return getTestFor("C0", "D0");
    }

    private OnBoardTest getTestFor(String availabilityCode, String incompleteCode) {
        if (!isOn(availabilityCode)) {
            return OnBoardTest.NotAvailable;
        }
        if (isOn(incompleteCode)) {
            return OnBoardTest.AvailableIncomplete;
        }
        return OnBoardTest.AvailableComplete;
    }

    /**
     * This enum contains the list availability status of OBD tests
     *
     * @author MacFJA
     */
    public enum OnBoardTest {
        AvailableIncomplete,
        AvailableComplete,
        NotAvailable
    }

    /**
     * This enum contains two fuel ignition type: by Spark, or by Compression
     *
     * @author MacFJA
     */
    public enum IgnitionType {
        Spark,
        Compression
    }
}
