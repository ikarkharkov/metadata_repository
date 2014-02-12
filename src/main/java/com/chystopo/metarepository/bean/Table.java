package com.chystopo.metarepository.bean;

import java.util.ArrayList;
import java.util.List;

public class Table extends Item {
    private List<Column> columns = new ArrayList<Column>();

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
