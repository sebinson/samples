package com.gooagoo.sample.service.base;

import java.util.List;

import com.gooagoo.sample.common.bean.pagination.Page;
import com.gooagoo.sample.persistence.domain.${fileid_cap_first};

/**
 * 
 * @author c
 *
 */
public interface ${fileid_cap_first}Service 
{               
    public List<${fileid_cap_first}> queryListPage(${fileid_cap_first} record, Page page);

    public int insert(${fileid_cap_first} record);

    public int delete(String[] keys);

    public int update(${fileid_cap_first} record);

    public ${fileid_cap_first} selectByKey(String key);
}
