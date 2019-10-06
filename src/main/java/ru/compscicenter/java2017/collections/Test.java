package ru.compscicenter.java2017.collections;

import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
        MapMultiSet temp = new MapMultiSet();
        Iterator iterator = temp.iterator();
        iterator.remove();
        iterator.next();
    }
}
