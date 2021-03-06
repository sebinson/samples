package net.sebinson.sample.web.mis.service.base.impl;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.mis.persistence.domain.SampleRole;
import net.sebinson.sample.web.mis.persistence.domain.SampleRoleExample;
import net.sebinson.sample.web.mis.persistence.domain.SampleRoleExample.Criteria;
import net.sebinson.sample.web.mis.persistence.mapper.SampleRoleMapper;
import net.sebinson.sample.web.mis.service.base.SampleRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
