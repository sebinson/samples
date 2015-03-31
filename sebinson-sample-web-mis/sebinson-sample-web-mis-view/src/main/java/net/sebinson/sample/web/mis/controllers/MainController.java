package net.sebinson.sample.web.mis.controllers;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sebinson.sample.web.common.BaseController;
import net.sebinson.sample.web.common.annotations.Auth;
import net.sebinson.sample.web.common.beans.ResponseJson;
import net.sebinson.sample.web.common.tools.SessionUtil;
import net.sebinson.sample.web.mis.common.tools.AuthUtil;
import net.sebinson.sample.web.mis.common.tools.MenuTreeHelper;
import net.sebinson.sample.web.mis.common.tools.UriUtil;
import net.sebinson.sample.web.mis.constants.WebConstants;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenu;
import net.sebinson.sample.web.mis.persistence.domain.SampleMenuOperation;
import net.sebinson.sample.web.mis.persistence.domain.SampleUser;
import net.sebinson.sample.web.mis.service.auth.VerificationAuthorizationService;
import net.sebinson.sample.web.mis.service.base.SampleUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.common.tools.Md5Util;
import com.sample.common.tools.StringUtil;

@Controller
public class MainController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired(required = false)
    SampleUserService sampleUserService;

    @Autowired(required = false)
    VerificationAuthorizationService vaService;

    @RequestMapping("/login")
    public @Auth void login(ModelMap modelMap) {
        modelMap.addAttribute("account", "gooagoo");
    }

    @RequestMapping(value = "/verify")
    public @Auth String verify(String account, String pwd, String verifyCode, HttpServletRequest request) {

        // Object vcode = SessionUtil.getAttribute(request,
        // WebConstants.SESSION_VERIFY_CODE);
        SessionUtil.removeAttribute(request, WebConstants.SESSION_VERIFY_CODE);// 清除验证码，确保验证码只能用一次

        if (StringUtil.isEmpty(account) || StringUtil.isEmpty(pwd)) {
            // return ResponseJson.body(false, "账号与密码都不能为空！");
        }
        /*
         * if (StringUtils.isEmpty(verifyCode) &&
         * !verifyCode.toLowerCase().equals(vcode)) {
         * logger.info("用户{}，登录失败，验证码输入错误！", account); return
         * ResponseJson.body(false, "验证码输入错误！"); }
         */
        Md5Util md5 = new Md5Util();
        SampleUser user = this.vaService.queryLogin(account, md5.encrypt(pwd));
        if (user == null) {
            logger.info("用户{}，登录失败，账号或密码输入有误！", account);
            // return ResponseJson.body(false, "账号或密码输入错误！");
        }
        SessionUtil.setAttribute(request, WebConstants.SESSION_SAMPLE_USER, user);
        logger.info("用户{}，登录成功！", account);
        return "forward:/main";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public @Auth String logout(HttpServletRequest request) {
        SessionUtil.removeAttribute(request, WebConstants.SESSION_SAMPLE_USER);
        return "forward:/login";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public @Auth(verifyLogin = true) void main(ModelMap modelMap, HttpServletRequest request) {

        SampleUser user = null;
        Object obj = SessionUtil.getAttribute(request, WebConstants.SESSION_SAMPLE_USER);
        if (obj != null && obj instanceof SampleUser) {
            user = (SampleUser) obj;
        }
        List<SampleMenu> rootMenus = null;
        List<SampleMenu> childMenus = null;
        List<SampleMenuOperation> childOpers = null;
        MenuTreeHelper helper = new MenuTreeHelper();
        if (user != null && true) {
            rootMenus = vaService.findRootMenuByUserId(user.getUserId());// 根节点
            childMenus = vaService.findChildMenuByUserId(user.getUserId());// 子节点
            childOpers = vaService.findMenuOperByUserId(user.getUserId());// 按钮操作
            helper.setRootMenus(rootMenus);
            helper.setChildMenus(childMenus);
            buildAccess(childMenus, childOpers, request); // 构建必要的数据
        }
        modelMap.addAttribute("user", SessionUtil.getAttribute(request, WebConstants.SESSION_SAMPLE_USER));
        modelMap.addAttribute("menuList", helper.getTreeNode());
    }

    @Auth(verifyLogin = true)
    @RequestMapping(value = "/operAuth", method = RequestMethod.GET)
    public @ResponseBody ModelMap operAuth(String url, HttpServletRequest request) throws MalformedURLException {
        List<String> actionTypes = new ArrayList<String>();
        ModelMap modelMap = new ModelMap("allType", true);
        String menuUri = StringUtil.removeStart(UriUtil.getReqUri(url), request.getContextPath());
        // 获取权限按钮
        actionTypes = AuthUtil.getMemuOperList(request, menuUri.trim());
        modelMap.put("allType", false);
        modelMap.put("types", actionTypes);
        return modelMap;
    }

    @Auth(verifyLogin = true)
    @RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
    public @ResponseBody ResponseJson modifyPwd(String oldPwd, String newPwd, HttpServletRequest request) {
        SampleUser user = AuthUtil.getUser(request);
        if (user == null) {
            return new ResponseJson(false, "对不起,登录超时.");
        }
        SampleUser bean = this.sampleUserService.selectByKey(user.getUserId());
        if (bean != null && bean.getUserId() == null) {
            return new ResponseJson(false, "对不起,用户不存在.");
        }
        if (StringUtil.isEmpty(newPwd)) {
            return new ResponseJson(false, "密码不能为空.");
        }
        if (bean != null && !bean.getPassword().equals(new Md5Util().encrypt(oldPwd))) {
            return new ResponseJson(false, "旧密码输入不匹配.");
        }
        bean.setPassword(new Md5Util().encrypt(newPwd));
        if (this.sampleUserService.update(bean) > 0) {
            SessionUtil.removeAttribute(request, WebConstants.SESSION_SAMPLE_USER);
            return new ResponseJson(true, "密码修改成功");
        } else {
            return new ResponseJson(false, "密码修改失败，系统错误！");
        }
    }

    private void buildAccess(List<SampleMenu> childMenus, List<SampleMenuOperation> childOpers,
            HttpServletRequest request) {

        // 能够访问的url列表
        List<String> accessUrls = new ArrayList<String>();
        // 菜单对应的按钮
        Map<String, List<String>> accessOperMap = new HashMap<String, List<String>>();
        for (SampleMenu menu : childMenus) {
            // 判断URL是否为空
            if (!StringUtil.isEmpty(menu.getMenuUri())) {
                List<String> operTypes = new ArrayList<String>();
                for (SampleMenuOperation oper : childOpers) {
                    if (menu.getMenuId().equals(oper.getMenuId())) {
                        operTypes.add(oper.getOperType());
                        UriUtil.joinOperAccessUrls(menu.getMenuUri(), oper.getOperActions(), accessUrls);
                    }
                }
                accessOperMap.put(menu.getMenuUri(), operTypes);
                UriUtil.joinOperAccessUrls(menu.getMenuUri(), menu.getMenuActions(), accessUrls);
                accessUrls.add(menu.getMenuUri());
            }
        }
        SessionUtil.setAttribute(request, WebConstants.SESSION_ACCESS_URLS, accessUrls);
        SessionUtil.setAttribute(request, WebConstants.SESSION_ACCESS_OPER_MAP, accessOperMap);
    }
}
