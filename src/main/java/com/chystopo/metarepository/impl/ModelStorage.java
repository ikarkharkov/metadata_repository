package com.chystopo.metarepository.impl;

import com.chystopo.metarepository.IModelStorage;
import com.chystopo.metarepository.bean.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelStorage implements IModelStorage {
    @Override
    public void saveOrUpdate(Item item) {

    }

    @Override
    public Item findById(Long id) {
        return null;
    }

    @Override
    public void saveOrUpdate(List<? extends Item> items) {

    }
}
