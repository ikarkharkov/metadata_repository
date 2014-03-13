package com.chystopo.metarepository.connection;

import java.util.HashSet;
import java.util.Set;

public class Node<T> {
    public T id;
    private Set<Node<T>> destinations = new HashSet<Node<T>>();

    public Node(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != null ? !id.equals(node.id) : node.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void addDestination(Node<T> destination) {
        this.destinations.add(destination);
    }

    public Set<Node<T>> getDestinations() {
        return destinations;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
