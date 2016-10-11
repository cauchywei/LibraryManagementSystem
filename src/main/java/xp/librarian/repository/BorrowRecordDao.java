package xp.librarian.repository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xp.librarian.model.dto.BorrowRecordDto;
import xp.librarian.repository.mapper.BorrowRecordMapper;

/**
 * @author xp
 */
@Repository
public class BorrowRecordDao {

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    public int add(BorrowRecordDto dto) {
        return borrowRecordMapper.insert(dto);
    }

    public int update(BorrowRecordDto dto) {
        return borrowRecordMapper.update(dto);
    }

    public BorrowRecordDto get(Integer userId, String ISBN, BorrowRecordDto.Status status) {
        return borrowRecordMapper.select(userId, ISBN, status);
    }

    public List<BorrowRecordDto> gets(Integer userId, int page, int limits) {
        int offset = (page - 1) * limits;
        return borrowRecordMapper.selectList(userId, offset, limits);
    }

}
