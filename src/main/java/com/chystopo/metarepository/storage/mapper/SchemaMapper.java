package com.chystopo.metarepository.storage.mapper;

import com.chystopo.metarepository.bean.Schema;

public class SchemaMapper extends BasicModelMapper<Schema> {
    @Override
    protected Schema createItem() {
        return new Schema();
    }
}
