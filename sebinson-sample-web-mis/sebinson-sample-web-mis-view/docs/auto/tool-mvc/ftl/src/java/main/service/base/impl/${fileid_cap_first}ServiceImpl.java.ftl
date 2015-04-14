package com.gooagoo.sample.service.base.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.${fileid_cap_first};
import com.gooagoo.sample.persistence.domain.${fileid_cap_first}Example;
import com.gooagoo.sample.persistence.domain.${fileid_cap_first}Example.Criteria;
import com.gooagoo.sample.persistence.mapper.${fileid_cap_first}Mapper;
import com.gooagoo.sample.service.base.${fileid_cap_first}Service;

/**
 * 
 * @author C
 *
 */
@Service
public class ${fileid_cap_first}ServiceImpl implements ${fileid_cap_first}Service {

    @Autowired
    ${fileid_cap_first}Mapper ${fileid_uncap_first}Mapper;

    @Override
    public List<${fileid_cap_first}> queryListPage(${fileid_cap_first} record, Page page) {
        ${fileid_cap_first}Example example = new ${fileid_cap_first}Example();
        Criteria criteria = example.createCriteria();
        <#list list as column>
        <#if column.x_searchField?lower_case=='y'>
        <#if column.x_cpattern?lower_case=='textbox'>
        if (!StringUtils.isEmpty(record.get${column.x_cname?cap_first}())) {
            criteria.and${column.x_cname?cap_first}Like(record.get${column.x_cname?cap_first}() + "%");
        }    
        </#if>
        <#if column.x_cpattern?lower_case=='datebox' || column.x_cpattern?lower_case=='datetimebox'>
        if (!StringUtils.isEmpty(record.get${column.x_cname?cap_first}())) {
            criteria.and${column.x_cname?cap_first}Like(record.get${column.x_cname?cap_first}() + "%");
            criteria.and${column.x_cname?cap_first}Between(record.get${column.x_cname?cap_first}(),record.get${column.x_cname?cap_first}());            
        }    
        </#if>
        <#if column.x_cpattern?lower_case=='combobox'>
        if (!StringUtils.isEmpty(record.get${column.x_cname?cap_first}())) {
            criteria.and${column.x_cname?cap_first}EqualTo(record.get${column.x_cname?cap_first}());
        }    
        </#if>        
        </#if>
        </#list>
        if (null != page) {
            example.setPage(page);
        }
        List<${fileid_cap_first}> list = ${fileid_uncap_first}Mapper.selectByExample(example);
        return list;
    }

    @Override
    public int insert(${fileid_cap_first} record) {
        return ${fileid_uncap_first}Mapper.insert(record);
    }

    @Override
    public int delete(String[] keys) {
        int count = 0;
        for (String key : keys) {
            count += ${fileid_uncap_first}Mapper.deleteByPrimaryKey(key);
        }
        return count;
    }

    @Override
    public int update(${fileid_cap_first} record) {
        return ${fileid_uncap_first}Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public ${fileid_cap_first} selectByKey(String key) {
        return ${fileid_uncap_first}Mapper.selectByPrimaryKey(key);
    }
}
