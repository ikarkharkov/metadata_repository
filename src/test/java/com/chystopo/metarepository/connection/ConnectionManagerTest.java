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

import static org.hamcrest.CoreMatchers.*;
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
        Column column = storage.findColumnByPublicIdAndContext(2111L, context);
        Collection<? extends Item> linage = connectionManager.findSources(column);
        assertEquals(2, linage.size());
        for (Item item : linage) {
            assertThat(item.getPublicId(), anyOf(equalTo(1111L), equalTo(1121L)));
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
        Schema schema = storage.findSchemaByPublicIdAndContext(31L, context);
        Collection<? extends Item> linage = connectionManager.findSources(schema);
        assertEquals(2, linage.size());
        for (Item item : linage) {
            assertThat(item, is(instanceOf(Schema.class)));
            assertThat(item.getPublicId(), anyOf(equalTo(11L), equalTo(21L)));
        }
    }

    private void checkTableLinage(String context) {
        Table table = storage.findTableByPublicIdAndContext(311L, context);
        Collection<? extends Item> linage = connectionManager.findSources(table);
        assertEquals(3, linage.size());
        for (Item item : linage) {
            assertThat(item, is(instanceOf(Table.class)));
            assertThat(item.getPublicId(), anyOf(equalTo(111L), equalTo(112L), equalTo(211L)));
        }
    }

    private void checkColumnLinage(String context) {
        Column column = storage.findColumnByPublicIdAndContext(3111L, context);
        Collection<? extends Item> linage = connectionManager.findSources(column);
        assertEquals(3, linage.size());
        for (Item item : linage) {
            assertThat(item, is(instanceOf(Column.class)));
            assertThat(item.getPublicId(), anyOf(equalTo(1111L), equalTo(1121L), equalTo(2111L)));
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