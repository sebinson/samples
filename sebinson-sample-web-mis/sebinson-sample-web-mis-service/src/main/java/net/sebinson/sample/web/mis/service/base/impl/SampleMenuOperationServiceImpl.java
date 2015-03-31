package net.sebinson.sample.web.mis.service.base.impl;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperation;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperationExample;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperationExample.Criteria;
import net.sebinson.sample.web.mis.persistence.mapper.SampleMenuOperationMapper;
import net.sebinson.sample.web.mis.service.base.SampleMenuOperationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
