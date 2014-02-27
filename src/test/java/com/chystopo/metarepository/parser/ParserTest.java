package com.chystopo.metarepository.parser;

import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.bean.*;
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
public class ParserTest {
    @Autowired
    private IParser parser;

    @Test
    public void loadOneTableFile() {
        Repo repo = parseFile("testParserContext", "data/onetable.xml");

        assertEquals(1, repo.getModels().size());

        Model model = repo.getModels().get(0);
        assertEquals("test", model.getName());
        assertEquals("database", model.getType());
        assertEquals(1, model.getSchemas().size());

        Schema schema = model.getSchemas().get(0);
        assertEquals("schema", schema.getName());

        Table table = schema.getTables().get(0);
        assertEquals("table", table.getName());

        Column column = table.getColumns().get(0);
        assertEquals("column", column.getName());
    }

    @Test
    public void parseSimpleFile() {
        Repo repo = parseFile("testParserContext", "data/simple.xml");

        assertEquals(2, repo.getModels().size());
        assertEquals(1, repo.getMappings().size());

        Mapping mapping = repo.getMappings().get(0);

        Connection source = mapping.getConnections().get(0);
        assertEquals("source", source.getType());

        Schema sourceSchema = source.getSchemas().get(0);
        Table sourceTable = sourceSchema.getTables().get(0);
        Column sourceColumn = sourceTable.getColumns().get(0);

        Connection target = mapping.getConnections().get(1);
        assertEquals("target", target.getType());

        Schema targetSchema = target.getSchemas().get(0);
        Table targetTable = targetSchema.getTables().get(0);
        Column targetColumn = targetTable.getColumns().get(0);
        assertEquals("c+d", targetColumn.getFormula());
        List<Long> sources = targetColumn.getSources();
        assertEquals(2, sources.size());
        for (Long sourceRef : sources) {
            assertThat(sourceRef, anyOf(equalTo(1111L), equalTo(1121L)));
        }
    }

    private Repo parseFile(String context, String fileName) {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName);
        return parser.parse(context, stream);
    }
}
