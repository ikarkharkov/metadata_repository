package com.chystopo.metarepository.bean;

import java.util.ArrayList;
import java.util.List;

public class Model extends Item implements Branch {
    private String type;
    private List<Schema> schemas = new ArrayList<Schema>();

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public List<Schema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<Schema> schemas) {
        this.schemas = schemas;
    }

    @Override
    public List<? extends Item> getChildren() {
        return getSchemas();
    }
}
