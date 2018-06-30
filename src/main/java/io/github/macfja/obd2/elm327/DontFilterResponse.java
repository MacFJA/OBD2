package io.github.macfja.obd2.elm327;

import java.lang.annotation.*;

/**
 * <p>Indicate that the result of the command doesn't need to be filtered by the commander.</p>
 * <p>This annotation is only understand by the ELM327 {@link Commander}</p>
 *
 * @author MacFJA
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface DontFilterResponse {
}
