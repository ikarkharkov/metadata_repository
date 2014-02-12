package com.chystopo.metarepository.parser;

import com.chystopo.metarepository.bean.Connection;
import com.chystopo.metarepository.bean.Model;

import java.util.List;

public class Repo {
    private List<Model> models;
    private List<Connection> connections;

    public Repo(List<Model> models, List<Connection> connections) {
        this.models = models;
        this.connections = connections;
    }

    public List<Model> getModels() {
        return models;
    }

    public List<Connection> getConnections() {
        return connections;
    }
}
