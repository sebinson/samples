package com.gooagoo.sample.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.SampleRole;
import com.gooagoo.sample.persistence.domain.SampleRoleExample;
import com.gooagoo.sample.persistence.domain.SampleRoleExample.Criteria;
import com.gooagoo.sample.persistence.mapper.SampleRoleMapper;
import com.gooagoo.sample.service.base.SampleRoleService;

/**
 * 
 * @author C
 *
 */
@Service
public class SampleRoleServiceImpl implements SampleRoleService {

    @Autowired
    SampleRoleMapper sampleRoleMapper;

    @Override
    public List<SampleRole> queryListPage(SampleRole record, Page page) {
        SampleRoleExample example = new SampleRoleExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(record.getRoleCode())) {
            criteria.andRoleCodeLike(record.getRoleCode() + "%");
        }    
        if (!StringUtils.isEmpty(record.getRoleName())) {
            criteria.andRoleNameLike(record.getRoleName() + "%");
        }    
        if (null != page) {
            example.setPage(page);
        }
        List<SampleRole> list = sampleRoleMapper.selectByExample(example);
        return list;
    }

    @Override
    public int insert(SampleRole record) {
        return sampleRoleMapper.insert(record);
    }

    @Override
    public int delete(String[] keys) {
        int count = 0;
        for (String key : keys) {
            count += sampleRoleMapper.deleteByPrimaryKey(key);
        }
        return count;
    }

    @Override
    public int update(SampleRole record) {
        return sampleRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SampleRole selectByKey(String key) {
        return sampleRoleMapper.selectByPrimaryKey(key);
    }
}
