package com.chystopo.metarepository.storage.mapper;

import com.chystopo.metarepository.bean.Item;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GlobalItemRowMapper implements RowMapper<Item> {
    @Override
    public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
        String entityType = rs.getString("entity_type");
        if ("column".equalsIgnoreCase(entityType))
            return new ColumnMapper().mapRow(rs, rowNum);
        else if ("table".equalsIgnoreCase(entityType))
            return new TableMapper().mapRow(rs, rowNum);
        else if ("schema".equalsIgnoreCase(entityType))
            return new SchemaMapper().mapRow(rs, rowNum);
        else if ("model".equalsIgnoreCase(entityType))
            return new ModelMapper().mapRow(rs, rowNum);
        return null;
    }
}
