package net.sebinson.sample.web.mis.persistence.mapper.dao;

import java.util.List;

import net.sebinson.sample.web.mis.persistence.domain.SampleMenu;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperation;

import org.apache.ibatis.annotations.Param;

public interface AuthorizationQueryDao {

    List<SampleMenu> queryRootMenuByUserId(@Param("userId") String userId);

    List<SampleMenu> queryChildMenuByUserId(@Param("userId") String userId);

    List<SampleMenuOperation> queryMenuOperByUserId(@Param("userId") String userId);
}
