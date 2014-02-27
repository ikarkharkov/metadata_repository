package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Mapping;

import java.util.Collection;
import java.util.List;

public interface IConnectionManager {
    void save(List<Mapping> mappings);

    Collection<? extends Item> findTargets(Item source);

    Collection<Column> findSources(Item target);
}
