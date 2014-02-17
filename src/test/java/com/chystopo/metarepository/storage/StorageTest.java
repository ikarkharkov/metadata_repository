package com.chystopo.metarepository.storage;


import com.chystopo.metarepository.IModelStorage;
import com.chystopo.metarepository.bean.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class StorageTest {
    @Autowired
    private IModelStorage storage;

    @Test
    public void saveColumn() {
        Table table = prepareTable(1L, "columnTable");
        String columnName = "test";
        String columnType = "string";
        Column column = prepareColumn(table, columnName, columnType);
        Column savedColumn = storage.saveOrUpdate(column);

        assertNotNull(savedColumn.getId());

        Column foundColumn = storage.findColumnById(savedColumn.getId());
        assertNotNull(foundColumn);
        assertEquals(columnName, foundColumn.getName());
        assertEquals(columnType, foundColumn.getType());
    }

    private Table prepareTable(Long id, String name) {
        Table table = new Table();
        table.setPublicId(id);
        table.setName(name);
        table.setContext("unit_test");
        return table;
    }

    private Column prepareColumn(Table parentTable, String name, String type) {
        Column column = new Column();
        column.setName(name);
        column.setType(type);
        column.setContext("unit_test");
        column.setParent(parentTable);
        return column;
    }

    @Test
    public void saveTableWithColumns() {
        String tableName = "table1";
        Table table = prepareTable(null, tableName);
        table.getColumns().add(prepareColumn(table, "tableTest1", "string"));
        table.getColumns().add(prepareColumn(table, "tableTest2", "int"));
        table.getColumns().add(prepareColumn(table, "tableTest3", "blob"));

        Table savedTable = storage.saveOrUpdate(table);

        assertNotNull(savedTable.getId());
        assertChildrenHasCorrectReferenceToParent(savedTable.getId(), savedTable);

        Table foundTable = storage.findTableById(savedTable.getId());
        assertNotNull(foundTable);
        assertEquals(tableName, foundTable.getName());
    }

    @Test
    public void saveSchemaWithTables() {
        String schemaName = "testSchema";
        Schema schema = prepareSchema(schemaName);

        Table table = prepareTable(null, "schemaTable1");
        schema.getTables().add(table);
        table.getColumns().add(prepareColumn(table, "schemaTest1", "string"));
        table.getColumns().add(prepareColumn(table, "schemaTest2", "int"));
        table.getColumns().add(prepareColumn(table, "schemaTest3", "blob"));

        Schema savedSchema = storage.saveOrUpdate(schema);

        assertNotNull(savedSchema.getId());
        assertChildrenHasCorrectReferenceToParent(savedSchema.getId(), savedSchema);

        Schema foundSchema = storage.findSchemaById(savedSchema.getId());
        assertNotNull(foundSchema);
        assertEquals(schemaName, foundSchema.getName());
    }

    @Test
    public void saveModelWithSchema() {
        Model model = new Model();
        String modelName = "testDB";
        model.setName(modelName);
        String modelType = "database";
        model.setType(modelType);
        Schema schema = prepareSchema("modelSchema");
        model.getSchemas().add(schema);
        Table table = prepareTable(null, "modelTable1");
        schema.getTables().add(table);
        table.getColumns().add(prepareColumn(table, "modelTest1", "string"));
        table.getColumns().add(prepareColumn(table, "modelTest2", "int"));
        table.getColumns().add(prepareColumn(table, "modelTest3", "blob"));

        Model savedModel = storage.saveOrUpdate(model);

        assertNotNull(savedModel.getId());
        assertChildrenHasCorrectReferenceToParent(savedModel.getId(), savedModel);

        Model foundModel = storage.findModelById(savedModel.getId());
        assertNotNull(foundModel);
        assertEquals(modelName, foundModel.getName());
        assertEquals(modelType, foundModel.getType());
    }

    private void assertChildrenHasCorrectReferenceToParent(Long parentId, Branch savedModel) {
        for (Item child : savedModel.getChildren()) {
            assertEquals(parentId, child.getParent().getId());
            if (child instanceof Branch) {
                assertChildrenHasCorrectReferenceToParent(child.getId(), ((Branch) child));
            }
        }
    }

    private Schema prepareSchema(String name) {
        Schema schema = new Schema();
        schema.setName(name);
        schema.setContext("unit_test");
        return schema;
    }
}