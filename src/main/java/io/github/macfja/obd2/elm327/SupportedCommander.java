package io.github.macfja.obd2.elm327;

import io.github.macfja.obd2.Command;
import io.github.macfja.obd2.Response;
import io.github.macfja.obd2.commander.AbstractSupportedCommanderDecorator;
import io.github.macfja.obd2.commander.CommanderInterface;
import io.github.macfja.obd2.elm327.command.Identify;
import io.github.macfja.obd2.elm327.response.TextResponse;
import io.github.macfja.obd2.elm327.response.TooOldResponse;
import io.github.macfja.obd2.exception.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>A wrapper around a {@link CommanderInterface} to handle if an {@link ELMCommand} is supported or not.</p>
 * <p>If a {@link ELMCommand} is not supported, then a {@link TooOldResponse} is return as a {@link Response}.</p>
 *
 * @author MacFJA
 * @see Command
 * @see Response
 */
public class SupportedCommander extends AbstractSupportedCommanderDecorator {
    private float version = 0.0f;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public SupportedCommander(CommanderInterface parent) {
        super(parent);
    }

    @Override
    public Response sendCommand(Command command) throws IOException, ScriptException, ExceptionResponse {
        if (!(command instanceof Identify) && !isCommandSupported(command)) {
            logger.warn("The command {} is not support by the ELM327 component", command.getRequest());
            return new TooOldResponse((ELMCommand) command, getElmVersion());
        }
        Response response = super.sendCommand(command);

        if (command instanceof Identify) {
            if (response instanceof TextResponse) {
                String versionText = response.getFormattedString();
                Pattern versionPattern = Pattern.compile("v([0-9.]+)$");
                Matcher matches = versionPattern.matcher(versionText);
                if (matches.matches()) {
                    version = Float.parseFloat(matches.group(1));
                }
            }
        }

        return response;
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

    @Override
    protected boolean doIsCommandSupported(Command command) {
        // Only check ELMCommand, suppose that others commands have already be check or will be check after
        if (!(command instanceof ELMCommand)) {
            return true;
        }

        ELMCommand castedCommand = (ELMCommand) command;
        return Float.parseFloat(castedCommand.minElmVersion()) <= getElmVersion();
    }
}