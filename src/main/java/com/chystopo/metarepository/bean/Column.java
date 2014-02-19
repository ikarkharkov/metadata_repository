package com.chystopo.metarepository.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        if (!super.equals(o)) return false;

        Column column = (Column) o;
        return new EqualsBuilder()
                .append(formula, column.formula)
                .append(type, column.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(type).append(formula).toHashCode();
    }
}
