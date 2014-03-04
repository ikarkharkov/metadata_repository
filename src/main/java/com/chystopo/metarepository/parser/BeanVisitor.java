package com.chystopo.metarepository.parser;

import com.chystopo.metarepository.bean.*;
import com.chystopo.metarepository.parser.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BeanVisitor {
    private final Collection<ModelBean> models;
    private String context;

    public BeanVisitor(String context, Collection<ModelBean> models) {
        this.context = context;
        this.models = models;
    }

    public Column visitColumn(ColumnBean bean, Item parent) {
        Column column = new Column();
        column.setContext(context);
        column.setParent(parent);
        column.setName(bean.getName());
        column.setType(bean.getType());
        column.setFormula(bean.getFormula());
        List<String> sources = new ArrayList<String>();

        for (SourceBean sourceBean : bean.getSources()) {
            String source = findSource(sourceBean.getIdref());
            sources.add(source);
        }
        column.setSources(sources);
        return column;
    }

    private String findSource(Long idref) {
        for (ModelBean model : models) {
            for (SchemaBean schemaBean : model.getSchemes()) {
                for (TableBean table : schemaBean.getTables()) {
                    for (ColumnBean column : table.getColumns()) {
                        if (column.getId().equals(idref)) {
                            return model.getName()
                                    + "." + schemaBean.getName()
                                    + "." + table.getName()
                                    + "." + column.getName();
                        }
                    }
                }
            }
        }
        return null;
    }

    public Mapping visitMapping(MappingBean mappingBean) {
        Mapping result = new Mapping();
        result.setContext(context);
        result.setName(mappingBean.getName());

        List<Connection> connections = new ArrayList<Connection>();
        for (ConnectionBean connectionBean : mappingBean.getConnections()) {
            connections.add(connectionBean.toEntity(this, result));
        }
        result.setConnections(connections);
        return result;
    }

    public Connection visitConnection(ConnectionBean bean, Item parent) {
        Connection connection = new Connection();
        connection.setContext(context);
        connection.setName(bean.getName());
        connection.setType(bean.getType());
        connection.setParent(parent);
        List<Schema> schemas = new ArrayList<Schema>();
        for (SchemaBean schemaBean : bean.getSchemas()) {
            schemas.add(schemaBean.toEntity(this, connection));
        }
        connection.setSchemas(schemas);
        return connection;
    }

    public Schema visitSchema(SchemaBean bean, Item parent) {
        Schema schema = new Schema();
        schema.setContext(context);
        schema.setName(bean.getName());
        schema.setParent(parent);
        List<Table> tables = new ArrayList<Table>();
        for (TableBean tableBean : bean.getTables()) {
            tables.add(tableBean.toEntity(this, schema));
        }
        schema.setTables(tables);
        return schema;
    }

    public Table visitTable(TableBean bean, Item parent) {
        Table table = new Table();
        table.setContext(context);
        table.setParent(parent);
        table.setName(bean.getName());
        List<Column> columns = new ArrayList<Column>();
        for (ColumnBean columnBean : bean.getColumns()) {
            columns.add(columnBean.toEntity(this, table));
        }
        table.setColumns(columns);
        return table;
    }

    public Model visitModel(ModelBean bean, Item parent) {
        Model model = new Model();
        model.setContext(context);
        model.setName(bean.getName());
        model.setType(bean.getType());
        List<Schema> schemas = new ArrayList<Schema>();
        for (SchemaBean schemaBean : bean.getSchemes()) {
            schemas.add(schemaBean.toEntity(this, model));
        }
        model.setSchemas(schemas);
        return model;
    }
}
