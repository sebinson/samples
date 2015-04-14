package com.gooagoo.sample.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.SampleUser;
import com.gooagoo.sample.persistence.domain.SampleUserExample;
import com.gooagoo.sample.persistence.domain.SampleUserExample.Criteria;
import com.gooagoo.sample.persistence.mapper.SampleUserMapper;
import com.gooagoo.sample.service.base.SampleUserService;

/**
 * 
 * @author C
 *
 */
@Service
public class SampleUserServiceImpl implements SampleUserService {

    @Autowired
    SampleUserMapper sampleUserMapper;

    @Override
    public List<SampleUser> queryListPage(SampleUser record, Page page) {
        SampleUserExample example = new SampleUserExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(record.getUserName())) {
            criteria.andUserNameLike(record.getUserName() + "%");
        }    
        if (!StringUtils.isEmpty(record.getCdt())) {
            criteria.andCdtLike(record.getCdt() + "%");
            criteria.andCdtBetween(record.getCdt(),record.getCdt());            
        }    
        if (null != page) {
            example.setPage(page);
        }
        List<SampleUser> list = sampleUserMapper.selectByExample(example);
        return list;
    }

    @Override
    public int insert(SampleUser record) {
        return sampleUserMapper.insert(record);
    }

    @Override
    public int delete(String[] keys) {
        int count = 0;
        for (String key : keys) {
            count += sampleUserMapper.deleteByPrimaryKey(key);
        }
        return count;
    }

    @Override
    public int update(SampleUser record) {
        return sampleUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SampleUser selectByKey(String key) {
        return sampleUserMapper.selectByPrimaryKey(key);
    }
}
