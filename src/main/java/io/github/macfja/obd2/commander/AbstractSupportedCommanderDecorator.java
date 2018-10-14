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
import io.github.macfja.obd2.exception.UnsupportedResponse;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Decorator for {@link CommanderInterface} to add {@link SupportedInterface} feature.
 *
 * @author MacFJA
 */
public abstract class AbstractSupportedCommanderDecorator implements CommanderInterface, SupportedInterface {
    private CommanderInterface parent;

    public AbstractSupportedCommanderDecorator(CommanderInterface parent) {
        this.parent = parent;
    }

    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException, ExceptionResponse {
        if (!isCommandSupported(command)) {
            return new UnsupportedResponse(command);
        }
        return parent.sendCommand(command);
    }

    @Override
    public boolean isCommandSupported(Command command) {
        boolean parentResult = true;
        if (parent instanceof SupportedInterface) {
            parentResult = ((SupportedInterface) parent).isCommandSupported(command);
        }
        return parentResult && doIsCommandSupported(command);
    }

    /**
     * Check if the command is supported.
     * @param command The command to check
     * @return {@code true} if the command is supported or not known
     */
    protected abstract boolean doIsCommandSupported(Command command);

    @Override
    public void clearPersistentResponseStorage() {
        parent.clearPersistentResponseStorage();
    }

    @Override
    public void setCommunicationInterface(OutputStream toOBDStream, InputStream fromOBDStream) {
        parent.setCommunicationInterface(toOBDStream, fromOBDStream);
    }

    @Override
    public void unpersistCommand(String request) {
        parent.unpersistCommand(request);
    }

    @Override
    public void persistCommand(String request, byte[] rawResponse) {
        parent.persistCommand(request, rawResponse);
    }
}
