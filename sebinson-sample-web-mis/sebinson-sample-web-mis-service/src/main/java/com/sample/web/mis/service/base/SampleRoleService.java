package com.sample.web.mis.service.base;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;

import com.sample.web.mis.persistence.domain.SampleRole;

/**
 * 
 * @author c
 *
 */
public interface SampleRoleService 
{               
    public List<SampleRole> queryListPage(SampleRole record, Page page);

    public int insert(SampleRole record);

    public int delete(String[] keys);

    public int update(SampleRole record);

    public SampleRole selectByKey(String key);
}
