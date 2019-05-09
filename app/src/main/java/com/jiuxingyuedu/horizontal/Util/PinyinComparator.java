package com.jiuxingyuedu.horizontal.Util;

import com.jiuxingyuedu.horizontal.Bean.NewsNean;

import java.util.Comparator;

public class PinyinComparator implements Comparator<NewsNean> {

          public int compare(NewsNean o1, NewsNean o2) {
               //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
              char[] chars1 = o1.getPinyin().toCharArray();
              char[] chars2 = o2.getPinyin().toCharArray();

            //  System.out.println("chars1[0]+.compareTo(chars2[0]======"+(chars1[0]+"").compareTo((chars2[0]+"")));
              return (chars1[0]+"").compareTo((chars2[0]+""));

            }
}
