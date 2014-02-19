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

    Model findModelByPublicIdAndContext(long publicId, String context);

    Table findTableById(Long id);

    void saveOrUpdate(List<Model> models);

    Collection<? extends Item> findChildren(Item item, boolean recursively);

    Collection<? extends Item> findChildren(Item item);

    Schema findSchemaById(Long id);

    Model findModelById(Long id);

    Collection<Item> findParents(Item item);

    Column findColumnByPublicIdAndContext(long publicId, String context);

    Table findTableByPublicIdAndContext(long publicId, String context);

    Schema findSchemaByPublicIdAndContext(long publicId, String context);
}
