package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.storage.mapper.TableMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class TableHelper extends BasicModelHelper<Table> {
    public static final String BASIC_QUERY = "SELECT be.* FROM basic_entity be INNER JOIN tables t on be.id=t.id ";
    private static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=:id";
    private static final String FIND_ONE_BY_PATH = "SELECT\n" +
            "  be1.*\n" +
            "FROM basic_entity be1\n" +
            "  JOIN basic_entity be2 ON be2.id = be1.schema_id\n" +
            "  JOIN basic_entity be3 ON be3.id = be1.model_id\n" +
            "WHERE\n" +
            "  be1.name = :tableName AND be1.entity_type = 'table' AND be1.context = :context\n" +
            "  AND be2.name = :schemaName AND be2.entity_type = 'schema' AND be2.context = :context\n" +
            "  AND be3.name = :modelName AND be3.entity_type = 'model' AND be3.context = :context";
    public static final String INSERT_SQL = "INSERT INTO tables(id) VALUES(:id)";

    protected TableHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getEntityType() {
        return "table";
    }

    @Override
    protected Table insert(Table table) {
        table = super.insert(table);

        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", table.getId());
        update(INSERT_SQL, args);
        DbHelper<Column> columnHelper = new ColumnHelper(jdbcTemplate);
        for (Column column : table.getColumns()) {
            column.setParent(table);
            columnHelper.save(column);
        }
        return table;
    }

    @Override
    protected String getUpdateSQL() {
        return null;
    }

    @Override
    protected RowMapper<Table> getRowMapper() {
        return new TableMapper();
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
