package com.chystopo.metarepository.parser.model;


import com.chystopo.metarepository.bean.Connection;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Mapping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "mapping")
@XmlAccessorType(XmlAccessType.FIELD)
public class MappingBean extends ItemBean {
    @XmlElement(name = "connection")
    private List<ConnectionBean> connections;

    public List<ConnectionBean> getConnections() {
        return connections;
    }

    public void setConnections(List<ConnectionBean> connections) {
        this.connections = connections;
    }

    @Override
    public Mapping toEntity(String context, Item parent) {
        Mapping result = new Mapping();
        result.setContext(context);
        result.setName(getName());

        List<Connection> connections = new ArrayList<Connection>();
        for (ConnectionBean connectionBean : getConnections()) {
            connections.add(connectionBean.toEntity(context, result));
        }
        result.setConnections(connections);

        return result;
    }
}
