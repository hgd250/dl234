package com.my120.market.utils;

import org.testng.annotations.Test;

import com.my120.test.BaseTest;

/**
 * @author cai.yc
 * 
 *         2012-11-28
 */
@Test(groups = { "cas-style", "cai.yc" })
public class MD5UtilTest extends BaseTest {

    @Test
    public void testEncrypt() {
        want.string(MD5Util.encrypt("123456")).isEqualTo("E10ADC3949BA59ABBE56E057F20F883E");
    }

}
