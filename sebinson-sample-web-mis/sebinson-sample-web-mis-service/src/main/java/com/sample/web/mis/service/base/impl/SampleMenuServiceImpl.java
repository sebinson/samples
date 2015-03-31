package com.sample.web.mis.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sample.web.common.bean.pagination.Page;
import com.sample.web.mis.persistence.domain.SampleMenu;
import com.sample.web.mis.persistence.domain.SampleMenuExample;
import com.sample.web.mis.persistence.domain.SampleMenuExample.Criteria;
import com.sample.web.mis.persistence.mapper.SampleMenuMapper;
import com.sample.web.mis.service.base.SampleMenuService;

/**
 * 
 * @author C
 *
 */
@Service
public class SampleMenuServiceImpl implements SampleMenuService {

    @Autowired
    SampleMenuMapper sampleMenuMapper;

    @Override
    public List<SampleMenu> queryListPage(SampleMenu record, Page page) {
        SampleMenuExample example = new SampleMenuExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(record.getMenuCode())) {
            criteria.andMenuCodeLike(record.getMenuCode() + "%");
        }    
        if (!StringUtils.isEmpty(record.getMenuType())) {
            criteria.andMenuTypeEqualTo(record.getMenuType());
        }    
        if (!StringUtils.isEmpty(record.getMenuName())) {
            criteria.andMenuNameLike(record.getMenuName() + "%");
        }    
        if (!StringUtils.isEmpty(record.getMenuStatus())) {
            criteria.andMenuStatusEqualTo(record.getMenuStatus());
        }    
        if (!StringUtils.isEmpty(record.getMenuParentId())) {
            criteria.andMenuParentIdLike(record.getMenuParentId() + "%");
        }    
        if (null != page) {
            example.setPage(page);
        }
        List<SampleMenu> list = sampleMenuMapper.selectByExample(example);
        return list;
    }

    @Override
    public int insert(SampleMenu record) {
        return sampleMenuMapper.insert(record);
    }

    @Override
    public int delete(String[] keys) {
        int count = 0;
        for (String key : keys) {
            count += sampleMenuMapper.deleteByPrimaryKey(key);
        }
        return count;
    }

    @Override
    public int update(SampleMenu record) {
        return sampleMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SampleMenu selectByKey(String key) {
        return sampleMenuMapper.selectByPrimaryKey(key);
    }
}
