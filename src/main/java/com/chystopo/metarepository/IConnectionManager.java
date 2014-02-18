package com.chystopo.metarepository;

import com.chystopo.metarepository.bean.Column;
import com.chystopo.metarepository.bean.Item;
import com.chystopo.metarepository.bean.Mapping;

import java.util.List;

public interface IConnectionManager {
    void save(Column column);

    void save(List<Mapping> mappings);

    List<? extends Item> findColumnLinageByPublicId(String context, long publicId);
}
