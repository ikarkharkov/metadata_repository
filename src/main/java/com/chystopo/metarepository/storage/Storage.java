package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.*;
import com.chystopo.metarepository.storage.mapper.GlobalItemRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class Storage implements IStorage {

    public static final String AND_PARENT_ID = "AND parent_id=?";
    public static final String GLOBAL_FETCH_BY_PATH = "SELECT " +
            "  be.*," +
            "  col.column_type AS column_type," +
            "  col.formula," +
            "  md.type AS model_type " +
            "FROM basic_entity be " +
            "  LEFT JOIN columns col ON be.id = col.id" +
            "  LEFT JOIN tables tb ON be.id = tb.id " +
            "  LEFT JOIN schemas sc ON be.id = sc.id " +
            "  LEFT JOIN models md ON be.id = md.id " +
            "WHERE path LIKE ? ";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
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
    public Item findById(ItemType type, Long publicId) {
        return null;
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
    public Column findColumnByPublicIdAndContext(long publicId, String context) {
        return new ColumnHelper(jdbcTemplate).findByPublicId(context, publicId);
    }

    @Override
    public Table findTableByPublicIdAndContext(long publicId, String context) {
        return new TableHelper(jdbcTemplate).findByPublicId(context, publicId);
    }

    @Override
    public Schema findSchemaByPublicIdAndContext(long publicId, String context) {
        return new SchemaHelper(jdbcTemplate).findByPublicId(context, publicId);
    }

    @Override
    public Model findModelByPublicIdAndContext(long publicId, String context) {
        return new ModelHelper(jdbcTemplate).findByPublicId(context, publicId);
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
    public Collection<? extends Item> findChildren(Item item, boolean recursively) {
        if (recursively) {
            return jdbcTemplate.query(GLOBAL_FETCH_BY_PATH, new Object[]{item.getPath() + item.getId() + "%"},
                    new GlobalItemRowMapper()
            );
        } else {
            return jdbcTemplate.query(GLOBAL_FETCH_BY_PATH + AND_PARENT_ID, new Object[]{item.getPath() + item.getId() + "%", item.getId()},
                    new GlobalItemRowMapper()
            );
        }
    }

    @Override
    public Collection<? extends Item> findChildren(Item item) {
        return findChildren(item, false);
    }
}
