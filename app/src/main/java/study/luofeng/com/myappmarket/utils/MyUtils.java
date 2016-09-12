package study.luofeng.com.myappmarket.utils;

import java.util.Formatter;

/**
 * MyUtils
 * Created by weixi on 2016/9/12.
 */
public class MyUtils {

    /**
     * 获得应用的大小，m为单位
     * @param size long类型 字节为单位
     * @return appSize
     */
    public static String getAppSize(long size){
        return new Formatter().format("%.2f", size / 1024f / 1024f) + "MB";
    }
}
