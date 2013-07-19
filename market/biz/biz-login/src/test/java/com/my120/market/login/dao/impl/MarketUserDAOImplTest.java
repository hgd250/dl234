package com.my120.market.login.dao.impl;

import org.jtester.unitils.dbfit.DbFit;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.my120.market.login.dao.IMarketUserDAO;
import com.my120.test.BaseDAOTest;

/**
 * 单元测试
 * 
 * @author cai.yc
 * 
 *         2012-11-28
 */
public class MarketUserDAOImplTest extends BaseDAOTest {

    @SpringBeanByType
    IMarketUserDAO marketUserDAO;

    @Test(groups = { "cas-style", "cai.yc" })
    @DbFit(when = { "CardDAOImpl-GetPasswordByUserName.when.wiki" })
    public void testGetPasswordByUserName() {
        final String userName = "cai.yc";
        want.string(marketUserDAO.getPasswordByUserName(userName)).isEqualTo("123456");
    }

    @Test(groups = { "cas-style", "cai.yc" })
    @DbFit(when = { "CardDAOImpl-UpdatePasswordByUserName.when.wiki" }, then = { "CardDAOImpl-UpdatePasswordByUserName.then.wiki" })
    public void testUpdatePasswordByUserName() {
        final String userName = "cai.yc";
        marketUserDAO.updatePasswordByUserName(userName, "123456", "654321");
    }

}
