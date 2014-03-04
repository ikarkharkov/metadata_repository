package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.*;

import java.util.Collection;
import java.util.List;

public interface IStorage {
    Column saveOrUpdate(Column item);

    Table saveOrUpdate(Table item);

    Schema saveOrUpdate(Schema item);

    Model saveOrUpdate(Model item);

    Column findColumnById(Long id);

    Model findModelByPathAndContext(String path, String context);

    Table findTableById(Long id);

    void saveOrUpdate(List<Model> models);

    Collection<? extends Item> findChildren(Item item);

    Schema findSchemaById(Long id);

    Model findModelById(Long id);

    Collection<Item> findParents(Item item);

    Column findColumnByPathAndContext(String path, String context);

    Table findTableByPathAndContext(String path, String context);

    Schema findSchemaByPathAndContext(String path, String context);
}
