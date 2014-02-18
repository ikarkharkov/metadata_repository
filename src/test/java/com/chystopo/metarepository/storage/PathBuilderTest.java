package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Model;
import com.chystopo.metarepository.bean.Schema;
import com.chystopo.metarepository.bean.Table;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by oleksiy on 18/02/14.
 */
public class PathBuilderTest {
    @Test
    public void generatePath() {
        Model model = new Model();
        model.setId(1L);
        Schema schema = new Schema();
        schema.setId(2L);
        schema.setParent(model);
        Table table = new Table();
        table.setId(3L);
        table.setParent(schema);
        Column column = new Column();
        column.setId(4L);
        column.setParent(table);

        PathBuilder builder = new PathBuilder();
        assertEquals("/1/2/3/", builder.build(column));
    }

}
