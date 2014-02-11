package com.chystopo.metarepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:repository-config.xml", "classpath:test-repository-config.xml"})

public class LoadTest {
    @Autowired
    private Repository repository;

    @Test
    public void loadSimpleFile() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("simple.xml");
        repository.load(stream);
    }
}
