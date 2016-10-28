package xp.librarian.config.mybatis.type;

import java.sql.*;
import java.time.*;
import java.util.*;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author xp
 */
@MappedTypes(Instant.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class InstantTypeHandler extends BaseTypeHandler<Instant> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Timestamp.from(parameter));
    }

    @Override
    public Instant getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Optional.ofNullable(rs.getTimestamp(columnName)).map(Timestamp::toInstant).orElse(null);
    }

    @Override
    public Instant getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Optional.ofNullable(rs.getTimestamp(columnIndex)).map(Timestamp::toInstant).orElse(null);
    }

    @Override
    public Instant getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Optional.ofNullable(cs.getTimestamp(columnIndex)).map(Timestamp::toInstant).orElse(null);
    }
}
