package com.my120.market.card.utils;

import org.testng.annotations.Test;

import com.my120.test.BaseBOTest;

public class CardNumUtilTest extends BaseBOTest {

	@Test(groups = { "cas-style", "cai" })
	public void testDoSomething_flag_true() {
		final int num1 = 2;
		want.string(CardNumUtil.getNumberString(num1)).isEqualTo("000002");
		final int num2 = 22;
		want.string(CardNumUtil.getNumberString(num2)).isEqualTo("000022");
		final int num3 = 222;
		want.string(CardNumUtil.getNumberString(num3)).isEqualTo("000222");
		final int num4 = 2222;
		want.string(CardNumUtil.getNumberString(num4)).isEqualTo("002222");
		final int num5 = 22222;
		want.string(CardNumUtil.getNumberString(num5)).isEqualTo("022222");
		final int num6 = 222222;
		want.string(CardNumUtil.getNumberString(num6)).isEqualTo("222222");
	}

	@Test(groups = { "cas-style", "cai" })
	public void testGetKeyCode() {
		for (int i = 0; i < 100; i++) {
			String result = CardNumUtil.getKeyCode();
			want.string(result).regular("[1-9][0-9]{5}");
		}
	}

}
