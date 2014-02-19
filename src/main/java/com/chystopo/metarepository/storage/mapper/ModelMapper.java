package com.chystopo.metarepository.storage.mapper;

import com.chystopo.metarepository.bean.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelMapper extends ItemMapper<Model> {
    @Override
    public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
        Model result = super.mapRow(rs, rowNum);
        result.setType(rs.getString("model_type"));
        return result;
    }

    @Override
    protected Model createItem() {
        return new Model();
    }
}
