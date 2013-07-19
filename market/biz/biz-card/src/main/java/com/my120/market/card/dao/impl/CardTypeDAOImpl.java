package com.my120.market.card.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.my120.market.card.dao.ICardTypeDAO;
import com.my120.market.model.CardTypeModel;

public class CardTypeDAOImpl extends SqlMapClientDaoSupport implements ICardTypeDAO {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.my120.market.card.dao.ICardTypeDAO#listAllCardType()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CardTypeModel> listAllCardType() {
		return this.getSqlMapClientTemplate().queryForList("listAllCardType");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.my120.market.card.dao.ICardTypeDAO#getCardTypeNameByKeyCode(java.
	 * lang.String)
	 */
	@Override
	public String getCardTypeNameByKeyCode(String keyCode) {
		if (keyCode == null || "".equals(keyCode)) {
			return null;
		}
		return (String) this.getSqlMapClientTemplate().queryForObject("getCardTypeNameByKeyCode", keyCode);
	}

}
