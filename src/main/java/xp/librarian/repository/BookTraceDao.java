package xp.librarian.repository;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.BookTrace;
import xp.librarian.repository.mapper.BookTraceMapper;

/**
 * @author xp
 */
@Repository
public class BookTraceDao {

    @Autowired
    private BookTraceMapper traceMapper;

    private static final Function<Boolean, Predicate<BookTrace>> forceFilter =
            force -> e -> force || BookTrace.Status.DELETED.equals(e.getStatus());

    public int add(@NonNull BookTrace trace) {
        return traceMapper.insert(trace);
    }

    public int update(@NonNull BookTrace where,
                      @NonNull BookTrace set) {
        return traceMapper.update(where, set);
    }

    public BookTrace get(@NonNull Integer traceId) {
        return get(traceId, false);
    }

    public BookTrace get(@NonNull Integer traceId,
                         boolean force) {
        BookTrace where = new BookTrace();
        where.setId(traceId);
        return gets(where, 1, 1, force).stream()
                .findFirst().orElse(null);

    }

    public List<BookTrace> gets(@NonNull BookTrace where,
                                @NonNull Integer page,
                                @NonNull Integer limits) {
        return gets(where, page, limits, false);
    }

    public List<BookTrace> gets(@NonNull BookTrace where,
                                @NonNull Integer page,
                                @NonNull Integer limits,
                                boolean force) {
        Integer offset = (page - 1) * limits;
        return traceMapper.select(where, offset, limits).stream()
                .filter(forceFilter.apply(force))
                .collect(Collectors.toList());
    }

}
