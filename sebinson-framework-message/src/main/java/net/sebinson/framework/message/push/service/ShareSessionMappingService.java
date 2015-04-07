package net.sebinson.framework.message.push.service;

public interface ShareSessionMappingService {
    /**
     * 存入共享区的映射
     * 
     * @param key
     *            　socket客户端add
     * @param value
     *            json串
     * @param expireTime
     *            　过期时间
     */
    public void setMapping(String key, String value, long expireTime);

    /**
     * 获取映射数据 精确key
     * 
     * @param key
     * @return 没有数据，返回null
     */
    public String getMapping(String key);

    /* *//**
     * 获取映射数据 模糊key
     * 
     * @param key
     * @return
     */
    /*
     * public String getPreKeyMapping(String preKey);
     */

    /**
     * 删除映射数据
     * 
     * @param key
     * @return
     */
    public void removeMaping(String key);
}
