package study.luofeng.com.myappmarket.global;

/**
 * 常量
 * Created by weixi on 2016/9/8.
 */
public class MyConstant {
    //Fragment参数的键
    //public static final String TAB="tab";

    //响应失败时，handler的msg的what
    public static final int HTTP_MSG_WHAT_ERROR = 500;
    //响应内容为空时，handler的msg的what
    public static final int HTTP_MSG_WHAT_EMPTY = 300;
    //响应成功且有内容时，handler的msg的what
    public static final int HTTP_MSG_WHAT_SUCCESS = 200;

    public static final String BASE_URL = "http://127.0.0.1:8090/";

    public static final String URL_KEY_HOME = "home"; //主页
    public static final String URL_KEY_APP = "app"; //应用
    public static final String URL_KEY_GAME = "game"; //游戏
    public static final String URL_KEY_SUBJECT = "subject"; //专题
    public static final String URL_KEY_RECOMMEND = "recommend"; //推荐
    public static final String URL_KEY_CATEGORY = "category"; //分类
    public static final String URL_KEY_HOT = "hot"; //排行
    public static final String URL_KEY_DETAIL = "detail"; //详情
    public static final String URL_KEY_DOWNLOAD = "download"; //下载
    public static final String URL_KEY_IMAGE = "image"; //图片

    public static final String URL_IMAGE_BASE = BASE_URL + URL_KEY_IMAGE + "?name="; //图片基础url
}
