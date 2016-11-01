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
        return after(now(), amount, unit);
    }

    public static Instant after(Instant instant, long amount, ChronoUnit unit) {
        return instant.plus(amount, unit);
    }

    public static Instant beforeNow(long amount, ChronoUnit unit) {
        return before(now(), amount, unit);
    }

    public static Instant before(Instant instant, long amount, ChronoUnit unit) {
        return instant.minus(amount, unit);
    }

}
