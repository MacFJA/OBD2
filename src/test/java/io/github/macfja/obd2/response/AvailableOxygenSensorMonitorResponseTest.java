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
public class AvailableOxygenSensorMonitorResponseTest {
    @Test
    public void testIsRichToLeanSupported() {
        String input = "1011 0010  1101 0110  0101 0100  1001 1101".replace(" ", "");
        input = Long.toHexString(Long.valueOf(input, 2));
        AvailableOxygenSensorMonitorResponse response = new AvailableOxygenSensorMonitorResponse(input.getBytes());

        assertEquals(true, response.isRichToLeanSupported(1, 1));
        assertEquals(false, response.isRichToLeanSupported(1, 2));
        assertEquals(true, response.isRichToLeanSupported(1, 3));
        assertEquals(true, response.isRichToLeanSupported(1, 4));

        assertEquals(false, response.isRichToLeanSupported(2, 1));
        assertEquals(false, response.isRichToLeanSupported(2, 2));
        assertEquals(true, response.isRichToLeanSupported(2, 3));
        assertEquals(false, response.isRichToLeanSupported(2, 4));


        assertEquals(true, response.isRichToLeanSupported(3, 1));
        assertEquals(true, response.isRichToLeanSupported(3, 2));
        assertEquals(false, response.isRichToLeanSupported(3, 3));
        assertEquals(true, response.isRichToLeanSupported(3, 4));

        assertEquals(false, response.isRichToLeanSupported(4, 1));
        assertEquals(true, response.isRichToLeanSupported(4, 2));
        assertEquals(true, response.isRichToLeanSupported(4, 3));
        assertEquals(false, response.isRichToLeanSupported(4, 4));
    }

    @Test
    public void testIsLeanToRichSupported() {
        String input = "1011 0010 1101 0110   0101 0100 1001 1101".replace(" ", "");
        input = Long.toHexString(Long.valueOf(input, 2));
        AvailableOxygenSensorMonitorResponse response = new AvailableOxygenSensorMonitorResponse(input.getBytes());

        assertEquals(false, response.isLeanToRichSupported(1, 1));
        assertEquals(true, response.isLeanToRichSupported(1, 2));
        assertEquals(false, response.isLeanToRichSupported(1, 3));
        assertEquals(true, response.isLeanToRichSupported(1, 4));

        assertEquals(false, response.isLeanToRichSupported(2, 1));
        assertEquals(true, response.isLeanToRichSupported(2, 2));
        assertEquals(false, response.isLeanToRichSupported(2, 3));
        assertEquals(false, response.isLeanToRichSupported(2, 4));

        assertEquals(true, response.isLeanToRichSupported(3, 1));
        assertEquals(false, response.isLeanToRichSupported(3, 2));
        assertEquals(false, response.isLeanToRichSupported(3, 3));
        assertEquals(true, response.isLeanToRichSupported(3, 4));

        assertEquals(true, response.isLeanToRichSupported(4, 1));
        assertEquals(true, response.isLeanToRichSupported(4, 2));
        assertEquals(false, response.isLeanToRichSupported(4, 3));
        assertEquals(true, response.isLeanToRichSupported(4, 4));
    }
}
