package study.luofeng.com.myappmarket.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * RequestParams
 * Created by weixi on 2016/9/12.
 */
public class RequestParams {

    private String model;
    private int index=-1;
    private Map<String,String> requestMap = new HashMap<>();

    public void putString(String key,String value){
        requestMap.put(key,value);
    }

    public RequestParams() {
    }

    public RequestParams(String model, int index) {
        this.model = model;
        this.index = index;
    }

    public RequestParams(String model, int index, Map<String, String> requestMap) {
        this.model = model;
        this.index = index;
        this.requestMap = requestMap;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Map<String, String> getRequestMap() {
        return requestMap;
    }

    public void setRequestMap(Map<String, String> requestMap) {
        this.requestMap = requestMap;
    }
}
