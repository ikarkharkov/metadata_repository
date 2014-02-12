package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;

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

    public Schema toEntity() {
        Schema schema = new Schema();
        schema.setId(getId());
        schema.setName(getName());
        List<Table> tables = new ArrayList<Table>();
        for (TableBean tableBean : getTables()) {
            tables.add(tableBean.toEntity());
        }
        schema.setTables(tables);
        return schema;
    }
}
