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
public class ModelStorage implements IModelStorage {

    private JdbcTemplate jdbcTemplate;
    private Saver saver;
    private Updater updater;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        saver = new Saver(jdbcTemplate);
        updater = new Updater(jdbcTemplate);
    }

    @Override
    public Column saveOrUpdate(Column item) {
        if (item.getId() == null)
            saver.save(item);
        else
            updater.update(item);

        return item;
    }

    @Override
    public Table saveOrUpdate(Table item) {
        if (item.getId() == null)
            saver.save(item);
        else
            updater.update(item);

        return item;
    }

    @Override
    public Schema saveOrUpdate(Schema item) {
        if (item.getId() == null)
            saver.save(item);
        else
            updater.update(item);

        return item;
    }

    @Override
    public Model saveOrUpdate(Model item) {
        if (item.getId() == null)
            saver.save(item);
        else
            updater.update(item);

        return item;
    }

    @Override
    public Item findById(ItemType type, Long id) {
        return null;
    }

    @Override
    public Column findColumnById(Long id) {
        return null;
    }

    @Override
    public Table findTableById(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(List<Model> models) {

    }

    @Override
    public Collection<Item> findChildren(Item item) {
        return null;
    }
}
