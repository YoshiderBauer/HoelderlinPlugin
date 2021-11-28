package de.yoshi.hoelderlinplugin.utils;

public class settingsUtils {
    public static String boolToStr(boolean setting){
        String saveSetting = Boolean.toString(setting);
        return saveSetting;
    }

    public static String stringToSetting(String setting){
        return setting;
    }

    public static Boolean strToBool(String str){
        return Boolean.parseBoolean(str);
    }
}
