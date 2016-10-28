package xp.librarian.repository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.Lend;
import xp.librarian.repository.mapper.LendMapper;

/**
 * @author xp
 */
@Repository
public class LendDao {

    @Autowired
    private LendMapper lendMapper;

    public int add(@NonNull Lend lend) {
        return lendMapper.insert(lend);
    }

    public int update(@NonNull Lend where,
                      @NonNull Lend set) {
        return lendMapper.update(where, set);
    }

    public Lend get(@NonNull Integer lendId) {
        Lend where = new Lend();
        where.setId(lendId);
        return gets(where, 1, 1).stream()
                .findFirst().orElse(null);
    }

    public List<Lend> gets(@NonNull Lend where,
                           @NonNull Integer page,
                           @NonNull Integer limits) {
        int offset = (page - 1) * limits;
        return lendMapper.select(where, offset, limits);
    }

}
