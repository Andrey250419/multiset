package ru.compscicenter.java2017.collections;

import java.util.*;

public class MapMultiSet3<E> extends AbstractCollection<E> implements MultiSet<E> {
    private Map<Object, Integer> map = new HashMap<>();
    private int size = 0;


    public MapMultiSet3() {
        map = new HashMap<>();
    }


    public MapMultiSet3(Collection<? extends E> c) {
        map = new HashMap<>();
        addAll(c);
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            private Iterator<E> mapSet = (Iterator<E>) map.entrySet().iterator();
            private E key;
            private int value = 0;

            @Override
            public boolean hasNext() {
                return (value != 0 || mapSet.hasNext());
            }

            @Override
            public E next() {
                if (value == 0) {
                    Map.Entry<E, Integer> nextEntry = (Map.Entry<E, Integer>) mapSet.next();
                    key = nextEntry.getKey();
                    value = nextEntry.getValue();
                }
                value--;
                return key;
            }

            @Override
            public void remove() {
                int count = map.get(key);
                size--;
                if (count == 1) {
                    mapSet.remove();
                } else {
                    map.put(key, count - 1);
                }
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
            throw new IllegalArgumentException();
        } else {
            size += occurrences;
            boolean inMap = false;
            for (Object key : map.keySet()) {
                if (key.equals(o)) {
                    int count = map.get(key);
                    count += occurrences;
                    map.put(key, count);
                    inMap = true;
                    break;
                }
            }
            if (!inMap) {
                map.put(o, occurrences);
            }
            return 0;
        }
    }


    @Override
    public boolean remove(Object e) {
        int count = remove(e, 1);
        return (count == 0);
    }

    @Override
    public int remove(Object e, int occurrences) {
        if (occurrences < 0) {
            throw new IllegalArgumentException();
        } else {
            int temp = 0;
          //  ArrayList<Object> delete = new ArrayList<>();
            for (Object key : map.keySet()) {
                if (e.equals(key)) {
                    int count = map.get(key);
                    temp = count;
                    count -= occurrences;
                    if (count <= 0) {
                        size -= map.get(key);
                        map.remove(key);
                    } else {
                        size -= occurrences;
                        map.put(key, count);
                    }
                }
            }
           /* for (int i = 0; i < delete.size(); i++) {
                map.remove(delete.get(i));
            }*/
            return temp;
        }
    }


    @Override
    public int count(Object e) {
        for (Object key : map.keySet()) {
            if (e.equals(key)) {
                return map.get(key);
            }
        }
        return 0;
    }


    @Override
    public void clear() {
        size = 0;
        map.clear();
    }

   /* @Override
    public boolean equals(Object o) {
        if (!(o instanceof MultiSet)) {
            return false;
        }
        MultiSet temp = (MultiSet) o;
        if (!(containsAll(temp)))
            return false;
        //temp содержится в this
            return !retainAll(temp);
    }*/

   /* @Override
    public boolean equals(Object o) {
        if (o instanceof MapMultiSet3) {
            MapMultiSet3 temp = (MapMultiSet3) o;
            int count = 0;
            Iterator iteratorMap = iterator();
            while (iteratorMap.hasNext()) {
                boolean equals = false;
                Object key = iteratorMap.next();
                Iterator iteratorTemp = temp.iterator();
                while (iteratorTemp.hasNext()) {
                    Object keyTemp = iteratorTemp.next();
                    if (key.equals(keyTemp)) {
                        temp.remove(keyTemp);
                        equals = true;
                    }
                }
                if (!equals) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
*/
    @Override
    public boolean equals(Object o) {
        if (o instanceof MapMultiSet3) {
            Object[] masO = ((MapMultiSet3) o).toArray();
            Object[] masMap = toArray();
            boolean[] flag = new boolean[masMap.length];
            int count = 0;
            for (int i = 0; i < masMap.length; i++) {
                for (int j = 0; j < masO.length; j++) {
                    if (!flag[i] && masMap[i].equals(masO[j])) {
                        flag[i] = true;
                        count++;
                    }
                }
            }
            if (count == masO.length) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
