package com.sample.web.mis.service.auth.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.web.mis.persistence.domain.SampleMenu;
import com.sample.web.mis.persistence.domain.SampleMenuOperation;
import com.sample.web.mis.persistence.domain.SampleUser;
import com.sample.web.mis.persistence.domain.SampleUserExample;
import com.sample.web.mis.persistence.domain.SampleUserExample.Criteria;
import com.sample.web.mis.persistence.mapper.SampleUserMapper;
import com.sample.web.mis.persistence.mapper.dao.AuthorizationQueryDao;
import com.sample.web.mis.service.auth.VerificationAuthorizationService;

/**
 * 用戶授权验证服务的实现
 * 
 * @author C
 */
@Service
public class VerificationAuthorizationServiceImpl implements VerificationAuthorizationService {

    @Autowired
    SampleUserMapper userMapper;

    @Autowired
    AuthorizationQueryDao authQueryDao;

    @Override
    public SampleUser queryLogin(String account, String encrypt) {
        SampleUser user = null;
        if (account != null && encrypt != null) {
            SampleUserExample example = new SampleUserExample();
            Criteria criteria = example.createCriteria();
            criteria.andUserNameEqualTo(account);
            criteria.andPasswordEqualTo(encrypt);
            List<SampleUser> userList = this.userMapper.selectByExample(example);
            if (userList != null && userList.size() > 0) {
                user = userList.get(0);
            }
        }
        return user;
    }

    @Override
    public List<SampleMenu> findRootMenuByUserId(String userId) {

        return authQueryDao.queryRootMenuByUserId(userId);
    }

    @Override
    public List<SampleMenu> findChildMenuByUserId(String userId) {

        return authQueryDao.queryChildMenuByUserId(userId);
    }

    @Override
    public List<SampleMenuOperation> findMenuOperByUserId(String userId) {

        return authQueryDao.queryMenuOperByUserId(userId);
    }
}
