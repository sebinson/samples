package com.gooagoo.sample.service.base;

import java.util.List;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.SampleRole;

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
