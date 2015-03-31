package net.sebinson.sample.web.mis.persistence.mapper;

import java.util.List;

import net.sebinson.sample.web.mis.persistence.domain.SampleMenu;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuExample;

import org.apache.ibatis.annotations.Param;

public interface SampleMenuMapper {
    int countByExample(SampleMenuExample example);

    int deleteByExample(SampleMenuExample example);

    int deleteByPrimaryKey(String menuId);

    int insert(SampleMenu record);

    int insertSelective(SampleMenu record);

    List<SampleMenu> selectByExample(SampleMenuExample example);

    SampleMenu selectByPrimaryKey(String menuId);

    int updateByExampleSelective(@Param("record") SampleMenu record, @Param("example") SampleMenuExample example);

    int updateByExample(@Param("record") SampleMenu record, @Param("example") SampleMenuExample example);

    int updateByPrimaryKeySelective(SampleMenu record);

    int updateByPrimaryKey(SampleMenu record);
}