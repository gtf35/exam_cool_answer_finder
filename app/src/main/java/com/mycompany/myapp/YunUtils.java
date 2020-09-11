package com.mycompany.myapp;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class YunUtils {
    
    private YunUtils(){}
    
    private String mHtmlStr = "";
    private String mTagStart = "<";
    private String mTagEnd = ">";
    
    public static YunUtils parseHtml(String htmlStr){
        YunUtils yunUtils = new YunUtils();
        yunUtils.mHtmlStr = htmlStr;
        return yunUtils;
    }
    
    public static YunUtils parseHtml(String htmlStr, String tagStart, String tagEnd){
        YunUtils yunUtils = new YunUtils();
        yunUtils.mHtmlStr = htmlStr;
        yunUtils.mTagStart = tagStart;
        yunUtils.mTagEnd = tagEnd;
        return yunUtils;
    }
    
    public String getStringData(String html, String key, String defaultValue){
        Pattern pattern = Pattern.compile("<" + key + ">.+</" + key +">");
        Matcher matcher = pattern.matcher(mHtmlStr);
        if(matcher.find()) matcher.group(1);
        return defaultValue;
    }
    
    public boolean getBooleanData(String html, String key, boolean defaultValue){
        try {
            Pattern pattern = Pattern.compile("<" + key + ">.+</" + key +">");
            Matcher matcher = pattern.matcher(mHtmlStr);
            if(matcher.find()){
                return Boolean.parseBoolean(matcher.group(1));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
    
    public int getIntData(String html, String key, int defaultValue){
        try {
            Pattern pattern = Pattern.compile("<" + key + ">.+</" + key +">");
            Matcher matcher = pattern.matcher(mHtmlStr);
            if(matcher.find()){
                return Integer.parseInt(matcher.group(1));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
    
    public float getFloatData(String html, String key, float defaultValue){
        try {
            Pattern pattern = Pattern.compile("<" + key + ">.+</" + key +">");
            Matcher matcher = pattern.matcher(mHtmlStr);
            if(matcher.find()){
                return Float.parseFloat(matcher.group(1));
            } 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
