package com.chystopo.metarepository.impl;

import com.chystopo.metarepository.IConnectionManager;
import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.IRepository;
import com.chystopo.metarepository.IStorage;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.parser.Repo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;

@Service
public class Repository implements IRepository {

    @Autowired
    private IStorage storage;

    @Autowired
    private IParser parser;

    @Autowired
    private IConnectionManager connectionManager;

    private static final Logger LOG = LoggerFactory.getLogger(Repository.class);

    @Override
    public void load(String context, InputStream is) {
        Repo repo = parser.parse(context, is);
        storage.saveOrUpdate(repo.getModels());
        connectionManager.save(repo.getMappings());
    }

    @Override
    public Collection<? extends Item> findChildren(Item parent) {
        return findChildren(parent, false);
    }

    @Override
    public Collection<? extends Item> findTargets(Item source) {
        return connectionManager.findTargets(source);
    }

    @Override
    public Collection<? extends Item> findChildren(Item parent, boolean recursively) {
        return storage.findChildren(parent);
    }

    @Override
    public Collection<? extends Item> findParents(Item item) {
        return storage.findParents(item);
    }

    @Override
    public Collection<? extends Item> findSources(Item target) {
        return connectionManager.findSources(target);
    }
}
