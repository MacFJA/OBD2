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

import java.util.Arrays;

/**
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.FuelType} command.</p>
 *
 * @author MacFJA
 */
public class FuelTypeResponse extends RawResponse {
    public FuelTypeResponse(byte[] rawResult) {
        super(rawResult);
    }

    public FuelType getFuelType() {
        Integer code = Integer.parseInt(new String(getRawResult()), 16);
        for (FuelType type : FuelType.values()) {
            if (code.equals(type.code)) {
                return type;
            }
        }
        return FuelType.UnknownType;
    }

    @Override
    public String getFormattedString() {
        return getFuelType().getLabel();
    }

    /**
     * This enum contains all (OBD) known fuel type
     *
     * @author MacFJA
     */
    public enum FuelType {
        UnknownType(-1, "Unknown Fuel type"),
        NotAvailable(0, "Not Available"),
        Gasoline(1, "Gasoline"),
        Methanol(2, "Methanol"),
        Ethanol(3, "Ethanol"),
        Diesel(4, "Diesel"),
        LGP(5, "LGP (Liquefied petroleum gas)"),
        CNG(6, "CNG (Compressed natural gas)"),
        Propane(7, "Propane"),
        Electric(8, "Electric"),
        BiGasoline(9, "Bifuel running Gasoline"),
        BiMethanol(10, "Bifuel running Methanol"),
        BiEthanol(11, "Bifuel running Ethanol"),
        BiLPG(12, "Bifuel running LPG (Liquefied petroleum gas)"),
        BiCNG(13, "Bifuel running CNG (Compressed natural gas)"),
        BiPropane(14, "Bifuel running Propane"),
        BiElectricity(15, "Bifuel running Electricity"),
        BiElectricCombustionEngine(16, "Bifuel running electric and combustion engine"),
        HybridGasoline(17, "Hybrid gasoline"),
        HybridEthanol(18, "Hybrid Ethanol"),
        HybridDiesel(19, "Hybrid Diesel"),
        HybridElectric(20, "Hybrid Electric"),
        HybridElectricCombustionEngine(21, "Hybrid running electric and combustion engine"),
        HybridRegenerative(22, "Hybrid Regenerative"),
        BiDiesel(23, "Bifuel running diesel");

        private final int code;
        private final String label;

        FuelType(int code, String label) {
            this.code = code;
            this.label = label;
        }

        public boolean isBifuel() {
            return Arrays.asList(
                    BiCNG, BiDiesel, BiElectricCombustionEngine, BiElectricity, BiPropane, BiGasoline, BiMethanol,
                    BiEthanol, BiLPG
            ).contains(this);
        }

        public boolean isHybrid() {
            return Arrays.asList(
                    HybridGasoline, HybridEthanol, HybridDiesel, HybridElectric, HybridElectricCombustionEngine,
                    HybridRegenerative
            ).contains(this);
        }

        public String getLabel() {
            return label;
        }
    }
}
