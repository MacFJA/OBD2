package io.github.macfja.obd2.exception;

import io.github.macfja.obd2.Command;

public class UnsupportedResponse extends ExceptionResponse {
    private Command command;

    public UnsupportedResponse(Command command) {
        super(new byte[0]);
        this.command = command;
    }

    @Override
    public String getFormattedString() {
        return String.format("The command '%s' is not supported by the ECU", command.getRequest());
    }
}
