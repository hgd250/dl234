package com.my120.market.card.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.my120.market.card.bo.ICardTypeBO;
import com.my120.market.card.dao.ICardTypeDAO;
import com.my120.market.model.CardTypeModel;

/**
 * 卡类型BO实现类
 * 
 * @author cai 2012-11-14
 */
public class CardTypeBOImpl implements ICardTypeBO {

	@Autowired
	private ICardTypeDAO cardTypeDAO;

	@Override
	public List<CardTypeModel> listAllCardType() {
		return cardTypeDAO.listAllCardType();
	}

	@Override
	public String getCardTypeName(String keyCode) {
		return cardTypeDAO.getCardTypeNameByKeyCode(keyCode);
	}

	public ICardTypeDAO getCardTypeDAO() {
		return cardTypeDAO;
	}

	public void setCardTypeDAO(ICardTypeDAO cardTypeDAO) {
		this.cardTypeDAO = cardTypeDAO;
	}

}
