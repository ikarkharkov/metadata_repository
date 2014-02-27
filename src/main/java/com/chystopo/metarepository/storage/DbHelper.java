package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DbHelper<T extends Item> {
    public static final String INSERT_SQL
            = "INSERT INTO basic_entity(context, parent_id, entity_type, name, model_id, schema_id, table_id, view_id) VALUES(?,?,?,?,?,?,?,?) RETURNING ID";
    protected JdbcTemplate jdbcTemplate;

    protected DbHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Object[] getArgs(T item) {
        return new Object[]{item.getContext(),
                item.getParent() == null ? null : item.getParent().getId(),
                getEntityType(),
                item.getName(),
                getModelId(item),
                getSchemaId(item),
                getTableId(item),
                getViewId(item)};
    }

    private Long getViewId(T item) {
        return null;
    }

    private Long getTableId(T item) {
        if (item instanceof Column) {
            return item.getParentId();
        }
        return null;
    }

    private Long getSchemaId(T item) {
        if (item instanceof Table) {
            return item.getParentId();
        } else if (item instanceof Column) {
            return item.getParent().getParentId();
        }
        return null;
    }

    private Long getModelId(T item) {
        if (item instanceof Schema) {
            return item.getParentId();
        } else if (item instanceof Table && item.getParent() != null) {
            return item.getParent().getParentId();
        } else if (item instanceof Column && item.getParent() != null && item.getParent().getParent() != null) {
            return item.getParent().getParent().getParentId();
        }
        return null;
    }

    public T save(T item) {
        if (item.getId() == null) {
            return insert(item);
        } else {
            update(item);
            return item;
        }
    }

    protected T insert(T item) {
        jdbcTemplate.query(getInsertSql(), getArgs(item), new IdFetcher(item));
        return item;
    }

    private String getInsertSql() {
        return INSERT_SQL;
    }

    protected void update(T item) {
        jdbcTemplate.execute(getUpdateSQL(), new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                return null;
            }
        });
    }

    protected abstract String getUpdateSQL();

    protected List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, args, rowMapper);
    }

    protected void update(String sql, Object[] args) {
        jdbcTemplate.update(sql, args);
    }

    protected abstract String getEntityType();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String getFindSql();

    public T find(Long id) {
        List<T> result = query(getFindSql(), new Object[]{id}, getRowMapper());
        if (result.isEmpty())
            return null;
        return result.get(0);
    }

    public T findByPath(String context, String path) {
        List<T> result = query(getByFindPublicIdSql(), new Object[]{context, path}, getRowMapper());
        if (result.isEmpty())
            return null;
        return result.get(0);
    }

    protected abstract String getByFindPublicIdSql();
}