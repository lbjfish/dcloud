package com.sida.dcloud.training.common;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TrainingTest {
    public static void main(String[] args) {
        Supplier<Integer> s = () -> 1;
        System.out.println(s.get());
        Function<Integer,Integer> function = x -> x+4;
        Consumer<Integer> consumer = x -> System.out.println(x);
        TrainingTest instance = new TrainingTest();
        Function<Integer, String>  func = TrainingTest::test;
        Supplier<String> c = instance::test1;
        Consumer<Void> cc = instance::test2;
        TrainingTestIntf ccc = instance::test3;
        ccc.test();
    }

    public static String test(Integer x) {
        return String.format("Your input is %d", x);
    }

    public String test1() {
        return "";
    }

    public void test2(Void v) {

    }

    public void test3() {

    }
}

