package com.chystopo.metarepository.storage;

import com.chystopo.metarepository.bean.Item;

public class PathBuilder {
    public static final String DELIMITER = "/";

    public String build(Item item) {
        return generate(item.getParent()) + DELIMITER;
    }

    private String generate(Item parent) {
        if (parent == null)
            return "";
        return generate(parent.getParent()) + DELIMITER + parent.getId();
    }
}
