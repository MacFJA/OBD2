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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MacFJA
 */
public class AvailablePidResponseTest {
    private static final String responseData = "BE1FA813";

    @Test
    public void testIsPidSupportedHex() {
        AvailablePidResponse response = new AvailablePidResponse(responseData.getBytes(), 1);

        assertEquals(true, response.isPidSupported("01"), "PID 01 should be available");
        assertEquals(true, response.isPidSupported("03"), "PID 03 should be available");
        assertEquals(true, response.isPidSupported("04"), "PID 04 should be available");
        assertEquals(true, response.isPidSupported("05"), "PID 05 should be available");
        assertEquals(true, response.isPidSupported("06"), "PID 06 should be available");
        assertEquals(true, response.isPidSupported("07"), "PID 07 should be available");
        assertEquals(true, response.isPidSupported("0C"), "PID 0C should be available");
        assertEquals(true, response.isPidSupported("0D"), "PID 0D should be available");
        assertEquals(true, response.isPidSupported("0E"), "PID 0E should be available");
        assertEquals(true, response.isPidSupported("0F"), "PID 0F should be available");
        assertEquals(true, response.isPidSupported("10"), "PID 10 should be available");
        assertEquals(true, response.isPidSupported("11"), "PID 11 should be available");
        assertEquals(true, response.isPidSupported("13"), "PID 13 should be available");
        assertEquals(true, response.isPidSupported("15"), "PID 15 should be available");
        assertEquals(true, response.isPidSupported("1C"), "PID 1C should be available");
        assertEquals(true, response.isPidSupported("1F"), "PID 1F should be available");
        assertEquals(true, response.isPidSupported("20"), "PID 20 should be available");
    }

    @Test
    public void testIsPidSupportedInt() {
        AvailablePidResponse response = new AvailablePidResponse(responseData.getBytes(), 1);

        assertEquals(false, response.isPidSupported(2), "PID 2 should not be available");
        assertEquals(false, response.isPidSupported(8), "PID 8 should not be available");
        assertEquals(false, response.isPidSupported(9), "PID 9 should not be available");
        assertEquals(false, response.isPidSupported(10), "PID 10 should not be available");
        assertEquals(false, response.isPidSupported(11), "PID 11 should not be available");
        assertEquals(false, response.isPidSupported(18), "PID 18 should not be available");
        assertEquals(false, response.isPidSupported(20), "PID 20 should not be available");
        assertEquals(false, response.isPidSupported(22), "PID 22 should not be available");
        assertEquals(false, response.isPidSupported(23), "PID 23 should not be available");
        assertEquals(false, response.isPidSupported(24), "PID 24 should not be available");
        assertEquals(false, response.isPidSupported(25), "PID 25 should not be available");
        assertEquals(false, response.isPidSupported(26), "PID 26 should not be available");
        assertEquals(false, response.isPidSupported(27), "PID 27 should not be available");
        assertEquals(false, response.isPidSupported(29), "PID 29 should not be available");
        assertEquals(false, response.isPidSupported(30), "PID 30 should not be available");
    }
}
