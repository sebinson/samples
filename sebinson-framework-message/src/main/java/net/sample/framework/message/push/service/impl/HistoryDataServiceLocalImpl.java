package net.sample.framework.message.push.service.impl;

import java.util.List;

import net.sample.framework.message.common.ClientInfoMsg;
import net.sample.framework.message.push.bean.GAGMessage;
import net.sample.framework.message.push.cache.HistortyDataLocalCache;
import net.sample.framework.message.push.service.HistoryDataService;

/**
 * 本地处理历史数据
 * @author jmb
 *
 */
public class HistoryDataServiceLocalImpl implements HistoryDataService
{

    private HistortyDataLocalCache histortyDataLocalCache;

    public HistoryDataServiceLocalImpl()
    {
        this.histortyDataLocalCache = HistortyDataLocalCache.getInstance();
    }

    @Override
    public void distory()
    {
        if (this.histortyDataLocalCache != null)
        {
            this.histortyDataLocalCache.destory();
        }
    }

    @Override
    public <T> GAGMessage<T> getData(String key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> List<GAGMessage<T>> getListData(String key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String Key)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> void setData(String key, GAGMessage<T> value, int expireTime)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public <T> void setListData(String key, List<GAGMessage<T>> value, int expireTime)
    {
        this.histortyDataLocalCache.setValue(key, value, expireTime);
    }

    @Override
    public void invokeHistoryData(long sessionId, String add, ClientInfoMsg clientInfoMsg)
    {
        // TODO Auto-generated method stub

    }

}
