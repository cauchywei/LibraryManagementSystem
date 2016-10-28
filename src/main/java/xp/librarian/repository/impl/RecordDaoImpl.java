package xp.librarian.repository.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.NonNull;
import xp.librarian.model.dto.Record;
import xp.librarian.repository.RecordDao;
import xp.librarian.repository.mapper.RecordMapper;

/**
 * @author xp
 */
@Repository
public class RecordDaoImpl implements RecordDao {

    @Autowired
    private RecordMapper recordMapper;

    public int add(@NonNull Record record) {
        return recordMapper.insert(record);
    }

    public List<Record> select(@NonNull Record where,
                               @NonNull Integer page,
                               @NonNull Integer limits) {
        Integer offset = (page - 1) * limits;
        return recordMapper.select(where, offset, limits);
    }

}
