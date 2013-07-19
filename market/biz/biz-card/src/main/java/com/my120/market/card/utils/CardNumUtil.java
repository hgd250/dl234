package com.my120.market.card.utils;

import java.util.Random;

/**
 * 卡号生成工具类
 * 
 * @author cai.yc
 * 
 *         2012-11-15
 */
public class CardNumUtil {
	/**
	 * 不足六位前面补0
	 * 
	 * @param num
	 * @return
	 */
	public static String getNumberString(int num) {
		if (num < 10)
			return "00000" + num;
		if (num < 100)
			return "0000" + num;
		if (num < 1000)
			return "000" + num;
		if (num < 10000)
			return "00" + num;
		if (num < 100000)
			return "0" + num;
		return "" + num;
	}

	/**
	 * 生成激活码(第一位不为0)
	 * 
	 * @return
	 */
	public static String getKeyCode() {
		Random random = new Random();
		String keyCode = "";
		int temp = 0;
		for (int i = 0; i < 6; i++) {
			temp = random.nextInt(10);
			while (i == 0 && temp == 0) {
				temp = random.nextInt(10);
			}
			keyCode += temp;
		}
		return keyCode;

	}

}
