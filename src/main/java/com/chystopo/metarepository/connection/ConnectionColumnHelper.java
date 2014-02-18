package com.chystopo.metarepository.connection;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.ConnectionItem;
import com.chystopo.metarepository.storage.IdFetcher;
import com.chystopo.metarepository.storage.mapper.ColumnMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ConnectionColumnHelper {
    private static final String INSERT_SQL = "INSERT INTO connections(source, destination) VALUES(?, ?) RETURNING ID";
    public static final String FIND_SOURCE_COLUMNS_BY_DESTINATION = "SELECT be.*, col.column_type, col.formula FROM closure_connection con INNER JOIN basic_entity be ON con.source=be.id INNER JOIN columns col ON be.id=col.id WHERE destination=?";
    public static final String INSERT_CLOSURE_CONNECTION = "INSERT INTO closure_connection(source, destination) VALUES (?, ?)";
    private final JdbcTemplate jdbcTemplate;

    protected ConnectionColumnHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ConnectionItem save(ConnectionItem item) {
        if (item.getId() == null)
            return insert(item);
        else
            return update(item);
    }

    private ConnectionItem update(ConnectionItem item) {
        return null;
    }

    private ConnectionItem insert(ConnectionItem item) {
        jdbcTemplate.query(INSERT_SQL, getArgs(item), new IdFetcher(item));
        createCloserRecords(item);
        return item;
    }

    private void createCloserRecords(ConnectionItem item) {
        insertCloserRecord(item.getSourceId(), item.getDestinationId());
        recursiveClosureUpdate(item.getSourceId(), item.getDestinationId());
    }

    private void insertCloserRecord(Long sourceId, Long destinationId) {
        jdbcTemplate.update(INSERT_CLOSURE_CONNECTION, sourceId, destinationId);
    }

    private void recursiveClosureUpdate(Long sourceId, Long destinationId) {
        List<Long> sources = jdbcTemplate.query("SELECT source FROM closure_connection WHERE destination=?", new Object[]{sourceId}, new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getLong("source");
            }
        });
        if (!sources.isEmpty()) {
            for (Long source : sources) {
                insertCloserRecord(source, destinationId);
            }
        }
    }

    private Object[] getArgs(ConnectionItem item) {
        return new Object[]{item.getSourceId(), item.getDestinationId()};
    }

    public List<Column> findSourceColumnsByDestination(Column column) {
        List<Column> result = jdbcTemplate.query(FIND_SOURCE_COLUMNS_BY_DESTINATION, new Object[]{column.getId()}, new ColumnMapper());
        return result;
    }
}
