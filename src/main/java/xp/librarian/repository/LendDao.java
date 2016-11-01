package xp.librarian.repository;

import java.util.*;

import xp.librarian.model.dto.Lend;

/**
 * @author xp
 */
public interface LendDao {

    int add(Lend lend);

    int update(Lend where, Lend set);

    Lend get(Integer lendId);

    Lend get(Lend where);

    List<Lend> gets(Lend where, Integer page, Integer limits);

}
