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
package io.github.macfja.obd2;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author MacFJA
 */
public class TroubleCodeTest {
    @Test
    public void testCreateFromString() {
        TroubleCode tc1 = TroubleCode.createFromString("P0420");
        assertEquals(TroubleCode.Type.Powertrain, tc1.getType());
        assertEquals(TroubleCode.Domain.Standard, tc1.getDomain());
        assertEquals("P0420", tc1.toString());
        try {
            assertEquals("Catalyst System Efficiency Below Threshold (Bank 1)", tc1.getDescription());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testCreateFromHex() {
        TroubleCode tc1 = TroubleCode.createFromHex("0420");
        assertEquals(TroubleCode.Type.Powertrain, tc1.getType());
        assertEquals(TroubleCode.Domain.Standard, tc1.getDomain());
        assertEquals("P0420", tc1.toString());
        try {
            assertEquals("Catalyst System Efficiency Below Threshold (Bank 1)", tc1.getDescription());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testCreateFromBin() {
        TroubleCode tc1 = TroubleCode.createFromBin("0000010000100000");
        assertEquals(TroubleCode.Type.Powertrain, tc1.getType());
        assertEquals(TroubleCode.Domain.Standard, tc1.getDomain());
        assertEquals("P0420", tc1.toString());
        try {
            assertEquals("Catalyst System Efficiency Below Threshold (Bank 1)", tc1.getDescription());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testIsValid() {
        TroubleCode valid = new TroubleCode(TroubleCode.Type.Powertrain, TroubleCode.Domain.Manufacturer, "123");
        TroubleCode invalid1 = new TroubleCode(null, TroubleCode.Domain.Manufacturer, "123");
        TroubleCode invalid2 = new TroubleCode(null, null, "123");
        TroubleCode invalid3 = new TroubleCode(TroubleCode.Type.Powertrain, null, "123");

        assertTrue(valid.isValid());
        assertFalse(invalid1.isValid());
        assertFalse(invalid2.isValid());
        assertFalse(invalid3.isValid());
    }

    @Test
    public void testBrandDrescription() {
        TroubleCode tc = new TroubleCode(TroubleCode.Type.Powertrain, TroubleCode.Domain.Manufacturer, "106");

        try {
            assertEquals("BARO Sensor Circuit Range/Performance Malfunction", tc.getDescription("honda"));
        } catch (IOException e) {
            fail();
        }

        try {
            assertEquals(TroubleCode.UNKNOWN_TROUBLE_CODE, tc.getDescription("bmw"));
        } catch (IOException e) {
            fail();
        }

        try {
            assertEquals(TroubleCode.UNKNOWN_TROUBLE_CODE, tc.getDescription("ERROR"));
        } catch (IOException e) {
            fail();
        }
    }
}
