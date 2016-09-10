package study.luofeng.com.myappmarket.bean;

/**
 * HomeInfo
 * Created by weixi on 2016/9/10.
 */
public class HomeInfo {
    private String[] picture;
    private AppInfo[] list;

    public String[] getPicture() {
        return picture;
    }

    public void setPicture(String[] picture) {
        this.picture = picture;
    }

    public AppInfo[] getList() {
        return list;
    }

    public void setList(AppInfo[] list) {
        this.list = list;
    }
}
