package com.my120.market.card.dao;

import java.util.List;

import com.my120.market.model.CardTypeModel;

/**
 * 卡类型DAO
 * 
 * @author cai.yc
 * 
 *         2012-11-19
 */
public interface ICardTypeDAO {
	/**
	 * 列出所有的卡类型信息
	 * 
	 * @return
	 * @author cai.yc
	 */
	public List<CardTypeModel> listAllCardType();

	/**
	 * 根据keyCode查找卡类型名
	 * 
	 * @param typeCode
	 *            四位类型服务级别代码,如0102表示类型为01,服务级别为02<br>
	 * @return
	 * 
	 * @author cai.yc
	 */
	public String getCardTypeNameByKeyCode(String typeCode);
}
