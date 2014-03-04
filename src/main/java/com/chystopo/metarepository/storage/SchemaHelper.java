package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.storage.mapper.SchemaMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class SchemaHelper extends BasicModelHelper<Schema> {
    public static final String BASIC_QUERY = "SELECT be.* FROM basic_entity be INNER JOIN schemas s ON be.id=s.id ";
    private static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=:id";
    private static final String FIND_ONE_BY_PATH = "SELECT\n" +
            "  be1.*\n" +
            "FROM basic_entity be1\n" +
            "  JOIN basic_entity be2 ON be2.id = be1.model_id\n" +
            "WHERE\n" +
            "  be1.name = :schemaName AND be1.entity_type = 'schema' AND be1.context = :context\n" +
            "  AND be2.name = :modelName AND be2.entity_type = 'model' AND be2.context = :context";
    public static final String INSERT_SQL = "INSERT INTO schemas(id) VALUES(:id)";

    public SchemaHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected Schema insert(Schema schema) {
        schema = super.insert(schema);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", schema.getId());
        update(INSERT_SQL, args);
        DbHelper<Table> tableHelper = new TableHelper(jdbcTemplate);
        for (Table table : schema.getTables()) {
            table.setParent(schema);

            tableHelper.save(table);
        }
        return schema;
    }

    @Override
    protected String getUpdateSQL() {
        return null;
    }

    @Override
    protected String getEntityType() {
        return "schema";
    }

    @Override
    protected RowMapper<Schema> getRowMapper() {
        return new SchemaMapper();
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
