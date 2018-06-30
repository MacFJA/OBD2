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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MacFJA
 */
public class UnitTest {
    @Test
    public void testConversions() {
        double precision = 0.000001;
        Unit temperature = Unit.DegreeCelsius;
        assertEquals(122, temperature.toImperial(50f), precision);
        assertEquals("°C", temperature.getSymbol());
        assertEquals("°F", temperature.getImperialSymbol());

        Unit pressure1 = Unit.KiloPascal;
        assertEquals(1.7404528560866, pressure1.toImperial(12f), precision);

        Unit distance = Unit.Kilometre;
        assertEquals(32.932673188579, distance.toImperial(53f), precision);

        Unit pressure2 = Unit.Pascal;
        assertEquals(0.01783964, pressure2.toImperial(123f), precision);

        Unit flow = Unit.LitrePerHour;
        assertEquals(0.85855916653507, flow.toImperial(3.25f), precision);

        Unit force = Unit.NewtonMetre;
        assertEquals(7.23548468441002, force.toImperial(9.81f), precision);
    }

    @Test
    public void testToString() {
        Unit temperature = Unit.DegreeCelsius;
        Unit force = Unit.NewtonMetre;
        Unit minute = Unit.Minute;

        assertEquals("°C (1°F = 1.8°C + 32)", temperature.toString());
        assertEquals("Nm (1lb-ft = 0.737562Nm)", force.toString());
        assertEquals("min", minute.toString());
    }
}
