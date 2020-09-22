package com.ac.java.utils.datatypes;

import org.apache.commons.lang3.mutable.MutableInt;

public class MutableIntUtil {

    public void recursiveMethod(MutableInt counter) {
        if (counter.intValue() ==10)
            return;
        else {
            counter.add(1);
            recursiveMethod(counter);
        }
    }

    public void recursiveMethod(Integer counter) {
        if (counter == 10)
            return;
        else {
            counter++;
            recursiveMethod(counter);
        }
    }

    public static void main(String[] args) {
        Integer c = new Integer(5);
        new MutableIntUtil().recursiveMethod(c);
        System.out.println("Without MutableInt "+c); // print 5
        MutableInt c1 = new MutableInt(5);
        new MutableIntUtil().recursiveMethod(c1);
        System.out.println("With MutableInt "+c1); // print 10
    }
}
