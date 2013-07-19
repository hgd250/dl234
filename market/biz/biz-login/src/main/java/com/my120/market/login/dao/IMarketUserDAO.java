package com.my120.market.login.dao;

/**
 * 市场用户DAO接口
 * 
 * @author cai.yc
 * 
 *         2012-11-27
 */
public interface IMarketUserDAO {

    /**
     * 根据用户名获取密码
     * 
     * @author cai.yc 2012-11-27
     * @param userName
     *            用户名
     * @return 密码
     */
    public String getPasswordByUserName(String userName);

    /**
     * 修改密码
     * 
     * @author cai.yc 2012-11-27
     * @param userName
     *            用户名
     * @param oldPassWord
     *            原密码
     * @param newPassWord
     *            新密码
     * @return
     */
    public int updatePasswordByUserName(String userName, String oldPassWord, String newPassWord);
}
