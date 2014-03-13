package com.chystopo.metarepository.connection;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PathBuilderTest {
    private PathBuilder<String> builder;

    @Before
    public void setUp() {
        builder = new PathBuilder<String>();
    }

    @Test
    public void oneLayer() {
        builder.add("a1", "b1");
        builder.add("a1", "b2");
        List<List<String>> paths = builder.generatePaths();
        assertEquals(2, paths.size());
        for (List<String> path : paths) {
            assertThat(path, anyOf(
                    equalTo(Arrays.asList("a1", "b1")),
                    equalTo(Arrays.asList("a1", "b2"))));
        }
    }

    @Test
    public void oneLayerElements() {
        builder.add("a1", "b1");
        builder.add("a1", "b2");
        builder.add("a1", "b3");
        builder.add("a2", "b2");
        builder.add("a2", "b3");
        List<List<String>> paths = builder.generatePaths();
        assertEquals(5, paths.size());
        for (List<String> path : paths) {
            assertThat(path, anyOf(
                    equalTo(Arrays.asList("a1", "b1")),
                    equalTo(Arrays.asList("a1", "b2")),
                    equalTo(Arrays.asList("a1", "b3")),
                    equalTo(Arrays.asList("a2", "b2")),
                    equalTo(Arrays.asList("a2", "b3"))
            ));
        }
    }

    @Test
    public void twoLayerSmall() {
        builder.add("a1", "b1");
        builder.add("b1", "c1");
        builder.add("b1", "c2");
        List<List<String>> paths = builder.generatePaths();
        assertEquals(2, paths.size());
        assertThat(paths.get(0), anyOf(
                equalTo(Arrays.asList("a1", "b1", "c1")),
                equalTo(Arrays.asList("a1", "b1", "c2"))
        ));
    }

    @Test
    public void twoLayerSmall2() {
        builder.add("a1", "b1");
        builder.add("b1", "c1");
        builder.add("b1", "c2");
        builder.add("a1", "b2");
        builder.add("b3", "c2");
        List<List<String>> paths = builder.generatePaths();
        assertEquals(4, paths.size());
        assertThat(paths.get(0), anyOf(
                equalTo(Arrays.asList("a1", "b1", "c1")),
                equalTo(Arrays.asList("a1", "b1", "c2")),
                equalTo(Arrays.asList("a1", "b2")),
                equalTo(Arrays.asList("b3", "c2"))
        ));
    }

    @Test
    public void largeSet() {
        builder.add("a1", "c1");
        builder.add("c1", "d1");
        builder.add("d1", "e2");
        builder.add("a1", "b1");
        builder.add("b1", "c1");
        builder.add("b1", "c2");
        builder.add("c2", "d2");
        builder.add("d2", "e3");
        builder.add("e3", "f2");
        builder.add("e3", "f3");
        builder.add("b3", "c2");
        builder.add("b3", "c3");
        builder.add("c3", "d3");
        builder.add("c3", "d4");

        List<List<String>> paths = builder.generatePaths();
        assertEquals(8, paths.size());
        for (List<String> path : paths) {
            assertThat(path, anyOf(
                    equalTo(Arrays.asList("a1", "b1", "c1", "d1", "e2")),
                    equalTo(Arrays.asList("a1", "c1", "d1", "e2")),
                    equalTo(Arrays.asList("a1", "b1", "c2", "d2", "e3", "f2")),
                    equalTo(Arrays.asList("a1", "b1", "c2", "d2", "e3", "f3")),
                    equalTo(Arrays.asList("b3", "c2", "d2", "e3", "f2")),
                    equalTo(Arrays.asList("b3", "c2", "d2", "e3", "f3")),
                    equalTo(Arrays.asList("b3", "c3", "d3")),
                    equalTo(Arrays.asList("b3", "c3", "d4"))

            ));
        }
    }
}
