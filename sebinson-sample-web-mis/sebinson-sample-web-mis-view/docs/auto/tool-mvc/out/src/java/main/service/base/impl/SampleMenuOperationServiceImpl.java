package com.gooagoo.sample.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.SampleMenuOperation;
import com.gooagoo.sample.persistence.domain.SampleMenuOperationExample;
import com.gooagoo.sample.persistence.domain.SampleMenuOperationExample.Criteria;
import com.gooagoo.sample.persistence.mapper.SampleMenuOperationMapper;
import com.gooagoo.sample.service.base.SampleMenuOperationService;

/**
 * 
 * @author C
 *
 */
@Service
public class SampleMenuOperationServiceImpl implements SampleMenuOperationService {

    @Autowired
    SampleMenuOperationMapper sampleMenuOperationMapper;

    @Override
    public List<SampleMenuOperation> queryListPage(SampleMenuOperation record, Page page) {
        SampleMenuOperationExample example = new SampleMenuOperationExample();
        Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(record.getMenuId())) {
            criteria.andMenuIdLike(record.getMenuId() + "%");
        }    
        if (!StringUtils.isEmpty(record.getOperCode())) {
            criteria.andOperCodeLike(record.getOperCode() + "%");
        }    
        if (!StringUtils.isEmpty(record.getOperName())) {
            criteria.andOperNameLike(record.getOperName() + "%");
        }    
        if (null != page) {
            example.setPage(page);
        }
        List<SampleMenuOperation> list = sampleMenuOperationMapper.selectByExample(example);
        return list;
    }

    @Override
    public int insert(SampleMenuOperation record) {
        return sampleMenuOperationMapper.insert(record);
    }

    @Override
    public int delete(String[] keys) {
        int count = 0;
        for (String key : keys) {
            count += sampleMenuOperationMapper.deleteByPrimaryKey(key);
        }
        return count;
    }

    @Override
    public int update(SampleMenuOperation record) {
        return sampleMenuOperationMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SampleMenuOperation selectByKey(String key) {
        return sampleMenuOperationMapper.selectByPrimaryKey(key);
    }
}
