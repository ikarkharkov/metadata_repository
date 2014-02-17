package com.chystopo.metarepository.storage.mapper;

import com.chystopo.metarepository.bean.Table;

public class TableMapper extends BasicModelMapper<Table> {
    @Override
    protected Table createItem() {
        return new Table();
    }
}
