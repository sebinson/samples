package net.sebinson.framework.message.push.cache;

import java.util.List;

import net.sebinson.framework.message.push.bean.GAGMessage;
import net.sebinson.framework.message.push.util.EHCacheTransportUtil;
import net.sf.ehcache.Cache;

/**
 * 单节点，历史数据存于本地
 * c1:保存数据到历史库中 N(转发器功能)_shopentityid_mmdd(月天),List<GAGMessage>,过期时间天
 * c2:保存数据到历史库中 userId_mmdd(月天),List<GAGMessage>,过期时间天
 * @author jmb
 *
 */
public class HistortyDataLocalCache
{
    private static HistortyDataLocalCache histortyDataLocalCache;

    private HistortyDataLocalCache()
    {

    }

    public static HistortyDataLocalCache getInstance()
    {
        if (histortyDataLocalCache == null)
        {
            synchronized (HistortyDataLocalCache.class)
            {
                if (histortyDataLocalCache == null)
                {
                    histortyDataLocalCache = new HistortyDataLocalCache();
                }
            }
        }
        return histortyDataLocalCache;
    }

    private final String cacheName = "histortyGAGMessage";

    /** 秒为单位 如为 -1,不设置 默认为-1 */
    private int expireTime = -1;

    /**EHcache 底层也是map(xx_MMdd,List<GAGMessage<T>>)实现的*/
    private Cache cache;

    /**
     * 初始化
     */
    public void init()
    {
        this.cache = EHCacheTransportUtil.createCache(this.cacheName);
    }

    public <T> void setValue(String key, List<GAGMessage<T>> value, int expireTime)
    {
        synchronized (key.intern())
        {
            EHCacheTransportUtil.put(this.cache, key, value);
            if (expireTime >= 0)//缓存失效处理
            {
                EHCacheTransportUtil.expire(this.cache, key, this.expireTime);
            }
            else
            {
                EHCacheTransportUtil.eternal(this.cache, key, true);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<GAGMessage<T>> getValue(String key)
    {
        List<GAGMessage<T>> value = (List<GAGMessage<T>>) EHCacheTransportUtil.get(this.cache, key);
        return value;
    }

    /**
     * 释放资源
     */
    public void destory()
    {
        if (this.cache != null)
        {
            EHCacheTransportUtil.dispose(this.cache.getName());
        }
    }

}
