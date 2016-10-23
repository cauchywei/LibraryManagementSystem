package xp.librarian.utils;

import java.time.temporal.ChronoUnit;

import java.time.*;
import java.time.temporal.*;

/**
 * @author xp
 */
public class TimeUtils {

    public static Instant afterNow(long amount, ChronoUnit unit) {
        return Instant.now().plus(amount, unit);
    }

}
