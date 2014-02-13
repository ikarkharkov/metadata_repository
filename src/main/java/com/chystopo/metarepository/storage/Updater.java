package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Item;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by oleksiy on 13/02/14.
 */
public class Updater {
    private JdbcTemplate jdbcTemplate;

    public Updater(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void update(Item item) {

    }
}
