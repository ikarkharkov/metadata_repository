package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.IModelStorage;
import com.chystopo.metarepository.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
public class Storage implements IModelStorage {

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
    public Collection<Item> findChildren(Item item) {
        return null;
    }
}