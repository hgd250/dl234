package com.my120.market.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my120.market.login.bo.ILoginBO;

/**
 * 登录逻辑类
 * 
 * @author cai.yc
 * 
 *         2012-11-24
 */
@Controller
public class LoginController {

    // sessionId
    public static final String SESSION_USER_INFO_KEY = "MY120_MARKET_LOGIN_USER_INFO";

    @Autowired
    private ILoginBO loginBO;

    /**
     * 登录
     * 
     * @author cai.yc 2012-11-24
     * @param model
     * @param userName
     *            用户名
     * @param password
     *            密码
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/login.html")
    public String login(ModelMap model, String userName, String password, HttpServletRequest req,
            HttpServletResponse res) {
        if (loginBO.checkNamePassword(userName, password)) {
            HttpSession session = req.getSession();
            session.setAttribute(SESSION_USER_INFO_KEY, userName);
            return "forward:toCreateCard.html";
        } else {
            return "login/login_fail.html";
        }
    }

    /**
     * 退出
     * 
     * @author cai.yc 2012-11-24
     * @param model
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/logout.html")
    public String logout(ModelMap model, HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        session.removeAttribute(SESSION_USER_INFO_KEY);
        return "redirect:toCreateCard.html";
    }

    /**
     * 修改密码页
     * 
     * @author cai.yc 2012-11-24
     * @param model
     * @return
     */
    @RequestMapping("/toModifyPassword.html")
    public String toMmodifyPassword(ModelMap model) {
        return "login/to_modify_pwd.html";
    }

    /**
     * 修改密码
     * 
     * @author cai.yc 2012-11-24
     * @param model
     * @param oldPassWord
     *            旧密码
     * @param newPassWord
     *            新密码
     * @param req
     * @return
     */
    @RequestMapping("/modifyPassword.html")
    public String modifyPassword(ModelMap model, String oldPassWord, String newPassWord, HttpServletRequest req) {
        if (newPassWord == null || newPassWord.length() == 0) {
            model.put("c", "A0001");
        } else {
            String userName = (String) req.getSession().getAttribute(SESSION_USER_INFO_KEY);
            int result = loginBO.modifyPassword(userName, oldPassWord, newPassWord);
            if (result == 1) {
                model.put("c", "A0000");
            } else {
                model.put("c", "A0002");
            }
        }
        return "login/do_modify_pwd.html";
    }

    public ILoginBO getLoginBO() {
        return loginBO;
    }

    public void setLoginBO(ILoginBO loginBO) {
        this.loginBO = loginBO;
    }

}
