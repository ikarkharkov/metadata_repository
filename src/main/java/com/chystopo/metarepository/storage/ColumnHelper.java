package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.storage.mapper.ColumnMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class ColumnHelper extends BasicModelHelper<Column> {

    public static final String INSERT_SQL = "INSERT INTO columns(id, column_type, formula) VALUES(:id, :column_type, :formula)";
    public static final String BASIC_QUERY = "SELECT be.*, cl.formula, cl.column_type FROM basic_entity be INNER JOIN columns cl ON be.id=cl.id ";
    public static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=:id";
    public static final String FIND_ONE_BY_PUBLIC_ID_SQL = BASIC_QUERY + "WHERE be.context = :context";

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
        args.put("column_type", column.getType());
        args.put("formula", column.getFormula());
        update(INSERT_SQL, args);
        return column;
    }

    @Override
    protected String getUpdateSQL() {
        return "UPDATE columns set type=?, formula=? where id=?";
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
    protected String getByFindPublicIdSql() {
        return FIND_ONE_BY_PUBLIC_ID_SQL;
    }
}
