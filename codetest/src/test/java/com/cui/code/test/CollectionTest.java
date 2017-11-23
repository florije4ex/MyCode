package com.cui.code.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Collection集合测试
 * Created by cuishixiang on 2017-10-15.
 */
public class CollectionTest {

    /**
     * UnsupportedOperationException异常测试
     * Arrays.asList返回的是java.util.Arrays.ArrayList
     * 其继承自AbstractList，add方法没有覆写，因此直接抛出了UnsupportedOperationException
     */
    @Test
    public void testArrays() {
        List<Integer> list = Arrays.asList(123, 234, 345);
        list.add(789);
        System.out.println(list);
    }

    /**
     * 如果需要更改数组转化而成的list，则需将其重新包装成list即可
     */
    @Test
    public void testArrays2List() {
        List<Integer> list = new ArrayList<>(Arrays.asList(123, 234, 345));
        list.add(789);
        System.out.println(list);
    }

    /**
     * collections的copy方法测试
     * copy后对原集合的增删操作不会影响新集合，对元素的操作会影响新集合，
     * 而这里的string对象改变却没有影响到集合，是因为String对象是不可变的，只是新建了一个对象，而原对象没变
     */
    @Test
    public void testCollectionCopy() {
        List<String> srcList = new ArrayList<>();
        srcList.add("111");
        String element2 = "222";
        srcList.add(element2);
        srcList.add("333");
        ArrayList<Object> destList = new ArrayList<>();

        Collections.addAll(destList, new String[srcList.size()]);
        Collections.copy(destList, srcList);

        System.out.println("srcList：");
        srcList.forEach(System.out::println);
        System.out.println("destList：");
        destList.forEach(System.out::println);


        srcList.add("444");

        System.out.println("after srcList add new element，srcList：");
        srcList.forEach(System.out::println);
        System.out.println("after srcList add new element，destList：");
        destList.forEach(System.out::println);

        srcList.remove(2);
        System.out.println("after srcList remove element index of 2，srcList：");
        srcList.forEach(System.out::println);
        System.out.println("after srcList remove element index of 2，destList：");
        destList.forEach(System.out::println);

        element2 = "222aha";
        System.out.println("after element2 change ，srcList：");
        srcList.forEach(System.out::println);
        System.out.println("after element2 change ，destList：");
        destList.forEach(System.out::println);

    }


    @Test
    public void testDataTypeOPeration() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(111);
        integerList.add(222);
        integerList.add(333);
        integerList.add(444);

        boolean remove = integerList.remove("222");
        System.out.println(remove);
        integerList.forEach(System.out::println);
    }


}
