package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Item;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class DbHelper<T extends Item> {
    protected JdbcTemplate jdbcTemplate;

    protected DbHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    abstract Object[] getArgs(T item);

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

    protected abstract String getInsertSql();

    protected abstract String getEntityType();

    protected abstract RowMapper<T> getRowMapper();

    protected abstract String getFindSql();

    public T find(Long id) {
        List<T> result = query(getFindSql(), new Object[]{id}, getRowMapper());
        if (result.isEmpty())
            return null;
        return result.get(0);
    }
}