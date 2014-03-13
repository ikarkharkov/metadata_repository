package com.chystopo.metarepository.connection;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by oleksiy on 13/03/2014.
 */
public class PathResolverTest {
    private PathResolver<Integer> resolver;

    @Before
    public void setUp() {
        resolver = new PathResolver<Integer>();
    }

    @Test
    public void isSameExists() {
        resolver.add(Arrays.asList(1, 2, 3));
        assertTrue(resolver.contains(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void subList() {
        resolver.add(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));

        assertTrue(resolver.contains(Arrays.asList(1, 2)));
        assertTrue(resolver.contains(Arrays.asList(2, 3)));
        assertTrue(resolver.contains(Arrays.asList(4, 5, 6, 7)));
        assertTrue(resolver.contains(Arrays.asList(4, 5, 6, 7, 8, 9, 10)));

        assertFalse(resolver.contains(Arrays.asList(4, 5, 6, 7, 8, 9, 10, 11)));
        assertFalse(resolver.contains(Arrays.asList(1, 2, 3, 4, 6)));
        assertFalse(resolver.contains(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void addMoreLongLists() {
        testIntersection(Arrays.asList(1, 2, 3), Arrays.asList(2, 3));
        testIntersection(Arrays.asList(2, 3), Arrays.asList(1, 2, 3));
        testIntersection(Arrays.asList(2, 3, 4), Arrays.asList(2, 3, 4, 5, 6));
    }

    private void testIntersection(List<Integer> list, List<Integer> longerList) {
        PathResolver<Integer> res = new PathResolver<Integer>();
        res.add(list);
        res.add(longerList);
        assertEquals(1, res.getRepo().size());
    }
}
