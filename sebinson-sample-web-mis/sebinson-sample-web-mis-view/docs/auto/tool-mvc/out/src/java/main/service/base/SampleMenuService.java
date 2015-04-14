package com.gooagoo.sample.service.base;

import java.util.List;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.SampleMenu;

/**
 * 
 * @author c
 *
 */
public interface SampleMenuService 
{               
    public List<SampleMenu> queryListPage(SampleMenu record, Page page);

    public int insert(SampleMenu record);

    public int delete(String[] keys);

    public int update(SampleMenu record);

    public SampleMenu selectByKey(String key);
}
