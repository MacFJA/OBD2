package io.github.macfja.obd2.elm327;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Commander;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.elm327.command.Identify;
import io.github.macfja.obd2.elm327.response.TextResponse;
import io.github.macfja.obd2.elm327.response.TooOldResponse;
import io.github.macfja.obd2.exception.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>A wrapper around a Commander to handle if a {@link ELMCommand} is supported or not.</p>
 * <p>If a {@link ELMCommand} is not supported, then a {@link TooOldResponse} is return as a {@link Response}.</p>
 *
 * @author MacFJA
 * @see Command
 * @see Response
 */
public class SupportedCommander extends io.github.macfja.obd2.Commander {
    private final io.github.macfja.obd2.Commander parent;
    private float version = 0.0f;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SupportedCommander(Commander parent) {
        this.parent = parent;
    }

    @Override
    public void setCommunicationInterface(OutputStream toOBDStream, InputStream fromOBDStream) {
        parent.setCommunicationInterface(toOBDStream, fromOBDStream);
    }

    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException, ExceptionResponse {
        if (!(command instanceof ELMCommand)) {
            return parent.sendCommand(command);
        }

        if (command instanceof Identify) {
            Response response = parent.sendCommand(command);
            if (response instanceof TextResponse) {
                String versionText = response.getFormattedString();
                Pattern versionPattern = Pattern.compile("v([0-9.]+)$");
                Matcher matches = versionPattern.matcher(versionText);
                if (matches.matches()) {
                    version = Float.parseFloat(matches.group(1));
                }
            }

            return response;
        }

        ELMCommand castedCommand = (ELMCommand) command;
        if (Float.parseFloat(castedCommand.minElmVersion()) > getElmVersion()) {
            logger.warn("The command {} is not support by the ELM327 component", command.getRequest());
            return new TooOldResponse(castedCommand, getElmVersion());
        }

        return parent.sendCommand(command);
    }

    protected float getElmVersion() {
        if (version == 0.0f) {
            try {
                sendCommand(new Identify());
            } catch (IOException | ExceptionResponse | ScriptException e) {
                return 0.0f;
            }
        }

        return version;
    }
}