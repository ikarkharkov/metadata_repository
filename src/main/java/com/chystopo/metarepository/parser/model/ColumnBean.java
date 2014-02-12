package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Column;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnBean extends ItemBean {

    @XmlAttribute
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Column toEntity() {
        Column column = new Column();
        column.setId(getId());
        column.setName(getName());
        column.setType(getType());

        return column;
    }
}
