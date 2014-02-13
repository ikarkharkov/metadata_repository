package com.chystopo.metarepository.bean;

import java.util.List;

public class Connection extends Item implements Branch {
    private List<Schema> schemas;
    private String type;

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public List<? extends Item> getChildren() {
        return getSchemas();
    }
}
