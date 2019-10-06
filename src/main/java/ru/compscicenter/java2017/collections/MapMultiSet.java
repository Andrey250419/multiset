package ru.compscicenter.java2017.collections;

import java.util.*;

public class MapMultiSet<E> extends AbstractCollection<E> implements MultiSet<E> {
    private Map<Object, Integer> map;
    private int size = 0;


    public MapMultiSet() {
        map = new HashMap<>();
    }

    public MapMultiSet(Collection<? extends E> c) {
        this();
        addAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            private E key;
            private int value = 0;
            private boolean earlyNext = false;
            private Iterator<E> mapSet = (Iterator<E>) map.entrySet().iterator();

            @Override
            public boolean hasNext() {
                return (value != 0 || mapSet.hasNext());
            }

            @Override
            public E next() {
                earlyNext = true;
                try {
                    if (value == 0) {
                        Map.Entry<E, Integer> keyValue = (Map.Entry<E, Integer>) mapSet.next();
                        key = keyValue.getKey();
                        value = keyValue.getValue();
                    }
                    value--;
                    return key;
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Doesn't have next element");
                }
            }

            @Override
            public void remove() {
                if (!earlyNext) {
                    throw new IllegalStateException("Method remove() may not be called at the beginning or twice");
                }
                earlyNext = false;
                int count = map.get(key);
                if (count == 1) {
                    mapSet.remove();
                } else {
                    map.put(key, count - 1);
                }
                size--;
            }
        };
        return iterator;
    }

    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }


    @Override
    public boolean contains(Object o) {
        return (map.containsKey(o));
    }

    @Override
    public boolean add(Object o) {
        add(o, 1);
        return true;
    }


    @Override
    public int add(Object o, int occurrences) {
        if (occurrences < 0) {
            throw new IllegalArgumentException("Number of occurrences must be non-negative");
        } else {
            int count = map.getOrDefault(o, 0);
            map.put(o, count + occurrences);
            size += occurrences;
            return count;
        }
    }


    @Override
    public boolean remove(Object e) {
        int count = remove(e, 1);
        return (count != 0);
    }

    @Override
    public int remove(Object e, int occurrences) {
        if (occurrences < 0) {
            throw new IllegalArgumentException("Number of occurrences must be non-negative");
        } else {
            int count = map.getOrDefault(e, 0);
            if (count <= occurrences) {
                map.remove(e);
                size -= count;
            } else {
                int delta = count - occurrences;
                map.put(e, delta);
                size -= delta;
            }
            return count;
        }
    }


    @Override
    public int count(Object e) {
        return map.getOrDefault(e, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MultiSet)) {
            return false;
        }
        MultiSet temp = (MultiSet) o;
        if (!(containsAll(temp))) {
            return false;
        }
        return !retainAll(temp);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}