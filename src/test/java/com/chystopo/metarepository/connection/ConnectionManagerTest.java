package com.chystopo.metarepository.connection;

import com.chystopo.metarepository.IConnectionManager;
import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.*;
import com.chystopo.metarepository.parser.Repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})
public class ConnectionManagerTest {
    @Autowired
    private IConnectionManager connectionManager;

    @Autowired
    private IStorage storage;

    @Autowired
    private IParser parser;

    @Test
    public void testSimpleLinage() {
        String context = generateContext();
        Repo repo = parseFile(context, "data/simple.xml");
        for (Model model : repo.getModels()) {
            storage.saveOrUpdate(model);
        }

        connectionManager.save(repo.getMappings());
        Column column = storage.findColumnByPathAndContext("DW.s1.t1.c1", context);
        Collection<Column> linage = connectionManager.findSources(column);
        assertEquals(2, linage.size());
        for (Item item : linage) {
            assertThat(item.getName(), anyOf(equalTo("c"), equalTo("e")));
        }
    }

    @Test
    public void testLayeredLinage() {
        String context = generateContext();
        Repo repo = parseFile(context, "data/twoLayerPath.xml");
        for (Model model : repo.getModels()) {
            storage.saveOrUpdate(model);
        }
        connectionManager.save(repo.getMappings());

        checkColumnLinage(context);

        checkTableLinage(context);

        checkModelLinage(context);
    }

    private void checkModelLinage(String context) {
        Schema schema = storage.findSchemaByPathAndContext("DW_LOAD.s2", context);
        Collection<Column> linage = connectionManager.findSources(schema);
        assertEquals(3, linage.size());
        for (Item item : linage) {
            assertThat(item.getName(), anyOf(equalTo("c1"), equalTo("c"), equalTo("e")));
        }
    }

    private void checkTableLinage(String context) {
        Table table = storage.findTableByPathAndContext("DW_LOAD.s2.t1", context);
        Collection<Column> linage = connectionManager.findSources(table);
        assertEquals(3, linage.size());
        for (Item item : linage) {
            assertThat(item.getName(), anyOf(equalTo("c1"), equalTo("c"), equalTo("e")));
        }
    }

    private void checkColumnLinage(String context) {
        Column column = storage.findColumnByPathAndContext("DW_LOAD.s2.t1.c1", context);
        Collection<Column> linage = connectionManager.findSources(column);
        assertEquals(3, linage.size());
        for (Item item : linage) {
            assertThat(item.getName(), anyOf(equalTo("c1"), equalTo("c"), equalTo("e")));
        }
    }

    private String generateContext() {
        return "testConnectionContext" + System.currentTimeMillis();
    }

    private Repo parseFile(String context, String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        return parser.parse(context, stream);
    }
}