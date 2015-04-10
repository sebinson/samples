package net.sebinson.common.cache.zookeeper.type;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TreeCache<T> implements Cache {
    private Map<String, T> map = new ConcurrentHashMap<String, T>();
    private Map<String, List<T>> next = new ConcurrentHashMap<String, List<T>>();
    private String currentId = "id";
    private String parentId = "parentId";

    /**
     * 根据编码值获取信息
     * 
     * @param key
     * @return
     */
    public T get(String key) {
        synchronized (this.map) {
            return this.map.get(key);
        }
    }

    /**
     * 根据编码值获取下级信息 输入-1可获取所有省级区域信息
     * 
     * @param key
     * @return
     */
    public List<T> getNext(String key) {
        synchronized (this.next) {
            return this.next.get(key);
        }
    }

    @SuppressWarnings("unchecked")
    public void reload(Object data) {
        List<T> list = (List<T>) data;
        synchronized (this.map) {
            this.map.clear();
            for (T t : list) {
                this.map.put(this.getValueByField(t, this.currentId), t);
            }
        }
        synchronized (this.next) {
            for (T t : list) {
                if (!this.next.containsKey(this.getValueByField(t, this.parentId))) {
                    this.next.put(this.getValueByField(t, this.parentId), new ArrayList<T>());
                }
                this.next.get(this.getValueByField(t, this.parentId)).add(t);
            }
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
}
