package com.sample.web.mis.persistence.mapper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sample.web.mis.persistence.domain.SampleMenu;
import com.sample.web.mis.persistence.domain.SampleMenuOperation;

public interface AuthorizationQueryDao {

    List<SampleMenu> queryRootMenuByUserId(@Param("userId") String userId);

    List<SampleMenu> queryChildMenuByUserId(@Param("userId") String userId);

    List<SampleMenuOperation> queryMenuOperByUserId(@Param("userId") String userId);
}
