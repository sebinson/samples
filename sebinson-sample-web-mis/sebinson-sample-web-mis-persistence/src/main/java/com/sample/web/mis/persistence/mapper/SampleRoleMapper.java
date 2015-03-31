package com.sample.web.mis.persistence.mapper;

import com.sample.web.mis.persistence.domain.SampleRole;
import com.sample.web.mis.persistence.domain.SampleRoleExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SampleRoleMapper {
    int countByExample(SampleRoleExample example);

    int deleteByExample(SampleRoleExample example);

    int deleteByPrimaryKey(String roleId);

    int insert(SampleRole record);

    int insertSelective(SampleRole record);

    List<SampleRole> selectByExample(SampleRoleExample example);

    SampleRole selectByPrimaryKey(String roleId);

    int updateByExampleSelective(@Param("record") SampleRole record, @Param("example") SampleRoleExample example);

    int updateByExample(@Param("record") SampleRole record, @Param("example") SampleRoleExample example);

    int updateByPrimaryKeySelective(SampleRole record);

    int updateByPrimaryKey(SampleRole record);
}