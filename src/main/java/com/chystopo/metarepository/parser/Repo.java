package com.chystopo.metarepository.parser;

import com.chystopo.metarepository.bean.Mapping;
import com.chystopo.metarepository.bean.Model;

import java.util.List;

public class Repo {
    private List<Model> models;
    private List<Mapping> mappings;

    public Repo(List<Model> models, List<Mapping> mappings) {
        this.models = models;
        this.mappings = mappings;
    }

    public List<Model> getModels() {
        return models;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
