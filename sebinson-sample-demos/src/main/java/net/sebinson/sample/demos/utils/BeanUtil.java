package net.sebinson.sample.demos.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {

    public static Map<String, Object> bean2Map(Object bean) {

        Map<String, Object> map = new HashMap<String, Object>();
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            try {
                if (method.getName().startsWith("get")) {
                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);
                    Object value = method.invoke(bean, new Object[0]);
                    if (value == null || value.toString().trim().equals("")) {
                        continue;
                    }
                    map.put(field, value);
                }

            } catch (Exception e) {
            }

        }
        return map;
    }

    public static void main(String... strings) {
        TestBean bean = new TestBean();
        bean.setKey("name");
        bean.setValue("sebinson");
        Map<String,Object> map = BeanUtil.bean2Map(bean);
        System.out.print(map);
    }
}
