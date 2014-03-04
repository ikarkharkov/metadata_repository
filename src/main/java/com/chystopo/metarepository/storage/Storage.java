package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.*;
import com.chystopo.metarepository.storage.mapper.GlobalItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class Storage implements IStorage {

    public static final String AND_PARENT_ID = "WHERE parent_id=:parentId";
    public static final String GLOBAL_FETCH_BY_PATH = "SELECT " +
            "  be.*," +
            "  col.column_type AS column_type," +
            "  col.formula," +
            "  md.type AS model_type " +
            "FROM basic_entity be " +
            "  LEFT JOIN columns col ON be.id = col.id" +
            "  LEFT JOIN tables tb ON be.id = tb.id " +
            "  LEFT JOIN schemas sc ON be.id = sc.id " +
            "  LEFT JOIN models md ON be.id = md.id ";
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Column saveOrUpdate(Column item) {
        return new ColumnHelper(jdbcTemplate).save(item);
    }

    @Override
    public Table saveOrUpdate(Table item) {
        return new TableHelper(jdbcTemplate).save(item);
    }

    @Override
    public Schema saveOrUpdate(Schema item) {
        return new SchemaHelper(jdbcTemplate).save(item);
    }

    @Override
    public Model saveOrUpdate(Model item) {
        return new ModelHelper(jdbcTemplate).save(item);
    }

    @Override
    public Column findColumnById(Long id) {
        return new ColumnHelper(jdbcTemplate).find(id);
    }

    @Override
    public Collection<Item> findParents(Item item) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Column findColumnByPathAndContext(String path, String context) {
        Map<String, String> pathElements = splitPath(path);
        return new ColumnHelper(jdbcTemplate).findByPath(context, pathElements);
    }

    private Map<String, String> splitPath(String path) {
        String[] elements = path.split("\\.");
        Map<String, String> result = new HashMap<String, String>();
        switch (elements.length) {
            case 4:
                result.put("columnName", elements[3]);
            case 3:
                result.put("tableName", elements[2]);
            case 2:
                result.put("schemaName", elements[1]);
            case 1:
                result.put("modelName", elements[0]);
        }

        return result;
    }

    @Override
    public Table findTableByPathAndContext(String path, String context) {
        Map<String, String> pathElements = splitPath(path);
        return new TableHelper(jdbcTemplate).findByPath(context, pathElements);
    }

    @Override
    public Schema findSchemaByPathAndContext(String path, String context) {
        Map<String, String> pathElements = splitPath(path);
        return new SchemaHelper(jdbcTemplate).findByPath(context, pathElements);
    }

    @Override
    public Model findModelByPathAndContext(String path, String context) {
        Map<String, String> pathElements = splitPath(path);
        return new ModelHelper(jdbcTemplate).findByPath(context, pathElements);
    }

    @Override
    public Table findTableById(Long id) {
        return new TableHelper(jdbcTemplate).find(id);
    }

    @Override
    public Schema findSchemaById(Long id) {
        return new SchemaHelper(jdbcTemplate).find(id);
    }

    @Override
    public Model findModelById(Long id) {
        return new ModelHelper(jdbcTemplate).find(id);
    }

    @Override
    public void saveOrUpdate(List<Model> models) {
        for (Model model : models) {
            saveOrUpdate(model);
        }
    }

    @Override
    public Collection<? extends Item> findChildren(Item item) {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("parentId", item.getId());
        return jdbcTemplate.query(GLOBAL_FETCH_BY_PATH + AND_PARENT_ID, args,
                new GlobalItemRowMapper()
        );
    }
}
