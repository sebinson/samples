package com.sample.web.mis.persistence.mapper;

import com.sample.web.mis.persistence.domain.SampleUser;
import com.sample.web.mis.persistence.domain.SampleUserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SampleUserMapper {
    int countByExample(SampleUserExample example);

    int deleteByExample(SampleUserExample example);

    int deleteByPrimaryKey(String userId);

    int insert(SampleUser record);

    int insertSelective(SampleUser record);

    List<SampleUser> selectByExample(SampleUserExample example);

    SampleUser selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") SampleUser record, @Param("example") SampleUserExample example);

    int updateByExample(@Param("record") SampleUser record, @Param("example") SampleUserExample example);

    int updateByPrimaryKeySelective(SampleUser record);

    int updateByPrimaryKey(SampleUser record);
}