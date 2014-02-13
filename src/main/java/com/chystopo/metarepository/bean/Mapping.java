package com.chystopo.metarepository.bean;

import java.util.ArrayList;
import java.util.List;

public class Mapping extends Item implements Branch {
    List<Connection> connections = new ArrayList<Connection>();

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    @Override
    public List<? extends Item> getChildren() {
        return getConnections();
    }
}
