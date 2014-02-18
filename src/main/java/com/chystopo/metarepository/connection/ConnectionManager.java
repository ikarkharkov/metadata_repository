package com.chystopo.metarepository.connection;

import com.chystopo.metarepository.IConnectionManager;
import com.chystopo.metarepository.bean.*;
import com.chystopo.metarepository.storage.ColumnHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Component
@Transactional
public class ConnectionManager implements IConnectionManager {

    private JdbcTemplate jdbcTemplate;
    private ColumnHelper columnHelper;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        columnHelper = new ColumnHelper(jdbcTemplate);
    }

    @Override
    public void save(Column column) {
        Column destinationColumn = columnHelper.findByPublicId(column.getContext(), column.getPublicId());
        ConnectionColumnHelper connectionColumnHelper = new ConnectionColumnHelper(jdbcTemplate);
        for (Long sourceId : column.getSources()) {
            Column sourceColumn = columnHelper.findByPublicId(column.getContext(), sourceId);
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
    public List<? extends Item> findColumnLinageByPublicId(String context, long publicId) {
        Column destinationColumn = columnHelper.findByPublicId(context, publicId);
        ConnectionColumnHelper connectionColumnHelper = new ConnectionColumnHelper(jdbcTemplate);
        List<Column> sourceColumns = connectionColumnHelper.findSourceColumnsByDestination(destinationColumn);
        return sourceColumns;
    }
}
