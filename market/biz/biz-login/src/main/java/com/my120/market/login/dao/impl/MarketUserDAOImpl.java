package com.my120.market.login.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.my120.market.login.dao.IMarketUserDAO;

/**
 * MarketUserDAO实现类
 * 
 * @author cai.yc
 * 
 *         2012-11-28
 */
public class MarketUserDAOImpl extends SqlMapClientDaoSupport implements IMarketUserDAO {
    @Override
    public String getPasswordByUserName(String userName) {
        return (String) this.getSqlMapClientTemplate().queryForObject("getPasswordByUserName", userName);
    }

    @Override
    public int updatePasswordByUserName(String userName, String oldPassWord, String newPassWord) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);
        param.put("oldPassWord", oldPassWord);
        param.put("newPassWord", newPassWord);
        return this.getSqlMapClientTemplate().update("updatePasswordByUserName", param);
    }
}
