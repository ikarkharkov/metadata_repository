package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.storage.mapper.SchemaMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class SchemaHelper extends BasicModelHelper<Schema> {
    private static final String FIND_ONE_SQL = "SELECT * FROM basic_entity be INNER JOIN schemas s ON be.id=s.id WHERE be.id=?";

    public SchemaHelper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected Schema insert(Schema schema) {
        schema = super.insert(schema);
        update("INSERT INTO schemas(id) VALUES(?)", new Object[]{schema.getId()});
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
}
