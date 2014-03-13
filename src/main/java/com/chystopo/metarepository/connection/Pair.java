package com.chystopo.metarepository.connection;

public class Pair<T> {
    public final T source;
    public final T destination;

    public Pair(T source, T destination) {
        this.source = source;
        this.destination = destination;
    }
}
