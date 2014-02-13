package com.chystopo.metarepository.parser.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "source")
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceBean {
    @XmlAttribute
    private Long idref;

    public Long getIdref() {
        return idref;
    }

    public void setIdref(Long idref) {
        this.idref = idref;
    }
}
