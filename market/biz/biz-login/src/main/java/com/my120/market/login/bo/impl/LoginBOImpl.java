package com.my120.market.login.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.my120.market.login.bo.ILoginBO;
import com.my120.market.login.dao.IMarketUserDAO;
import com.my120.market.utils.MD5Util;

/**
 * 登录BO实现类
 * 
 * @author cai.yc
 * 
 *         2012-11-27
 */
public class LoginBOImpl implements ILoginBO {
    @Autowired
    private IMarketUserDAO marketUserDAO;

    @Override
    public boolean checkNamePassword(String userName, String password) {
        String pwd = marketUserDAO.getPasswordByUserName(userName);
        return MD5Util.encrypt(password).equals(pwd);
    }

    @Override
    public int modifyPassword(String userName, String oldPassWord, String newPassWord) {
        return marketUserDAO.updatePasswordByUserName(userName, MD5Util.encrypt(oldPassWord), MD5Util
                .encrypt(newPassWord));
    }

    public IMarketUserDAO getMarketUserDAO() {
        return marketUserDAO;
    }

    public void setMarketUserDAO(IMarketUserDAO marketUserDAO) {
        this.marketUserDAO = marketUserDAO;
    }

}
