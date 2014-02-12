package com.chystopo.metarepository;

import com.chystopo.metarepository.parser.Repo;

import java.io.InputStream;

public interface IParser {

    Repo parse(InputStream inputStream);
}
