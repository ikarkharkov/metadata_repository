package com.chystopo.metarepository.impl;

import com.chystopo.metarepository.Repository;
import com.chystopo.metarepository.bean.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Collection;

public class RepositoryImpl implements Repository {
    private static final Logger LOG = LoggerFactory.getLogger(RepositoryImpl.class);

    @Override
    public void load(InputStream is) {
        LOG.debug("someone tries to upload data");
    }

    @Override
    public Collection<Item> findChildren(Item parent) {
        return null;
    }

    @Override
    public Collection<Item> findTargets(Item source) {
        return null;
    }
}
