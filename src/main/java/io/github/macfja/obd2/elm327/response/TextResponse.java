package io.github.macfja.obd2.elm327.response;

import io.github.macfja.obd2.response.RawResponse;

/**
 * <p>This class is the response of a {@link io.github.macfja.obd2.Command} that return a plain text.</p>
 * <p>All ASCII table chars are keep, except for the controls chars</p>
 *
 * @author MacFJA
 */
public class TextResponse extends RawResponse {
    public TextResponse(byte[] rawResult) {
        super(rawResult);
    }

    @Override
    public String getFormattedString() {
        String source = new String(getRawResult());
        StringBuilder result = new StringBuilder();

        for (int index = 0; index < source.length(); index++) {
            char charAt = source.charAt(index);

            // Remove all controls chars
            if (charAt < 32 || charAt > 126) {
                continue;
            }

            result.append(Character.toChars(charAt));
        }

        return result.toString();
    }
}
