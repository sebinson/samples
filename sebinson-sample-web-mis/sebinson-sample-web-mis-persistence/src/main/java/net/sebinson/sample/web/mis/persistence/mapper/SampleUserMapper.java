package net.sebinson.sample.web.mis.persistence.mapper;

import java.util.List;
import net.sebinson.sample.web.mis.persistence.domain.SampleUser;
import net.sebinson.sample.web.mis.persistence.domain.SampleUserExample;
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