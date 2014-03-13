package com.chystopo.metarepository.connection;

import java.util.ArrayList;
import java.util.List;

public class PathResolver<T> {
    List<List<T>> repo = new ArrayList<List<T>>();

    public void add(List<T> list) {
        validate(list);
        if (!contains(list)) {
            normalizeRepoByNewElement(list);
            repo.add(list);
        }
    }

    private void normalizeRepoByNewElement(List<T> list) {
        List<List<T>> lists = findByLengthAndAtLeastOneElement(list);
        for (List<T> elements : lists) {
            if (isPartOf(list, elements)) {
                repo.remove(elements);
            }
        }
    }

    private List<List<T>> findByLengthAndAtLeastOneElement(List<T> list) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (List<T> ts : repo) {
            if (ts.size() < list.size() && containsAtLeastOneElement(ts, list))
                result.add(ts);
        }

        return result;
    }

    private boolean containsAtLeastOneElement(List<T> ts, List<T> list) {
        for (T t : list) {
            if (ts.contains(t))
                return true;
        }
        return false;
    }

    private void validate(List<T> list) {
        if (list.size() < 2) {
            throw new IllegalArgumentException("List should have at least two elements");
        }
    }

    public boolean contains(List<T> list) {
        List<List<T>> lists = findByLengthAndElement(list.size(), list.get(0));
        for (List<T> elements : lists) {
            if (isPartOf(elements, list)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPartOf(List<T> elements, List<T> list) {
        for (int i = 0; i <= elements.size() - list.size(); i++) {
            if (isEquals(elements.subList(i, i + list.size()), list))
                return true;
        }
        return false;
    }

    private boolean isEquals(List<T> one, List<T> two) {
        for (int i = 0; i < one.size(); i++) {
            if (!one.get(i).equals(two.get(i))) {
                return false;
            }
        }
        return true;
    }

    private List<List<T>> findByLengthAndElement(int size, T element) {
        List<List<T>> result = new ArrayList<List<T>>();
        for (List<T> list : repo) {
            if (list.size() >= size && list.contains(element)) {
                result.add(list);
            }
        }
        return result;
    }

    public List<List<T>> getRepo() {
        return repo;
    }
}
