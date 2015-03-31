package com.sample.web.mis.persistence.mapper;

import com.sample.web.mis.persistence.domain.SampleMenuOperation;
import com.sample.web.mis.persistence.domain.SampleMenuOperationExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SampleMenuOperationMapper {
    int countByExample(SampleMenuOperationExample example);

    int deleteByExample(SampleMenuOperationExample example);

    int deleteByPrimaryKey(String operId);

    int insert(SampleMenuOperation record);

    int insertSelective(SampleMenuOperation record);

    List<SampleMenuOperation> selectByExample(SampleMenuOperationExample example);

    SampleMenuOperation selectByPrimaryKey(String operId);

    int updateByExampleSelective(@Param("record") SampleMenuOperation record, @Param("example") SampleMenuOperationExample example);

    int updateByExample(@Param("record") SampleMenuOperation record, @Param("example") SampleMenuOperationExample example);

    int updateByPrimaryKeySelective(SampleMenuOperation record);

    int updateByPrimaryKey(SampleMenuOperation record);
}