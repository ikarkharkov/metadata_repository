package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.storage.mapper.ColumnMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class ColumnHelper extends BasicModelHelper<Column> {

    public static final String INSERT_SQL = "INSERT INTO columns(id, column_type, formula) VALUES(:id, :columnType, :formula)";
    public static final String BASIC_QUERY = "SELECT be.*, cl.formula, cl.column_type FROM basic_entity be INNER JOIN columns cl ON be.id=cl.id ";
    public static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=:id";
    public static final String FIND_ONE_BY_PATH_SQL
            = "SELECT\n" +
            "  be1.*,\n" +
            "  cl.column_type,\n" +
            "  cl.formula\n" +
            "FROM basic_entity be1\n" +
            "  JOIN basic_entity be2 ON be2.id = be1.table_id\n" +
            "  JOIN basic_entity be3 ON be3.id = be1.schema_id\n" +
            "  JOIN basic_entity be4 ON be4.id = be1.model_id\n" +
            "  JOIN columns cl ON be1.id = cl.id\n" +
            "WHERE\n" +
            "  be1.name = :columnName AND be1.entity_type = 'column' AND be1.context = :context\n" +
            "  AND be2.name = :tableName AND be2.entity_type = 'table' AND be2.context = :context\n" +
            "  AND be3.name = :schemaName AND be3.entity_type = 'schema' AND be3.context = :context\n" +
            "  AND be4.name = :modelName AND be4.entity_type = 'model' AND be4.context = :context";

    public ColumnHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getEntityType() {
        return "column";
    }

    @Override
    protected Column insert(Column column) {
        column = super.insert(column);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("id", column.getId());
        args.put("columnType", column.getType());
        args.put("formula", column.getFormula());
        update(INSERT_SQL, args);
        return column;
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE columns set type=:columnType, formula=:formulaType where id=:id";
    }

    @Override
    protected RowMapper<Column> getRowMapper() {
        return new ColumnMapper();
    }

    @Override
    protected String getFindSql() {
        return FIND_ONE_SQL;
    }

    @Override
    protected String getByFindContextAndPathSql() {
        return FIND_ONE_BY_PATH_SQL;
    }
}
