package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Item;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public abstract class BasicModelHelper<T extends Item> extends DbHelper<T> {

    protected BasicModelHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

}