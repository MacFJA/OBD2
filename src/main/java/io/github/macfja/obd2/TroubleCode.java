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
package io.github.macfja.obd2;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

/**
 * Object representation of a Diagnostic Trouble Code (DTC)
 *
 * @author MacFJA
 */
public class TroubleCode {
    public static final String UNKNOWN_TROUBLE_CODE = "Unknown DTC";
    private final Type type;
    private final Domain domain;
    private final String code;

    public TroubleCode(Type type, Domain domain, String code) {
        this.type = type;
        this.domain = domain;
        this.code = code;
    }

    public static TroubleCode createFromString(String line) {
        Type type = Type.getType(Character.valueOf(line.charAt(0)));
        Domain domain = Domain.getDomain(Integer.parseInt(String.valueOf(line.charAt(1))));
        String code = line.substring(2);

        return new TroubleCode(type, domain, code);
    }

    public static TroubleCode createFromHex(String hex) {
        // A DTC is contains in 4 hex chars
        String corrected = hex.substring(0, 4);
        return createFromBin(String.format("%16s", new BigInteger(corrected, 16).toString(2)).replace(' ', '0'));
    }

    public static TroubleCode createFromBin(String bin) {
        Type type = Type.getType(Integer.parseInt(bin.substring(0, 2), 2));
        Domain domain = Domain.getDomain(Integer.parseInt(bin.substring(2, 4), 2));
        String code = Integer.toHexString(Integer.parseInt(bin.substring(4, 16), 2));

        return new TroubleCode(type, domain, code);
    }

    public Type getType() {
        return type;
    }

    public Domain getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return type.letter.toString() + Integer.toString(domain.code) + code;
    }

    public boolean isValid() {
        return type != null && domain != null;
    }

    public String getDescription(Locale locale) throws IOException {
        return getDescription(locale, "generic");
    }

    public String getDescription() throws IOException {
        return getDescription(Locale.ENGLISH);
    }

    public String getDescription(String brand) throws IOException {
        return getDescription(Locale.ENGLISH, brand);
    }

    /**
     * Get the description of the trouble code
     *
     * @param locale The locale to use (if no translation found, falling back to "en")
     * @param brand  The brand to lookup for custom code
     * @return The description of trouble code. If the code is not found the string "Unknown DTC" is return ({@see UNKNOWN_TROUBLE_CODE}).
     * @throws IOException If an IO error occur while reading the description
     */
    public String getDescription(Locale locale, String brand) throws IOException {
        Set<String> files = new HashSet<>();
        files.add("/dtc/generic/" + locale.getLanguage() + ".properties");
        files.add("/dtc/generic/en.properties");

        // Check if brand don't start with "../" or contains "/../"
        if (!brand.startsWith("../") && !brand.contains("/../")) {
            files.add("/dtc/" + brand + "/" + locale.getLanguage() + ".properties");
            files.add("/dtc/" + brand + "/en.properties");
        }

        Properties properties = new Properties();
        for (String filePath : files) {
            URL url = TroubleCode.class.getResource(filePath);
            if (url == null) {
                continue;
            }
            properties.load(getClass().getResourceAsStream(filePath));

            if (!properties.containsKey(toString())) {
                continue;
            }
            return properties.getProperty(toString());
        }

        return UNKNOWN_TROUBLE_CODE;
    }

    /**
     * The type of the Trouble Code (The first letter of the DTC)
     *
     * @author MacFJA
     */
    public enum Type {
        Powertrain('P', 0),
        Body('B', 2),
        Chassis('C', 1),
        UserNetwork('U', 3);

        private final Character letter;
        private final int code;

        Type(Character letter, int code) {
            this.letter = letter;
            this.code = code;
        }

        /**
         * Get the Trouble Code Type from its letter.
         *
         * @param letter The type letter to search
         * @return The type, or {@code null} if the letter doesn't exist as a Type
         */
        public static Type getType(Character letter) {
            for (Type type : values()) {
                if (type.letter.equals(letter)) {
                    return type;
                }
            }
            return null;
        }

        /**
         * Get the Trouble Code Type from its code.
         *
         * @param code The type code to search
         * @return The type
         * @throws IndexOutOfBoundsException If {@code code} is less than 0 or more than 3 (code is a 2 bits value)
         */
        public static Type getType(int code) {
            if (code < 0 || code > 3) {
                throw new IndexOutOfBoundsException(
                        "Type code is a 2 bits value, therefor can only vary between 0 and 3 (both included)"
                );
            }

            for (Type type : values()) {
                if (type.code == code) {
                    return type;
                }
            }

            // Shouldn't goes here
            return null;
        }
    }

    /**
     * The Domain of the Trouble Code. (The second letter of the DTC)
     *
     * @author MacFJA
     */
    public enum Domain {
        Standard(0),
        Manufacturer(1),
        Custom2(2), // Shouldn't be used
        Custom3(3); // Shouldn't be used

        private final int code;

        Domain(int code) {
            this.code = code;
        }

        /**
         * <p>Get the Trouble Code Domain from its code.</p>
         * <p>{@code code} is normally {@code 0} or {@code 1}. Other values are not defined in the standard.</p>
         *
         * @param code The domain code to search
         * @return The domain
         * @throws IndexOutOfBoundsException If {@code code} is less than 0 or more than 3 (code is a 2 bits value)
         */
        public static Domain getDomain(int code) {
            if (code < 0 || code > 3) {
                throw new IndexOutOfBoundsException(
                        "Domain code is a 2 bits value, therefor can only vary between 0 and 3 (both included)"
                );
            }

            for (Domain domain : values()) {
                if (domain.code == code) {
                    return domain;
                }
            }

            // Shouldn't goes here
            return null;
        }
    }
}
