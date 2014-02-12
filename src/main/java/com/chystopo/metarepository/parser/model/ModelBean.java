package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.bean.Schema;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "model")
@XmlAccessorType(XmlAccessType.FIELD)
public class ModelBean extends ItemBean {

    @XmlAttribute
    private String type;

    @XmlElement(name = "schema")
    private List<SchemaBean> schemes = new ArrayList<SchemaBean>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SchemaBean> getSchemes() {
        return schemes;
    }

    public void setSchemes(List<SchemaBean> schemes) {
        this.schemes = schemes;
    }

    public Model toEntity() {
        Model model = new Model();
        model.setName(getName());
        model.setId(getId());
        model.setType(getType());
        List<Schema> schemas = new ArrayList<Schema>();
        for (SchemaBean schemaBean : getSchemes()) {
            schemas.add(schemaBean.toEntity());
        }
        model.setSchemas(schemas);
        return model;
    }
}
