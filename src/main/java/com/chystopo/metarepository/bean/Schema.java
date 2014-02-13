package com.chystopo.metarepository.bean;

import java.util.ArrayList;
import java.util.List;

public class Schema extends Item implements Branch {
    private List<Table> tables = new ArrayList<Table>();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public List<? extends Item> getChildren() {
        return getTables();
    }
}
