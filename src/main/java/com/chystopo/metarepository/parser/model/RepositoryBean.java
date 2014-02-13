package com.chystopo.metarepository.parser.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "repo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RepositoryBean {

    @XmlElement(name = "model")
    private List<ModelBean> models = new ArrayList<ModelBean>();

    @XmlElement(name = "mapping")
    private List<MappingBean> mappings = new ArrayList<MappingBean>();

    public List<ModelBean> getModels() {
        return models;
    }

    public void setModels(List<ModelBean> models) {
        this.models = models;
    }

    public List<MappingBean> getMappings() {
        return mappings;
    }

    public void setMappings(List<MappingBean> mappings) {
        this.mappings = mappings;
    }
}
