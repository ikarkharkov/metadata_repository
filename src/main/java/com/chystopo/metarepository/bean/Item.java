package com.chystopo.metarepository.bean;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Item implements HasId {
    private Long id;
    private String name;
    private String context;
    private Item parent;
    private Long parentId;
    private Long modelId;
    private Long schemaId;
    private Long tableId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
        if (parent != null) {
            parentId = parent.getId();
        }
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;
        return new EqualsBuilder().append(parentId, item.parentId)
                .append(id, item.id)
                .append(context, item.context)
                .append(modelId, item.modelId)
                .append(schemaId, item.schemaId)
                .append(tableId, item.tableId)
                .append(name, item.name).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(name)
                .append(id)
                .append(context)
                .append(parentId)
                .append(modelId)
                .append(schemaId)
                .append(tableId)
                .toHashCode();
    }
}
