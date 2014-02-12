package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Item;

import java.util.List;

public interface IModelStorage {
    void saveOrUpdate(Item item);

    Item findById(Long id);

    void saveOrUpdate(List<? extends Item> items);
}
