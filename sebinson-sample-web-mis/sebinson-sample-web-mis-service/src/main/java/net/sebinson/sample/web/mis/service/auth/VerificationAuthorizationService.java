package net.sebinson.sample.web.mis.service.auth;

import java.util.List;

import net.sebinson.sample.web.mis.persistence.domain.SampleMenu;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperation;
import net.sebinson.sample.web.mis.persistence.domain.SampleUser;

/**
 * 用戶授权验证服务
 * 
 * @author C
 */
public interface VerificationAuthorizationService {

    /**
     * 用户登入验证
     * 
     * @param account
     *            账号
     * @param encrypt
     *            经MD5加密后的密码字符
     * @return 若验证成功返回用户信息，否则返回null
     */
    public SampleUser queryLogin(String account, String encrypt);

    /**
     * 根据用户ID获取用户的授权资源下的頂級菜单
     */
    public List<SampleMenu> findRootMenuByUserId(String userId);

    /**
     * 根据用户ID获取用户授权资源下的所有頂級菜单的子菜单
     */
    public List<SampleMenu> findChildMenuByUserId(String userId);

    /**
     * 根据用户ID获取用户的授权资源下所有菜单的操作
     */
    public List<SampleMenuOperation> findMenuOperByUserId(String userId);
}
