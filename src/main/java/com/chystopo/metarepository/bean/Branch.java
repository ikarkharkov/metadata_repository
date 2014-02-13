package com.chystopo.metarepository.bean;

import java.util.List;

public interface Branch {
    List<? extends Item> getChildren();
}
