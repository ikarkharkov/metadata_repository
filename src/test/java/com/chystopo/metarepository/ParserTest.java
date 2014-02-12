package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import com.chystopo.metarepository.parser.Repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})
public class ParserTest {
    @Autowired
    private IParser parser;

    @Test
    public void loadOneTableFile() {
        Repo repo = parseFile("data/onetable.xml");

        assertEquals(1, repo.getModels().size());

        Model model = repo.getModels().get(0);
        assertEquals("test", model.getName());
        assertEquals("database", model.getType());
        assertEquals(1, model.getSchemas().size());

        Schema schema = model.getSchemas().get(0);
        assertEquals(2L, schema.getId().longValue());
        assertEquals("schema", schema.getName());

        Table table = schema.getTables().get(0);
        assertEquals(3L, table.getId().longValue());
        assertEquals("table", table.getName());

        Column column = table.getColumns().get(0);
        assertEquals(4L, column.getId().longValue());
        assertEquals("column", column.getName());
    }

    @Test
    public void parseSimpleFile() {
        Repo repo = parseFile("data/simple.xml");

        assertEquals(2, repo.getModels().size());
    }

    private Repo parseFile(String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        return parser.parse(stream);
    }
}
