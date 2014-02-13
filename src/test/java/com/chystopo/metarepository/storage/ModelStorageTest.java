package com.chystopo.metarepository.storage;


import com.chystopo.metarepository.IModelStorage;
import com.chystopo.metarepository.bean.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})
public class ModelStorageTest {
    @Autowired
    private IModelStorage storage;

    @Test
    public void saveColumn() {
        Table table = prepareTable(1L, "columnTable");
        Column column = prepareColumn(table, "test", "string");
        Column savedColumn = storage.saveOrUpdate(column);

        assertNotNull(savedColumn.getId());
    }

    private Table prepareTable(Long id, String name) {
        Table table = new Table();
        table.setId(id);
        table.setName(name);
        return table;
    }

    private Column prepareColumn(Table parentTable, String name, String type) {
        Column column = new Column();
        column.setName(name);
        column.setType(type);

        column.setParent(parentTable);
        return column;
    }

    @Test
    public void saveTableWithColumns() {
        Table table = prepareTable(null, "table1");
        table.getColumns().add(prepareColumn(table, "tableTest1", "string"));
        table.getColumns().add(prepareColumn(table, "tableTest2", "int"));
        table.getColumns().add(prepareColumn(table, "tableTest3", "blob"));

        Table savedTable = storage.saveOrUpdate(table);

        assertNotNull(savedTable.getId());
        assertChildrenHasCorrectReferenceToParent(savedTable.getId(), savedTable);
    }

    @Test
    public void saveSchemaWithTables() {
        Schema schema = prepareSchema("testSchema");

        Table table = prepareTable(null, "schemaTable1");
        schema.getTables().add(table);
        table.getColumns().add(prepareColumn(table, "schemaTest1", "string"));
        table.getColumns().add(prepareColumn(table, "schemaTest2", "int"));
        table.getColumns().add(prepareColumn(table, "schemaTest3", "blob"));

        Schema savedSchema = storage.saveOrUpdate(schema);

        assertNotNull(savedSchema.getId());
        assertChildrenHasCorrectReferenceToParent(savedSchema.getId(), savedSchema);
    }

    @Test
    public void saveModelWithSchema() {
        Model model = new Model();
        model.setName("testDB");
        model.setType("database");
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
        return schema;
    }
}