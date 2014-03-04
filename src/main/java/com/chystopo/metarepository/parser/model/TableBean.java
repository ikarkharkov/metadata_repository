package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.parser.BeanVisitor;

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
    public Table toEntity(BeanVisitor visitor, Item parent) {
        Table table = visitor.visitTable(this, parent);

        return table;

    }
}
