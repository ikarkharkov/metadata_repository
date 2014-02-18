package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.*;

import java.util.Collection;
import java.util.List;

public interface IStorage {
    Column saveOrUpdate(Column item);

    Table saveOrUpdate(Table item);

    Schema saveOrUpdate(Schema item);

    Model saveOrUpdate(Model item);

    Item findById(ItemType type, Long publicId);

    Column findColumnById(Long id);

    Table findTableById(Long id);

    void saveOrUpdate(List<Model> models);

    Collection<Item> findChildren(Item item);

    Schema findSchemaById(Long id);

    Model findModelById(Long id);
}
