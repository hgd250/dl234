package com.my120.market.login.bo;

/**
 * 登录BO接口
 * 
 * @author cai.yc
 * 
 *         2012-11-27
 */
public interface ILoginBO {

    /**
     * 校验证用户名密码是否正确
     * 
     * @author cai.yc 2012-11-27
     * @param userName
     *            用户名
     * @param password
     *            密码
     * @return true 成功，false失败
     */
    public boolean checkNamePassword(String userName, String password);

    /**
     * 修改密码
     * 
     * @author cai.yc 2012-11-27
     * @param userName
     *            用户名
     * @param oldPassWord
     *            旧密码
     * @param newPassWord
     *            新密码
     * @return 返回1，修改成功,其他失败
     */
    public int modifyPassword(String userName, String oldPassWord, String newPassWord);
}
