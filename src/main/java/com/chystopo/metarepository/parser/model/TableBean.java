package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Column;
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

    public Table toEntity() {
        Table table = new Table();
        table.setId(getId());
        table.setName(getName());
        List<Column> columns = new ArrayList<Column>();
        for (ColumnBean columnBean : getColumns()) {
            columns.add(columnBean.toEntity());
        }
        table.setColumns(columns);
        return table;

    }
}