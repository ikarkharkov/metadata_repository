package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Saver {
    public static final String COLUMN_INSERT = "INSERT INTO model(entity_type, name, type, formula,  parent_id) VALUES('column', ?, ?, ?, ?) RETURNING id";
    public static final String TABLE_INSERT = "INSERT INTO model(entity_type, name, parent_id) VALUES('table', ?, ?) RETURNING id";
    public static final String SCHEMA_INSERT = "INSERT INTO model(entity_type, name, parent_id) VALUES('schema', ?, ?) RETURNING id";
    public static final String MODEL_INSERT = "INSERT INTO model(entity_type, name, type, parent_id) VALUES('model', ?, ?, ?) RETURNING id";
    private JdbcTemplate jdbcTemplate;

    public Saver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Column save(Column item) {
        return new ColumnInsertHelper(item, COLUMN_INSERT, jdbcTemplate).insert();
    }

    public Table save(Table item) {
        return new TableInsertHelper(item, TABLE_INSERT, jdbcTemplate).insert();
    }

    public Schema save(Schema item) {
        return new SchemaInsertHelper(item, SCHEMA_INSERT, jdbcTemplate).insert();
    }

    public Model save(Model item) {
        return new ModelInsertHelper(item, MODEL_INSERT, jdbcTemplate).insert();
    }

    private static class IdFetcher implements RowMapper<Long> {
        private final Item item;

        public IdFetcher(Item item) {
            this.item = item;
        }

        @Override
        public Long mapRow(ResultSet resultSet, int i) throws SQLException {
            item.setId(resultSet.getLong(1));
            return item.getId();
        }
    }

    private static abstract class InsertHelper<T extends Item> {
        protected JdbcTemplate jdbcTemplate;
        protected T item;
        protected String sql;

        protected InsertHelper(T item, String sql, JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
            this.item = item;
            this.sql = sql;
        }

        abstract Object[] getArgs();

        protected T insert() {
            jdbcTemplate.query(sql, getArgs(), new IdFetcher(item));
            return item;
        }

        protected T getItem() {
            return item;
        }
    }

    private static class ColumnInsertHelper extends InsertHelper<Column> {

        private ColumnInsertHelper(Column item, String sql, JdbcTemplate jdbcTemplate) {
            super(item, sql, jdbcTemplate);
        }

        @Override
        public Object[] getArgs() {
            return new Object[]{item.getName(), item.getType(), item.getFormula(), item.getParent() == null ? null : item.getParent().getId()};
        }
    }

    private class TableInsertHelper extends InsertHelper<Table> {
        private TableInsertHelper(Table item, String sql, JdbcTemplate jdbcTemplate) {
            super(item, sql, jdbcTemplate);
        }

        @Override
        public Object[] getArgs() {
            return new Object
                    []{item.getName(), item.getParent() == null ? null : item.getParent().getId()};
        }

        @Override
        protected Table insert() {
            super.insert();
            for (Column column : item.getColumns()) {
                column.setParent(item);
                save(column);
            }
            return item;
        }
    }

    private class SchemaInsertHelper extends InsertHelper<Schema> {
        private SchemaInsertHelper(Schema item, String sql, JdbcTemplate jdbcTemplate) {
            super(item, sql, jdbcTemplate);
        }

        @Override
        public Object[] getArgs() {
            return new Object[]{item.getName(), item.getParent() == null ? null : item.getParent().getId()};
        }

        @Override
        protected Schema insert() {
            super.insert();
            for (Table table : item.getTables()) {
                table.setParent(item);
                save(table);
            }
            return item;
        }
    }

    private class ModelInsertHelper extends InsertHelper<Model> {
        private ModelInsertHelper(Model item, String sql, JdbcTemplate jdbcTemplate) {
            super(item, sql, jdbcTemplate);
        }

        @Override
        public Object[] getArgs() {
            return new Object[]{item.getName(), item.getType(), item.getParent() == null ? null : item.getParent().getId()};
        }

        @Override
        protected Model insert() {
            super.insert();

            for (Schema schema : item.getSchemas()) {
                schema.setParent(item);
                save(schema);
            }
            return item;
        }
    }
}
