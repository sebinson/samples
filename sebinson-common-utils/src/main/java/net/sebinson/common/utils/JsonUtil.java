package net.sebinson.common.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {

    public final static Gson gson;

    static {
        gson = new Gson();
    }

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T toObj(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String[] json2StringArray(String json) {
        return gson.fromJson(json, new TypeToken<String[]>() {
        }.getType());
    }

    public static List<String> json2List(String json) {
        return gson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
    }

    public static Map<String, String> json2Map(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    @SuppressWarnings("unchecked")
    public static <T> T json2Genericity(String json, TypeToken<T> typeToken) {
        return (T) gson.fromJson(json, typeToken.getType());
    }

}
