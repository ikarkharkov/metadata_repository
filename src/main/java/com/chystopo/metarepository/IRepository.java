package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Item;

import java.io.InputStream;
import java.util.Collection;

public interface IRepository {

    void load(InputStream is);

    Collection<Item> findChildren(Item parent);

    Collection<Item> findTargets(Item source);
}
