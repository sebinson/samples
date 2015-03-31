package com.sample.web.mis.service.base;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.mis.persistence.domain.SampleUser;

/**
 * 
 * @author c
 *
 */
public interface SampleUserService 
{               
    public List<SampleUser> queryListPage(SampleUser record, Page page);

    public int insert(SampleUser record);

    public int delete(String[] keys);

    public int update(SampleUser record);

    public SampleUser selectByKey(String key);
}
