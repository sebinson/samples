package net.sebinson.common.cache.zookeeper.type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DictionaryCache<T> implements Cache {
    private Map<String, Map<String, T>> map = new ConcurrentHashMap<String, Map<String, T>>();
    private String type = "type";
    private String code = "code";

    public Map<String, T> get(String type) {
        synchronized (this.map) {
            return this.map.get(type);
        }
    }

    public T get(String type, String name) {
        synchronized (this.map) {
            return this.map.get(type).get(name);
        }
    }

    private String getValueByField(T t, String fieldName) {
        try {
            String result = null;
            Class<? extends Object> fromClass = t.getClass();
            Field[] fromFields = fromClass.getDeclaredFields();
            for (Field fromField : fromFields) {
                if (!Modifier.isFinal(fromField.getModifiers())) {
                    fromField.setAccessible(true);
                    if (fieldName.equals(fromField.getName())) {
                        result = fromField.get(t).toString();
                        fromField.setAccessible(false);
                        break;
                    }
                    fromField.setAccessible(false);
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void reload(Object data) {
        List<T> list = (List<T>) data;
        String tempType = null;
        String tempCode = null;
        synchronized (this.map) {
            this.map.clear();
            for (T t : list) {
                tempType = this.getValueByField(t, this.type);
                tempCode = this.getValueByField(t, this.code);
                if (!this.map.containsKey(tempType)) {
                    this.map.put(tempType, new ConcurrentHashMap<String, T>());
                }
                this.map.get(tempType).put(tempCode, t);
            }
        }

    }

}
