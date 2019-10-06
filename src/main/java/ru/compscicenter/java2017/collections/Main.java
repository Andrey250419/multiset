package ru.compscicenter.java2017.collections;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
        MapMultiSet<String> temp = new MapMultiSet<>();
        ArrayList<String> temp1 = new ArrayList<>();
        temp.add("dssfs");
        temp.add("wd");
        temp.add("fdewfewew");
        Iterator iterator = temp.iterator();
        while (iterator.hasNext()) {
            out.println(iterator.next());
            iterator.remove();
        }
    }
}
