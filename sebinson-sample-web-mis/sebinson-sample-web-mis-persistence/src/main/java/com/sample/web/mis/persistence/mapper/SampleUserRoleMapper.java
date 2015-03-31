package com.sample.web.mis.persistence.mapper;

import com.sample.web.mis.persistence.domain.SampleUserRole;
import com.sample.web.mis.persistence.domain.SampleUserRoleExample;
import com.sample.web.mis.persistence.domain.SampleUserRoleKey;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SampleUserRoleMapper {
    int countByExample(SampleUserRoleExample example);

    int deleteByExample(SampleUserRoleExample example);

    int deleteByPrimaryKey(SampleUserRoleKey key);

    int insert(SampleUserRole record);

    int insertSelective(SampleUserRole record);

    List<SampleUserRole> selectByExample(SampleUserRoleExample example);

    SampleUserRole selectByPrimaryKey(SampleUserRoleKey key);

    int updateByExampleSelective(@Param("record") SampleUserRole record, @Param("example") SampleUserRoleExample example);

    int updateByExample(@Param("record") SampleUserRole record, @Param("example") SampleUserRoleExample example);

    int updateByPrimaryKeySelective(SampleUserRole record);

    int updateByPrimaryKey(SampleUserRole record);
}