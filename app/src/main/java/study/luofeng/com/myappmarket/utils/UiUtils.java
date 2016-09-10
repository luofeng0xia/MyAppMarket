package study.luofeng.com.myappmarket.utils;

import android.graphics.drawable.Drawable;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import study.luofeng.com.myappmarket.global.MyApplication;

/**
 * 封装一些UI相关的方法
 * Created by weixi on 2016/9/8.
 */
public class UiUtils {

    /*****************加载资源文件*****************/

    //获得字符串
    public static String getString(int id){
        return MyApplication.context.getResources().getString(id);
    }

    //获得字符串数组
    public static String[] getStringArray(int id){
        return MyApplication.context.getResources().getStringArray(id);
    }

    //获得颜色
    public static int getColor(int id){
        return MyApplication.context.getResources().getColor(id);
    }

    //获得尺寸 像素
    public static int getDimens(int id){
        return MyApplication.context.getResources().getDimensionPixelSize(id);
    }

    //获得图片
    public static Drawable getDrawable(int id){
        return MyApplication.context.getResources().getDrawable(id);
    }

    /**********************dp、px互相转换**************************/
    
    //px转dp
    public static float pix2dp(int pix){
        //获得像素密度
        float density = MyApplication.context.getResources().getDisplayMetrics().density;
        return pix/density;
    }

    //dp转px
    public static int dp2pix(float dp){
        float density = MyApplication.context.getResources().getDisplayMetrics().density;
        return (int) (dp*density+0.5f);
    }

    /**********************加载布局文件***********************/
    public static View inflate(int id){
        return View.inflate(MyApplication.context,id,null);
    }

    // RecyclerView加载item布局，上面那种无法居中显示
    public static View inflate(int id,ViewGroup parent){
        return LayoutInflater.from(MyApplication.context).inflate(id,parent,false);
    }

    /***********************判断是否在主线程********************/
    public static boolean isRunOnMain(){
        int myTid = Process.myTid();
        return myTid == MyApplication.mainThreadId;
    }
}
