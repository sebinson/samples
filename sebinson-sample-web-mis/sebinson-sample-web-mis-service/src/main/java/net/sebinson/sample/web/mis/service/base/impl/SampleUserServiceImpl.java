package net.sebinson.sample.web.mis.service.base.impl;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.mis.persistence.domain.SampleUser;
import net.sebinson.sample.web.mis.persistence.domain.SampleUserExample;
import net.sebinson.sample.web.mis.persistence.domain.SampleUserExample.Criteria;
import net.sebinson.sample.web.mis.persistence.mapper.SampleUserMapper;
import net.sebinson.sample.web.mis.service.base.SampleUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
