package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.HasId;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IdFetcher implements RowMapper<Long> {
    private final HasId item;

    public IdFetcher(HasId item) {
        this.item = item;
    }

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        item.setId(resultSet.getLong(1));
        return item.getId();
    }
}
