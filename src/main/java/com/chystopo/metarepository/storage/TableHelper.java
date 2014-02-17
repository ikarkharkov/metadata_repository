package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.storage.mapper.TableMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class TableHelper extends BasicModelHelper<Table> {
    private static final String FIND_ONE_SQL = "SELECT * FROM basic_entity be INNER JOIN tables t on be.id=t.id WHERE be.id=?";

    protected TableHelper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getEntityType() {
        return "table";
    }

    @Override
    protected Table insert(Table table) {
        table = super.insert(table);

        update("INSERT INTO tables(id) VALUES(?)", new Object[]{table.getId()});
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
}
