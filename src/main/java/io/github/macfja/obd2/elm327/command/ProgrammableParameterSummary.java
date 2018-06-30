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

package io.github.macfja.obd2.elm327.command;

import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.elm327.DontFilterResponse;
import io.github.macfja.obd2.elm327.response.ProgrammableParameterResponse;

/**
 * <p>This class is the ELM327 command for "AT PPS".</p>
 * <p>Description: Programmable Parameter Summary</p>
 * <p>ELM327 version: {@code 1.1}</p>
 * <p>Response: All parameters with their value and status</p>
 *
 * @author MacFJA
 * @see <a href="https://www.elmelectronics.com/wp-content/uploads/2017/01/ELM327DS.pdf">ELM327 PDF</a>
 */
@DontFilterResponse
public class ProgrammableParameterSummary extends ATCommand {
    @Override
    protected String getCode() {
        return "PPS";
    }

    @Override
    public Response getResponse(byte[] rawResult) {
        return new ProgrammableParameterResponse(rawResult);
    }

    @Override
    public String minElmVersion() {
        return "1.1";
    }
}
