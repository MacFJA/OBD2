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
 * <p>Abstract class for response of a {@link io.github.macfja.obd2.Command} that return binary (ON/OFF bytes).</p>
 *
 * @author MacFJA
 */
public abstract class BinaryResponse extends RawResponse {
    public BinaryResponse(byte[] rawResult) {
        super(rawResult);
    }

    public boolean isOn(String code) {
        Integer group = getGroup(code.charAt(0));
        int groupPos = Integer.parseInt(code.substring(1, 2));

        Integer mask = new Integer((int) Math.pow(2, groupPos));
        return (group & mask) != 0;
    }

    public boolean isOn(int position) {
        String hex = new String(getRawResult());
        String binary = String.format("%" + (hex.length() * 4) + "s", Long.toBinaryString(Long.parseLong(hex, 16))).replace(" ", "0");

        return binary.charAt(position) == '1';
    }

    private Integer getGroup(char code) {
        String data = new String(getRawResult());

        // Each group is 2 hex char
        return Integer.parseInt(data.substring((code - 'A'), (code - 'A') + 2), 16);
    }

    public int getNumber(String startCode, String endCode) {
        return getNumber(stringCodeToInt(startCode), stringCodeToInt(endCode));
    }

    public int getNumber(int start, int end) {
        Long fullAvailability = Long.parseLong(getFormattedString(), 16);
        String binary = Long.toBinaryString(fullAvailability);

        return Integer.parseInt(binary.substring(start, end), 2);
    }

    private int stringCodeToInt(String code) {
        char groupChar = code.charAt(0);
        int inGroupPosition = Integer.parseInt(String.valueOf(code.charAt(1)));
        return (groupChar - 'A') * 8 + inGroupPosition;
    }
}
