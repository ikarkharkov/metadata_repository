package com.chystopo.metarepository.connection;

import com.chystopo.metarepository.IConnectionManager;
import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.*;
import com.chystopo.metarepository.storage.ColumnHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
@Transactional
public class ConnectionManager implements IConnectionManager {

    @Autowired
    private IStorage storage;

    private JdbcTemplate jdbcTemplate;
    private ColumnHelper columnHelper;
    private ConnectionColumnHelper connectionColumnHelper;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        columnHelper = new ColumnHelper(jdbcTemplate);
        connectionColumnHelper = new ConnectionColumnHelper(jdbcTemplate);
    }

    private void save(Column column) {
        Column destinationColumn = columnHelper.findByPath(column.getContext(), "column.path");

        for (Long pathId : column.getSources()) {
            Column sourceColumn = columnHelper.findByPath(column.getContext(), pathId.toString());
            ConnectionItem item = new ConnectionItem();
            item.setContext(column.getContext());
            item.setDestinationId(destinationColumn.getId());
            item.setSourceId(sourceColumn.getId());
            connectionColumnHelper.save(item);
        }
    }

    @Override
    public void save(List<Mapping> mappings) {
        for (Mapping mapping : mappings) {
            for (Connection connection : mapping.getConnections()) {
                if ("target".equals(connection.getType())) {
                    for (Schema schema : connection.getSchemas()) {
                        for (Table table : schema.getTables()) {
                            for (Column column : table.getColumns()) {
                                if (column.getSources() != null && !column.getSources().isEmpty()) {
                                    save(column);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Collection<? extends Item> findTargets(Item source) {
        return null;
    }

    @Override
    public Collection<Column> findSources(Item target) {
        ConnectionColumnHelper connectionColumnHelper = new ConnectionColumnHelper(jdbcTemplate);
        if (target instanceof Column) {
            return connectionColumnHelper.findSourceColumnsByDestination(target);
        } else if (target instanceof Table) {
            Collection<Column> result = new HashSet<Column>();
            for (Item child : storage.findChildren(target)) {
                for (Column sourceItem : findSources(child)) {
                    result.add(sourceItem);
                }
            }
            return result;
        } else if (target instanceof Schema) {
            Collection<Column> result = new HashSet<Column>();
            for (Item child : storage.findChildren(target)) {
                result.addAll(findSources(child));
            }

            return result;
        }
        return new ArrayList<Column>();
    }
}
