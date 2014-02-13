package com.chystopo.metarepository.impl;

import com.chystopo.metarepository.IModelStorage;
import com.chystopo.metarepository.IParser;
import com.chystopo.metarepository.IRepository;
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
    private IModelStorage storage;

    @Autowired
    private IParser parser;

    private static final Logger LOG = LoggerFactory.getLogger(Repository.class);

    @Override
    public void load(InputStream is) {
        Repo repo = parser.parse(is);
        storage.saveOrUpdate(repo.getModels());
//        storage.saveOrUpdate(repo.getConnections());
    }

    @Override
    public Collection<Item> findChildren(Item item) {
        return storage.findChildren(item);
    }

    @Override
    public Collection<Item> findTargets(Item source) {
        return null;
    }
}
