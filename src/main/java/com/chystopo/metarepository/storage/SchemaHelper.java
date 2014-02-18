package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.storage.mapper.SchemaMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SchemaHelper extends BasicModelHelper<Schema> {
    public static final String BASIC_QUERY = "SELECT be.* FROM basic_entity be INNER JOIN schemas s ON be.id=s.id ";
    private static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=?";
    private static final String FIND_ONE_BY_PUBLIC_ID_SQL = BASIC_QUERY + "WHERE be.context = ? and be.public_id=?";
    public static final String INSERT_SQL = "INSERT INTO schemas(id) VALUES(?)";

    public SchemaHelper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected Schema insert(Schema schema) {
        schema = super.insert(schema);
        update(INSERT_SQL, new Object[]{schema.getId()});
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
    protected String getByFindPublicIdSql() {
        return FIND_ONE_BY_PUBLIC_ID_SQL;
    }
}
