package com.chystopo.metarepository.storage.mapper;

import com.chystopo.metarepository.bean.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BasicModelMapper<T extends Item> implements RowMapper<T> {
    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T item = createItem();
        item.setId(rs.getLong("id"));
        item.setParentId(rs.getLong("parent_id"));
        item.setName(rs.getString("name"));
        item.setContext(rs.getString("context"));
        item.setPublicId(rs.getLong("public_id"));
        return item;
    }

    protected abstract T createItem();
}
