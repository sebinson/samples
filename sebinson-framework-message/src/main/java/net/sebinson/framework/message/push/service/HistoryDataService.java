package net.sebinson.framework.message.push.service;

import java.util.List;

import net.sebinson.framework.message.common.ClientInfoMsg;
import net.sebinson.framework.message.push.bean.GAGMessage;

public interface HistoryDataService
{
    /**
     * 获取key对应的单条数据
     * @param key
     * @return
     */
    public <T> GAGMessage<T> getData(String key);

    /**
     * 获取key对应的多条数据
     * @param key
     * @return
     */
    public <T> List<GAGMessage<T>> getListData(String key);

    /**
     * 删除key对应数据
     * @param Key
     */
    public void delete(String Key);

    /**
     * 存入一条数据
     * @param key
     * @param value
     * @param expireTime　过期时间
     */
    public <T> void setData(String key, GAGMessage<T> value, int expireTime);

    /**
     * value为List,向List中存入一条数据
     * @param key
     * @param value 向List中存入一条数据
     * @param expireTime　key的过期时间
     */
    public <T> void setListData(String key, List<GAGMessage<T>> value, int expireTime);

    /**
     *  TODO 用到　#FutionItypeCache
     * 处理历史数据
     * @param sessionId
     * @param add
     * @param clientInfoMsg
     */
    public void invokeHistoryData(long sessionId, String add, ClientInfoMsg clientInfoMsg);

    /**
     * 释放资源
     */
    public void distory();

}
