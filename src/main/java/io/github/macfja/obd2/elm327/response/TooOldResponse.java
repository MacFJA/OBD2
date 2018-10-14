package io.github.macfja.obd2.elm327.response;

import io.github.macfja.obd2.elm327.ELMCommand;
import io.github.macfja.obd2.exception.UnsupportedResponse;

/**
 * The Exception/Response when the {@link ELMCommand} is not supported.
 *
 * @author MacFJA
 */
public class TooOldResponse extends UnsupportedResponse {
    private ELMCommand command;
    private float elmVersion;

    public TooOldResponse(ELMCommand command, float currentVersion) {
        super(command);
        this.command = command;
        this.elmVersion = currentVersion;
    }

    @Override
    public String getFormattedString() {
        return String.format(
                "The command '%s' is not supported by the ELM327.\n" +
                        "The command require a version %s, your ELM327 is a version %.1f.",
                command.getRequest(),
                command.minElmVersion(),
                elmVersion
        );
    }
}
