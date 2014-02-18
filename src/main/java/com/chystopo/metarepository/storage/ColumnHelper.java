package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.storage.mapper.ColumnMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ColumnHelper extends BasicModelHelper<Column> {

    public static final String INSERT_SQL = "INSERT INTO columns(id, column_type, formula) VALUES(?, ?, ?)";
    public static final String BASIC_QUERY = "SELECT be.*, cl.formula, cl.column_type FROM basic_entity be INNER JOIN columns cl ON be.id=cl.id ";
    public static final String FIND_ONE_SQL = BASIC_QUERY + "WHERE be.id=?";
    public static final String FIND_ONE_BY_PUBLIC_ID_SQL = BASIC_QUERY + "WHERE be.context = ? and be.public_id=?";

    public ColumnHelper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getEntityType() {
        return "column";
    }

    @Override
    protected Column insert(Column column) {
        column = super.insert(column);
        update(INSERT_SQL, new Object[]{column.getId(), column.getType(), column.getFormula()});
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
