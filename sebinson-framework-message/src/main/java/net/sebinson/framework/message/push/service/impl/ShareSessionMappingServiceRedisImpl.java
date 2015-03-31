package net.sebinson.framework.message.push.service.impl;

import net.sebinson.framework.message.push.service.ShareSessionMappingService;

/**
 * Redis处理映射数据
 * @author jmb
 *
 */
public class ShareSessionMappingServiceRedisImpl implements ShareSessionMappingService
{

    @Override
    public void setMapping(String key, String value, long expireTime)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public String getMapping(String key)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeMaping(String key)
    {
        // TODO Auto-generated method stub

    }

}
