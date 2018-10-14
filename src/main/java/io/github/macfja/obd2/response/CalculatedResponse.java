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

import io.github.macfja.obd2.Response;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Abstract class for response of a {@link io.github.macfja.obd2.Command} that return calculation based data.</p>
 *
 * @author MacFJA
 */
public abstract class CalculatedResponse implements Response {
    private byte[] raw;
    private Number calculated;

    public CalculatedResponse(byte[] raw, Number calculated) {
        this.raw = raw;
        this.calculated = calculated;
    }

    public CalculatedResponse(byte[] raw, String equation) throws ScriptException {
        this(raw, calculateFromEquation(raw, equation));
    }

    public static int getGroupCount(byte[] rawResult) {
        String hexValue = new String(rawResult);
        return hexValue.length() / 2;
    }

    public static int getIntValue(byte[] rawResult, int ofGroup) {
        String hexValue = new String(rawResult);
        return Integer.parseInt(hexValue.substring(ofGroup * 2, (ofGroup + 1) * 2), 16);
    }

    public static int getIntValue(byte[] rawResult, char ofGroup) {
        return getIntValue(rawResult, ofGroup - 'A');
    }

    public static Number calculateFromEquation(byte[] raw, String equation) throws ScriptException {
        ScriptEngine mathSolver = new ScriptEngineManager().getEngineByName("JavaScript");
        Map<String, Object> vars = new HashMap<>();
        //vars.put("SignedA", getSignedA2(raw));
        for (int index = 0; index < CalculatedResponse.getGroupCount(raw); index++) {
            vars.put(Character.toString((char) ('A' + index)), CalculatedResponse.getIntValue(raw, index));
            vars.put("Signed" + Character.toString((char) ('A' + index)), CalculatedResponse.getSigned(raw, index));
        }
        return (Number) mathSolver.eval(equation, new SimpleBindings(vars));
    }

    private static Number getSigned(byte[] raw, int index) {
        int value = getIntValue(raw, index);
        if ((value & (1 << (Byte.SIZE - 1))) != 0) {
            value = value - (1 << Byte.SIZE);
        }
        return value;
    }

    protected int getByte(char group) {
        return getByte(group - 'A');
    }

    protected int getByte(int group) {
        return getIntValue(raw, group);
    }

    @Override
    public byte[] getRawResult() {
        return raw;
    }

    public Number getCalculated() {
        return calculated;
    }

    @Override
    public String getFormattedString() {
        return getCalculated() + getUnit().getSymbol();
    }
}
