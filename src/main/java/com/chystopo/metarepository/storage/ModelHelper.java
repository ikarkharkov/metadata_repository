package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.storage.mapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ModelHelper extends BasicModelHelper<Model> {

    public static final String BASIC_QUERY = "SELECT be.*, m.type FROM basic_entity be inner join models m ON be.id=m.id ";
    public static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=?";
    public static final String FIND_ONE_BY_PUBLIC_ID_SQL = BASIC_QUERY + "WHERE be.context=? and be.public_id=?";
    public static final String INSERT_SQL = "INSERT INTO models(id, type) VALUES(?, ?)";

    public ModelHelper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getEntityType() {
        return "model";
    }

    @Override
    protected Model insert(Model model) {
        model = super.insert(model);
        update(INSERT_SQL, new Object[]{model.getId(), model.getType()});
        DbHelper<Schema> schemaHelper = new SchemaHelper(jdbcTemplate);
        for (Schema schema : model.getSchemas()) {
            schema.setParent(model);
            schemaHelper.save(schema);
        }
        return model;
    }

    @Override
    protected String getUpdateSQL() {
        return null;
    }

    @Override
    protected RowMapper<Model> getRowMapper() {
        return new ModelMapper();
    }

    @Override
    protected String getFindSql() {
        return FIND_ONE_SQL;
    }

    @Override
    protected String getByFindPublicIdSql() {
        return FIND_ONE_BY_PUBLIC_ID_SQL;
    }
}


