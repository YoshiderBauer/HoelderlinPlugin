package de.yoshi.hoelderlinplugin.utils;

public class startUtils {
    public static String boolToStr(boolean start){
        String saveStart = Boolean.toString(start);
        return saveStart;
    }

    public static Boolean strToBool(String str){
        return Boolean.parseBoolean(str);
    }
}
