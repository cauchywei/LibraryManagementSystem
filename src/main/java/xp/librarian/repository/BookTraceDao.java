package xp.librarian.repository;

import java.util.*;

import xp.librarian.model.dto.BookTrace;

/**
 * @author xp
 */
public interface BookTraceDao {

    int add(BookTrace trace);

    int update(BookTrace where, BookTrace set);

    BookTrace get(Integer traceId);

    BookTrace get(Integer traceId, boolean force);

    List<BookTrace> gets(BookTrace where, Integer page, Integer limits);

    List<BookTrace> gets(BookTrace where, Integer page, Integer limits, boolean force);

}
