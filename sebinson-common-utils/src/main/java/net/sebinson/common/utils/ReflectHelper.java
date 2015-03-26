package net.sebinson.common.utils;

import java.lang.reflect.Field;

/**
 * 反射工具
 */
public class ReflectHelper {
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        if (obj == null || fieldName == null) {
            return null;
        }
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (Exception e) {
            }
        }
        return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName) {
        Object value = null;
        try {
            Field field = getFieldByFieldName(obj, fieldName);
            if (field != null) {
                if (field.isAccessible()) {
                    value = field.get(obj);
                } else {
                    field.setAccessible(true);
                    value = field.get(obj);
                    field.setAccessible(false);
                }
            }
        } catch (Exception e) {
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValueByFieldType(Object obj, Class<T> fieldType) {
        Object value = null;
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] fields = superClass.getDeclaredFields();
                for (Field f : fields) {
                    if (f.getType() == fieldType) {
                        if (f.isAccessible()) {
                            value = f.get(obj);
                            break;
                        } else {
                            f.setAccessible(true);
                            value = f.get(obj);
                            f.setAccessible(false);
                            break;
                        }
                    }
                }
                if (value != null) {
                    break;
                }
            } catch (Exception e) {
            }
        }
        return (T) value;
    }

    public static boolean setValueByFieldName(Object obj, String fieldName, Object value) {
        try {
            // java.lang.Class.getDeclaredField()方法用法实例教程 -
            // 方法返回一个Field对象，它反映此Class对象所表示的类或接口的指定已声明字段。
            // 此方法返回这个类中的指定字段的Field对象
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field.isAccessible()) {// 获取此对象的 accessible 标志的值。
                field.set(obj, value);// 将指定对象变量上此 Field 对象表示的字段设置为指定的新值
            } else {
                field.setAccessible(true);
                field.set(obj, value);
                field.setAccessible(false);
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
