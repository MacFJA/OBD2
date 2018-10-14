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

package io.github.macfja.obd2.commander;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.exception.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Send OBD command and return command result.
 *
 * @author MacFJA
 * @see Command
 * @see Response
 */
public interface CommanderInterface {
    /**
     * Send the command to the OBD interface and get its response
     *
     * @param command The command to send
     * @return The command response
     * @throws IOException     If an error occurs during communication with the OBD interfaces
     * @throws ScriptException If the conversion equation is wrong
     */
    public Response sendCommand(Command command) throws IOException, ScriptException, ExceptionResponse;

    /**
     * Delete all stored response of persistent command
     */
    public void clearPersistentResponseStorage();

    /**
     * Set the streams for communicating with the OBD
     * @param toOBDStream The stream to send data
     * @param fromOBDStream The stream to read data
     */
    public void setCommunicationInterface(OutputStream toOBDStream, InputStream fromOBDStream);

    /**
     * <p>Remove a persistent command result.</p>
     *
     * <p>Can be use if an error occurs, or if the data corrupted or invalid</p>
     *
     * @param request The request code of the command ({@link Command#getRequest()})
     */
    public void unpersistCommand(String request);

    /**
     * Store the response of a command for later use.
     *
     * If the command is call later, the provided response will be use, and the OBD will not be call
     *
     * @param request The command request code
     * @param rawResponse The raw output of the command
     */
    public void persistCommand(String request, byte[] rawResponse);
}
