package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.storage.mapper.ModelMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class ModelHelper extends BasicModelHelper<Model> {

    private static final String BASIC_QUERY = "SELECT be.*, m.type as model_type " +
            "FROM basic_entity be inner join models m ON be.id=m.id ";
    private static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=:id";
    private static final String FIND_ONE_BY_PATH = "SELECT\n" +
            "  be1.*, m.type as model_type\n" +
            "FROM basic_entity be1\n" +
            "JOIN models m on be1.id=m.id " +
            "WHERE\n" +
            "  be1.name = :modelName AND be1.entity_type = 'model' AND be1.context = :context";
    private static final String INSERT_SQL = "INSERT INTO models(id, type) VALUES(:modelId, :type)";

    public ModelHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getEntityType() {
        return "model";
    }

    @Override
    protected Model insert(Model model) {
        model = super.insert(model);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("modelId", model.getId());
        args.put("type", model.getType());

        update(INSERT_SQL, args);
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
    protected String getByFindContextAndPathSql() {
        return FIND_ONE_BY_PATH;
    }
}


