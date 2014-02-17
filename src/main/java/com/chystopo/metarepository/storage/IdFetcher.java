package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by oleksiy on 17/02/14.
 */
public class IdFetcher implements RowMapper<Long> {
    private final Item item;

    public IdFetcher(Item item) {
        this.item = item;
    }

    @Override
    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
        item.setId(resultSet.getLong(1));
        return item.getId();
    }
}
