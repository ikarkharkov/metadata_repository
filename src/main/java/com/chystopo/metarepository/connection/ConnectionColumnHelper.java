package com.chystopo.metarepository.connection;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.ConnectionItem;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.storage.IdFetcher;
import com.chystopo.metarepository.storage.mapper.ColumnMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionColumnHelper {
    private static final String INSERT_SQL = "INSERT INTO connections(source, destination) VALUES(:sourceId, :destinationId) RETURNING ID";
    public static final String FIND_SOURCE_COLUMNS_BY_DESTINATION = "SELECT be.*, col.column_type, col.formula FROM closure_connection con INNER JOIN basic_entity be ON con.source=be.id INNER JOIN columns col ON be.id=col.id WHERE destination=:destinationId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    protected ConnectionColumnHelper(NamedParameterJdbcTemplate jdbcTemplate) {
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
        Map<String, Object> args = new HashMap<String, Object>();
    }

    private void recursiveClosureUpdate(Long sourceId, Long destinationId) {

    }

    private Map<String, Object> getArgs(ConnectionItem item) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("sourceId", item.getSourceId());
        args.put("destinationId", item.getDestinationId());
        return args;
    }

    public List<Column> findSourceColumnsByDestination(Item column) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("destinationId", column.getId());
        List<Column> result = jdbcTemplate.query(FIND_SOURCE_COLUMNS_BY_DESTINATION, args, new ColumnMapper());
        return result;
    }
}
