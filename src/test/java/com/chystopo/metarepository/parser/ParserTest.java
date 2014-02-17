package com.chystopo.metarepository.parser;

import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.bean.*;
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
        assertEquals(2L, schema.getPublicId().longValue());
        assertEquals("schema", schema.getName());

        Table table = schema.getTables().get(0);
        assertEquals(3L, table.getPublicId().longValue());
        assertEquals("table", table.getName());

        Column column = table.getColumns().get(0);
        assertEquals(4L, column.getPublicId().longValue());
        assertEquals("column", column.getName());
    }

    @Test
    public void parseSimpleFile() {
        Repo repo = parseFile("data/simple.xml");

        assertEquals(2, repo.getModels().size());
        assertEquals(1, repo.getMappings().size());

        Mapping mapping = repo.getMappings().get(0);

        Connection source = mapping.getConnections().get(0);
        assertEquals("source", source.getType());

        Schema sourceSchema = source.getSchemas().get(0);
        Table sourceTable = sourceSchema.getTables().get(0);
        Column sourceColumn = sourceTable.getColumns().get(0);
        assertEquals(1111L, sourceColumn.getPublicId().longValue());

        Connection target = mapping.getConnections().get(1);
        assertEquals("target", target.getType());

        Schema targetSchema = target.getSchemas().get(0);
        Table targetTable = targetSchema.getTables().get(0);
        Column targetColumn = targetTable.getColumns().get(0);
        assertEquals(2111L, targetColumn.getPublicId().longValue());
        assertEquals("c+d", targetColumn.getFormula());
    }

    private Repo parseFile(String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        return parser.parse(stream);
    }
}
