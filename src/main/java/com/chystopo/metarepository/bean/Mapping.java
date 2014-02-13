package com.chystopo.metarepository.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleksiy on 13/02/14.
 */
public class Mapping extends Item {
    List<Connection> connections = new ArrayList<Connection>();

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
