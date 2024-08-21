package com.example;

import cn.hutool.core.collection.CollUtil;
import com.beust.jcommander.internal.Lists;

import java.util.List;

/**
 * @author WangYunwei [2023-12-22]
 */
public class ContainsAllUtils {

    public static void main(String[] args){

        List<String> arr1 = Lists.newArrayList();
        arr1.add("a");
        arr1.add("b");
        arr1.add("c");

        List<String> arr2 = Lists.newArrayList();
        arr2.add("b");
        arr2.add("c");
        arr2.add("d");

        // 交集
        System.out.println("交集 ===>"+ CollUtil.intersection(arr1,arr2) );
        // 并集
        System.out.println("并集 ===>"+ CollUtil.union(arr1,arr2));
        // 差集
        System.out.println("差集 ===>"+ CollUtil.subtract(arr1,arr2));
        // 是否完全相同
        System.out.println("完全相同 ===>"+ CollUtil.isEqualList(arr1,arr2));
        // arr1 是否完全包含 arr2
        System.out.println("是否全部包含 ===>"+ arr1.containsAll(arr2));
    }
}
