package com.chystopo.metarepository.bean;

import java.util.List;

public class Column extends Item {
    private String type;
    private List<Long> sources;
    private String formula;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSources(List<Long> sources) {
        this.sources = sources;
    }

    public List<Long> getSources() {
        return sources;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
