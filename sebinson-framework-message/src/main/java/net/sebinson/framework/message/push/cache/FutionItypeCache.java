package net.sebinson.framework.message.push.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 转发器功能与itype对应的关系　临时用，后期用zookper更新本地缓存
 * 对于像开发票，退房这样的请求，可以在字典表里配置其映射，然后缓存于本地即可
 * 如[gtsf01//开发票接口编码，N//转发器开发票功能]
 * 在发送消息失败后，根据消息的接口编码，就能确定存入缓存中的key是 N_shopentityid_mac:value
 * @author jmb
 *
 *  临时用
 */
//@Deprecated
public class FutionItypeCache
{
    //key=itype,value=转发器功能N
    private static Map<String, String> map = new HashMap<String, String>();

    public static String getValue(String key)
    {
        return map.get(key);
    }

    public static String setValue(String key, String value)
    {
        return map.put(key, value);
    }

}
