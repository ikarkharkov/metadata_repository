package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Connection;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Schema;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
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

    public Connection toEntity(Item parent) {
        Connection connection = new Connection();
        connection.setPublicId(getId());
        connection.setName(getName());
        connection.setType(getType());
        connection.setParent(parent);
        List<Schema> schemas = new ArrayList<Schema>();
        for (SchemaBean schemaBean : getSchemas()) {
            schemas.add(schemaBean.toEntity(connection));
        }
        connection.setSchemas(schemas);
        return connection;
    }
}
