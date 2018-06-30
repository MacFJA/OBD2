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
 * <p>This class is the response of a {@link io.github.macfja.obd2.command.livedata.OBDStandards} command.</p>
 *
 * @author MacFJA
 */
public class OBDStandardTypeResponse extends RawResponse {
    public OBDStandardTypeResponse(byte[] rawResult) {
        super(rawResult);
    }

    public OBDStandard getStandard() {
        Integer code = Integer.parseInt(new String(getRawResult()), 16);
        for (OBDStandard standard : OBDStandard.values()) {
            if (code.equals(standard.code)) {
                return standard;
            }
        }
        return OBDStandard.NotOBD;
    }

    @Override
    public String getFormattedString() {
        OBDStandard standard = getStandard();
        return String.format("%d: %s", standard.code, standard.description);
    }

    /**
     * This enum contains the list of all existing OBD standards
     *
     * @author MacFJA
     */
    public enum OBDStandard {
        OBD2(1, "OBD-II as defined by the CARB"),
        OBD(4, "OBD-I"),
        NotOBD(5, "Not OBD compliant"),
        EPA_OBD(2, "OBD as defined by the EPA"),
        EuropeOBD(6, "EOBD (Europe)"),
        JapanOBD(10, "JOBD (Japan)"),
        EngineManufactureDiagnostics(17, "Engine Manufacturer Diagnostics (EMD)"),
        EngineManufactureDiagnosticsEnhanced(18, "Engine Manufacturer Diagnostics Enhanced (EMD+)"),
        HeavyDutyOBDChild(19, "Heavy Duty On-Board Diagnostics (Child/Partial) (HD OBD-C)"),
        HeavyDutyOBD(20, "Heavy Duty On-Board Diagnostics (HD OBD)"),
        WorldWidOBD(21, "World Wide Harmonized OBD (WWH OBD)"),
        HeavyDutyEuropeOBDStage1(23, "Heavy Duty Euro OBD Stage I without NOx control (HD EOBD-I)"),
        HeavyDutyEuropeOBDStage1Nox(24, "Heavy Duty Euro OBD Stage I with NOx control (HD EOBD-I N)"),
        HeavyDutyEuropeOBDStage2(25, "Heavy Duty Euro OBD Stage II without NOx control (HD EOBD-II)"),
        HeavyDutyEuropeOBDStage2Nox(26, "Heavy Duty Euro OBD Stage II with NOx control (HD EOBD-II N)"),
        HeavyDutyEuropeOBDStage4(33, "Heavy Duty Euro OBD Stage VI (HD EOBD-IV)"),
        BrazilOBDPhase1(28, "Brazil OBD Phase 1 (OBDBr-1)"),
        BrazilOBDPhase2(29, "Brazil OBD Phase 2 (OBDBr-2)"),
        KoreanOBD(30, "Korean OBD (KOBD)"),
        IndiaOBD(31, "India OBD I (IOBD I)"),
        IndiaOBD2(32, "India OBD II (IOBD II)"),

        OBD_OB2(3, "OBD and OBD-II", new OBDStandard[]{OBD, OBD2}),
        EOBD_OBD2(7, "EOBD and OBD-II", new OBDStandard[]{EuropeOBD, OBD2}),
        EOBD_OBD(8, "EOBD and OBD", new OBDStandard[]{EuropeOBD, OBD}),
        EOBD_OBD_OBD2(9, "EOBD, OBD and OBD II", new OBDStandard[]{EuropeOBD, OBD, OBD2}),
        JOBD_OBD2(11, "JOBD and OBD II", new OBDStandard[]{JapanOBD, OBD2}),
        JOBD_EOBD(12, "JOBD and EOBD", new OBDStandard[]{JapanOBD, EuropeOBD}),
        JOBD_EOBD_OBD2(13, "JOBD, EOBD, and OBD II", new OBDStandard[]{JapanOBD, EuropeOBD, OBD2});
        private final String description;
        private final int code;
        private final OBDStandard[] composition;

        OBDStandard(int code, String description, OBDStandard[] composition) {
            this.description = description;
            this.code = code;
            this.composition = composition;
        }

        OBDStandard(int code, String description) {
            this(code, description, new OBDStandard[0]);
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompliantTo(OBDStandard standard) {
            if (this.equals(standard)) {
                return true;
            }
            for (OBDStandard item : composition) {
                if (item.equals(standard)) {
                    return true;
                }
            }

            return false;
        }
    }
}
