package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Table;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "table")
@XmlAccessorType(XmlAccessType.FIELD)
public class TableBean extends ItemBean {

    @XmlElement(name = "column")
    private List<ColumnBean> columns = new ArrayList<ColumnBean>();

    public List<ColumnBean> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnBean> columns) {
        this.columns = columns;
    }

    @Override
    public Table toEntity(String context, Item parent) {
        Table table = new Table();
        table.setContext(context);
        table.setParent(parent);
        table.setName(getName());
        List<Column> columns = new ArrayList<Column>();
        for (ColumnBean columnBean : getColumns()) {
            columns.add(columnBean.toEntity(context, table));
        }
        table.setColumns(columns);
        return table;

    }
}
