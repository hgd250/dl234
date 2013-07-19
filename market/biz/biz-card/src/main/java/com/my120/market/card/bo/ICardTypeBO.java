package com.my120.market.card.bo;

import java.util.List;

import com.my120.market.model.CardTypeModel;

/**
 * @author cai.yc
 * 
 *         2012-11-20
 */
public interface ICardTypeBO {
	/**
	 * 列了所有的卡类型
	 * 
	 * 
	 * @return
	 * @author cai.yc 2012-11-20
	 */
	public List<CardTypeModel> listAllCardType();

	/**
	 * 根据keycode获取卡类型名
	 * 
	 * @param keyCode
	 *            卡的keycode
	 * @return 卡类型名称
	 * @author cai.yc 2012-11-20
	 */
	public String getCardTypeName(String keyCode);
}
