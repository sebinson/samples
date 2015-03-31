package net.sebinson.sample.web.mis.persistence.mapper;

import java.util.List;

import net.sebinson.sample.web.mis.persistence.domain.SampleRoleMenuExample;
import net.sebinson.sample.web.mis.persistence.domain.SampleRoleMenuKey;

import org.apache.ibatis.annotations.Param;

public interface SampleRoleMenuMapper {
    int countByExample(SampleRoleMenuExample example);

    int deleteByExample(SampleRoleMenuExample example);

    int deleteByPrimaryKey(SampleRoleMenuKey key);

    int insert(SampleRoleMenuKey record);

    int insertSelective(SampleRoleMenuKey record);

    List<SampleRoleMenuKey> selectByExample(SampleRoleMenuExample example);

    int updateByExampleSelective(@Param("record") SampleRoleMenuKey record, @Param("example") SampleRoleMenuExample example);

    int updateByExample(@Param("record") SampleRoleMenuKey record, @Param("example") SampleRoleMenuExample example);
}