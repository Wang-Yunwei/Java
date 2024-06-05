package com.example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author WangYunwei [2023-11-23]
 */
public class DateTest {

    public static void main(String[] args){

        /**
         * java 1.1 工具
         */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 0); // 获取1天前的日期
        String format = simpleDateFormat.format(instance.getTime());
        System.out.println(format);

        instance.add(Calendar.DATE, -1); // 获取1天前的日期
        String formatA = simpleDateFormat.format(instance.getTime());
        System.out.println(formatA);

        instance.add(Calendar.DATE, -6); // 获取 7 天前的日期,此处和上面的 -1 叠加
        String formatB = simpleDateFormat.format(instance.getTime());
        System.out.println(formatB);

        for(int i = 0; i < 52 ; i++){
            instance.add(Calendar.DATE, -1);
            String formatC = simpleDateFormat.format(instance.getTime());
            instance.add(Calendar.DATE, -6);
            String formatD = simpleDateFormat.format(instance.getTime());
            System.out.println(String.format("%s ~ %s",formatC ,formatD) );
        }

        System.out.println("Integer.valueOf('-23')===>"+Integer.valueOf("-23"));
    }
}
