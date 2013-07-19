package com.my120.market.card.dao.impl;

import java.util.List;

import org.jtester.unitils.dbfit.DbFit;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.my120.market.card.dao.ICardTypeDAO;
import com.my120.market.model.CardTypeModel;
import com.my120.test.BaseDAOTest;

/**
 * @author Ali 2012-11-21
 */
public class CardTypeDAOImplTest extends BaseDAOTest {
	@SpringBeanByType
	ICardTypeDAO cardTypeDAO;

	@Test(groups = { "cas-style", "Ali" })
	@DbFit(when = { "CardTypeDAOImpl-listAllCardType.when.wiki" })
	public void testListAllCardType() {
		List<CardTypeModel> list = cardTypeDAO.listAllCardType();
		want.number(3).isEqualTo(list.size());
		want.string("type1").isEqualTo(list.get(0).getName());
		want.string("type2").isEqualTo(list.get(1).getName());
		want.string("type3").isEqualTo(list.get(2).getName());
	}

	@Test(groups = { "cas-style", "Ali" })
	@DbFit(when = { "CardTypeDAOImpl-GetCardTypeNameByKeyCode.when.wiki" })
	public void testGetCardTypeNameByKeyCode() {
		String typeName = cardTypeDAO.getCardTypeNameByKeyCode("0101");
		want.string(typeName).isEqualTo("abc");
	}

}
