package xp.librarian.utils;

import java.time.*;
import java.time.temporal.*;

/**
 * @author xp
 */
public class TimeUtils {

    public static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public static Instant afterNow(long amount, ChronoUnit unit) {
        return now().plus(amount, unit);
    }

}
