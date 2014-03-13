package com.chystopo.metarepository.connection;

import java.util.*;

public class PathBuilder<T> {
    private Set<Node<T>> sources;
    private Map<T, Node<T>> nodes;

    public PathBuilder() {
        sources = new HashSet<Node<T>>();
        nodes = new HashMap<T, Node<T>>();
    }

    public void add(T source, T destination) {
        add(new Pair<T>(source, destination));
    }

    private void add(Pair<T> pair) {
        Node<T> source = findOrCreateNode(pair.source);
        Node<T> destination = findOrCreateNode(pair.destination);
        source.addDestination(destination);
        sources.add(source);
    }

    private Node<T> findOrCreateNode(T source) {
        Node<T> node = nodes.get(source);
        if (node == null) {
            node = new Node<T>(source);
            nodes.put(source, node);
        }
        return node;
    }

    public List<List<T>> generatePaths() {
        List<List<T>> result = new LinkedList<List<T>>();
        for (Node<T> source : sources) {
            List<List<T>> paths = extractPaths(source);
            result.addAll(paths);
        }
        result = normalize(result);
        return result;
    }

    private List<List<T>> normalize(List<List<T>> result) {
        PathResolver<T> solver = new PathResolver<T>();
        for (List<T> list : result) {
            solver.add(list);
        }

        return solver.getRepo();
    }

    private List<List<T>> extractPaths(Node<T> source) {
        List<List<T>> result = new LinkedList<List<T>>();
        if (source.getDestinations().isEmpty()) {
            List<T> path = new LinkedList<T>();
            path.add(source.id);
            result.add(path);
            return result;
        }

        for (Node<T> destination : source.getDestinations()) {
            List<List<T>> subpaths = extractPaths(destination);
            for (List<T> subpath : subpaths) {
                List<T> path = new LinkedList<T>();
                path.add(source.id);
                path.addAll(subpath);
                result.add(path);
            }
        }
        return result;
    }
}
