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

    List<Lend> gets(Lend where, Integer page, Integer limits);

}
