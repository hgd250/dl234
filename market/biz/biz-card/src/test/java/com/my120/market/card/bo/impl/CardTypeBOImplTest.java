package com.my120.market.card.bo.impl;

import mockit.Mocked;

import org.jtester.unitils.inject.Inject;
import org.testng.annotations.Test;

import com.my120.market.card.bo.ICardTypeBO;
import com.my120.market.card.dao.ICardTypeDAO;
import com.my120.test.BaseBOTest;

/**
 * @author Ali 2012-11-21
 */
public class CardTypeBOImplTest extends BaseBOTest {

    ICardTypeBO cardTypeBO = new CardTypeBOImpl();

    @Mocked
    @Inject(targets = "cardTypeBO", properties = "cardTypeDAO")
    ICardTypeDAO cardTypeDAO;

    /**
     * @author Ali 2012-11-21
     */
    @Test(groups = { "cas-style", "Ali" })
    public void testListAllCardType() {
        new NonStrictExpectations() {
            {
                cardTypeDAO.listAllCardType();
                returns(result);
            }
        };
        cardTypeBO.listAllCardType();
    }

    /**
     * @author Ali 2012-11-21
     */
    @Test(groups = { "cas-style", "Ali" })
    public void testGetCardTypeName() {
        final String keyCode = "0101";
        final String result = "21012121121";
        new NonStrictExpectations() {
            {
                cardTypeDAO.getCardTypeNameByKeyCode(keyCode);
                returns(result);
            }
        };
        cardTypeBO.getCardTypeName(keyCode);
    }

}
