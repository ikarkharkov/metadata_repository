package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class PerformanceTest {
    private static final Logger LOG = LoggerFactory.getLogger(PerformanceTest.class);
    public static final int TABLE_COUNT = 1000;
    public static final int SCHEMA_COUNT = 20;
    public static final int COLUMN_COUNT = 100;
    public static final int MODEL_COUNT = 5;
    @Autowired
    private IStorage storage;

    @Test
    @Ignore
    public void createHierarchy() {
        for (int modelCount = 0; modelCount < MODEL_COUNT; modelCount++) {
            Model model = new Model();
            model.setName("model_" + modelCount);
            model.setType("database");
            storage.saveOrUpdate(model);
            LOG.debug("model {}", modelCount);
            for (int schemeCount = 0; schemeCount < SCHEMA_COUNT; schemeCount++) {
                Schema schema = new Schema();
                schema.setParent(model);
                schema.setName("schema_" + modelCount * MODEL_COUNT + schemeCount);
                storage.saveOrUpdate(schema);
                LOG.debug("scheme {}", schemeCount);
                for (int tableCount = 0; tableCount < TABLE_COUNT; tableCount++) {
                    Table table = new Table();
                    table.setName("table_" + modelCount * MODEL_COUNT * SCHEMA_COUNT + schemeCount * SCHEMA_COUNT + tableCount);
                    table.setParent(schema);
                    storage.saveOrUpdate(table);
                    LOG.debug("table {}", tableCount);
                    for (int columnCount = 0; columnCount < COLUMN_COUNT; columnCount++) {
                        Column column = new Column();
                        column.setName("column_" + modelCount * MODEL_COUNT * SCHEMA_COUNT * TABLE_COUNT + schemeCount * SCHEMA_COUNT * TABLE_COUNT + tableCount * TABLE_COUNT + columnCount);
                        column.setParent(table);
                        storage.saveOrUpdate(column);
                    }
                }
            }
        }
    }
}
