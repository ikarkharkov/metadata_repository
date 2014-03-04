package com.chystopo.metarepository.parser.model;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.parser.BeanVisitor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlType(name = "column")
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnBean extends ItemBean {

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String formula;

    @XmlElement(name = "source")
    private List<SourceBean> sources = new ArrayList<SourceBean>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<SourceBean> getSources() {
        return sources;
    }

    public void setSources(List<SourceBean> sources) {
        this.sources = sources;
    }

    @Override
    public Column toEntity(BeanVisitor visitor, Item parent) {
        return visitor.visitColumn(this, parent);
    }

    public String getPath() {
        return getName();
    }
}
