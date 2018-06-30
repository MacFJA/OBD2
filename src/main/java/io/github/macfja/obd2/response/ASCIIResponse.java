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
 * <p>This class is the response of a {@link io.github.macfja.obd2.Command} that return a hex encoded text.</p>
 * <p>All ASCII table chars are keep, except for the controls chars</p>
 *
 * @author MacFJA
 */
public class ASCIIResponse extends RawResponse {
    public ASCIIResponse(byte[] rawResult) {
        super(rawResult);
    }

    @Override
    public String getFormattedString() {
        String hex = new String(getRawResult());
        StringBuilder ascii = new StringBuilder();

        for (int index = 0; index < hex.length() / 2; index++) {
            int hexValue = Integer.parseInt(hex.substring(index * 2, index * 2 + 2), 16);

            // Remove all controls chars
            if (hexValue < 32 || hexValue > 126) {
                continue;
            }

            ascii.append(Character.toChars(hexValue));
        }

        return ascii.toString();
    }
}
