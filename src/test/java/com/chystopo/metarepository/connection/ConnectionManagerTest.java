package com.chystopo.metarepository.connection;

import com.chystopo.metarepository.IConnectionManager;
import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.parser.Repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.util.List;

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

        List<? extends Item> linage = connectionManager.findColumnLinageByPublicId(context, 2111L);
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

        List<? extends Item> linage = connectionManager.findColumnLinageByPublicId(context, 3111L);
        assertEquals(3, linage.size());
        for (Item item : linage) {
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
