package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Item;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BasicModelHelper<T extends Item> extends DbHelper<T> {

    protected BasicModelHelper(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

}