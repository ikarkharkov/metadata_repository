package com.chystopo.metarepository.bean;

import java.util.ArrayList;
import java.util.List;

public class Schema extends Item {
    private List<Table> tables = new ArrayList<Table>();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
