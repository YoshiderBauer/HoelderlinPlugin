package de.yoshi.hoelderlinplugin.utils;

public class afkUtils {
    public static String  AfkToStr(boolean afk){
        String pl = Boolean.toString(afk);
        return pl;
    }


    public static boolean StrToAfk(String afk){
        return Boolean.parseBoolean(afk);
    }
}
