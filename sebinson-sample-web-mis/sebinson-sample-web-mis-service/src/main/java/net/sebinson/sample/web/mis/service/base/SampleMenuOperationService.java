package net.sebinson.sample.web.mis.service.base;

import java.util.List;

import net.sebinson.sample.web.common.bean.pagination.Page;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperation;

/**
 * 
 * @author c
 *
 */
public interface SampleMenuOperationService 
{               
    public List<SampleMenuOperation> queryListPage(SampleMenuOperation record, Page page);

    public int insert(SampleMenuOperation record);

    public int delete(String[] keys);

    public int update(SampleMenuOperation record);

    public SampleMenuOperation selectByKey(String key);
}
