package com.chystopo.metarepository.storage;


import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class StorageTest {
    @Autowired
    private IStorage storage;

    private Table prepareTable(String name, String context) {
        Table table = new Table();
        table.setName(name);
        table.setContext(context);
        return table;
    }

    private Column prepareColumn(Table parentTable, String name, String type, String context) {
        Column column = new Column();
        column.setName(name);
        column.setType(type);
        column.setContext(context);
        column.setParent(parentTable);
        return column;
    }

    @Test
    public void saveModelWithSchema() {
        Model model = new Model();
        String context = "unit_test_" + System.currentTimeMillis();
        model.setContext(context);
        String modelName = "testDB";
        model.setName(modelName);
        String modelType = "database";
        model.setType(modelType);
        String schemaName = "modelSchema";
        Schema schema = prepareSchema(schemaName, context);
        schema.setContext(context);
        model.getSchemas().add(schema);
        String tableName = "modelTable1";
        Table table = prepareTable(tableName, context);
        schema.getTables().add(table);
        String columnName1 = "modelTest1";
        table.getColumns().add(prepareColumn(table, columnName1, "string", context));
        table.getColumns().add(prepareColumn(table, "modelTest2", "int", context));
        table.getColumns().add(prepareColumn(table, "modelTest3", "blob", context));

        Model savedModel = storage.saveOrUpdate(model);

        assertNotNull(savedModel.getId());
        assertChildrenHasCorrectReferenceToParent(savedModel.getId(), savedModel);

        Model foundModel = storage.findModelById(savedModel.getId());
        assertNotNull(foundModel);
        assertEquals(modelName, foundModel.getName());
        assertEquals(modelType, foundModel.getType());

        Column byPath = storage.findColumnByPathAndContext(modelName
                + "."
                + schemaName
                + "."
                + tableName
                + "."
                + columnName1, context);
        assertNotNull(byPath);
        assertEquals(columnName1, byPath.getName());
        assertEquals(context, byPath.getContext());

        Collection<Schema> schemas = (Collection<Schema>) storage.findChildren(foundModel);
        assertEquals(1, schemas.size());
    }

    private void assertChildrenHasCorrectReferenceToParent(Long parentId, Branch savedModel) {
        for (Item child : savedModel.getChildren()) {
            assertEquals(parentId, child.getParent().getId());
            if (child instanceof Branch) {
                assertChildrenHasCorrectReferenceToParent(child.getId(), ((Branch) child));
            }
        }
    }

    private Schema prepareSchema(String name, String context) {
        Schema schema = new Schema();
        schema.setName(name);
        schema.setContext(context);
        return schema;
    }
}