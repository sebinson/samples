package com.sample.web.mis.service.base;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenu;

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
