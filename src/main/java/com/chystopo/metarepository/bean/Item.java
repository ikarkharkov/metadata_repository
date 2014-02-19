package com.chystopo.metarepository.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Item implements HasId {
    private Long id;
    private Long publicId;
    private String name;
    private String context;
    private Item parent;
    private long parentId;
    private String path;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setPublicId(Long publicId) {
        this.publicId = publicId;
    }

    public Long getPublicId() {
        return publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getParentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;
        return new EqualsBuilder().append(parentId, item.parentId)
                .append(id, item.id)
                .append(context, item.context)
                .append(name, item.name)
                .append(publicId, item.publicId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(publicId)
                .append(name)
                .append(id)
                .append(context)
                .append(parentId)
                .toHashCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
