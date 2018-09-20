package com.sida.xiruo.common.components.collection;

import java.util.ArrayList;
import java.util.EmptyStackException;

public class ArrayStack<E> extends ArrayList<E>{


    public ArrayStack() {
        super();
    }


    public ArrayStack(int initialSize) {
        super(initialSize);
    }


    public boolean empty() {
        return isEmpty();
    }


    public E peek(){
        int n = size();
        if (n <= 0) {
           	return null;
        } else {
            return get(n - 1);
        }
    }


    public E peek(int n) throws EmptyStackException {
        int m = (size() - n) - 1;
        if (m < 0) {
            return null;
        } else {
            return get(m);
        }
    }


    public E pop(){
        int n = size();
        if (n <= 0) {
            return null;
        } else {
            return remove(n - 1);
        }
    }


    public E push(E item) {
        add(item);
        return item;
    }


    public int search(E object) {
        int i = size() - 1;        // Current index
        int n = 1;                 // Current distance
        while (i >= 0) {
            Object current = get(i);
            if ((object == null && current == null) ||
                (object != null && object.equals(current))) {
                return n;
            }
            i--;
            n++;
        }
        return -1;
    }
}
