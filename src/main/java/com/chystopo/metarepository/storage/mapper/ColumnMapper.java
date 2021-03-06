package com.chystopo.metarepository.storage.mapper;

import com.chystopo.metarepository.bean.Column;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnMapper extends ItemMapper<Column> {
    @Override
    public Column mapRow(ResultSet rs, int rowNum) throws SQLException {
        Column column = super.mapRow(rs, rowNum);
        column.setType(rs.getString("column_type"));
        column.setFormula(rs.getString("formula"));
        return column;
    }

    @Override
    protected Column createItem() {
        return new Column();
    }
}
