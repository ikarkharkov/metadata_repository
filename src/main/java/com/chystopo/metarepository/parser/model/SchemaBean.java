package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.parser.BeanVisitor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "schema")
@XmlAccessorType(XmlAccessType.FIELD)
public class SchemaBean extends ItemBean {

    @XmlElement(name = "table")
    private List<TableBean> tables = new ArrayList<TableBean>();

    public List<TableBean> getTables() {
        return tables;
    }

    public void setTables(List<TableBean> tables) {
        this.tables = tables;
    }

    @Override
    public Schema toEntity(BeanVisitor visitor, Item parent) {
        Schema schema = visitor.visitSchema(this, parent);

        return schema;
    }
}
