package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Connection;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.parser.BeanVisitor;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlType(name = "connection")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionBean extends ItemBean {

    @XmlAttribute
    private String type;

    @XmlElement(name = "schema")
    private List<SchemaBean> schemas;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SchemaBean> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<SchemaBean> schemas) {
        this.schemas = schemas;
    }

    public Connection toEntity(BeanVisitor visitor, Item parent) {
        Connection connection = visitor.visitConnection(this, parent);

        return connection;
    }
}
