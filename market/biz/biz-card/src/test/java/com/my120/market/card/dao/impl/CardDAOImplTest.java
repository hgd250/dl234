package com.my120.market.card.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtester.unitils.dbfit.DbFit;
import org.testng.annotations.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.my120.commons.ResultList;
import com.my120.market.card.dao.ICardDAO;
import com.my120.market.model.CardQueryModel;
import com.my120.test.BaseDAOTest;

/**
 * @author cai.yc
 * 
 *         2012-11-21
 */
public class CardDAOImplTest extends BaseDAOTest {

    @SpringBeanByType
    ICardDAO cardDAO;

    @Test(groups = { "cas-style", "cai.yc" })
    @DbFit(when = { "CardDAOImpl-GetMaxCardNumber.when.wiki" })
    public void testGetMaxCardNumber() {
        final CardQueryModel query = new CardQueryModel();
        query.setCityCode("210200");
        query.setTypeCode("0101");
        String num = cardDAO.getMaxCardNumber(query);
        want.string(num).isEqualTo("2102000101000009");
    }

    @Test(groups = { "cas-style", "cai.yc" })
    @DbFit(when = { "CardDAOImpl-InsertCard.when.wiki" }, then = { "CardDAOImpl-InsertCard.then.wiki" })
    public void testInsertCard() {
        final CardQueryModel query = new CardQueryModel();
        query.setNumber("2102000101000001");
        query.setCdkey("123456");
        query.setType(1);
        query.setStatus(0);
        query.setCityCode("210200");
        query.setTypeCode("0101");
        cardDAO.insertCard(query);
    }

    @Test(groups = { "cas-style", "cai.yc" })
    @DbFit(when = { "CardDAOImpl-BatchInsert.when.wiki" }, then = { "CardDAOImpl-BatchInsert.then.wiki" })
    public void testBatchInsert() throws SQLException {
        final List<String> cardNumberList = new ArrayList<String>();
        cardNumberList.add("2102000101000001");
        final CardQueryModel query = new CardQueryModel();
        query.setType(1);
        cardDAO.batchInsert(cardNumberList, query);
    }

    @Test(groups = { "cas-style", "shitou" })
    @DbFit(when = { "CardDAOImpl-selectCardInfoList.when.wiki" })
    public void testSelectCardInfoList() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("start", 0);
        paramMap.put("end", 20);
        ResultList<CardQueryModel> modelResultList = cardDAO.selectCardInfoList(paramMap);
        want.number(modelResultList.getSize()).isEqualTo(1);
    }

    @Test(groups = { "cas-style", "shitou" })
    @DbFit(when = { "CardDAOImpl-selectCardInfoByCardNumber.when.wiki" })
    public void testSelectCardInfoByCardNumber() {
        final CardQueryModel query = new CardQueryModel();
        query.setNumber("2102000101900082");
        CardQueryModel modelResult = cardDAO.selectCardInfoByCardNumber(query);
        want.string(modelResult.getNumber()).isEqualTo("2102000101900082");
    }

    @Test(groups = { "cas-style", "shitou" })
    @DbFit(when = { "CardDAOImpl-updateCardInfo.when.wiki" }, then = { "CardDAOImpl-updateCardInfo.then.wiki" })
    public void testUpdateCard() {
        final CardQueryModel query = new CardQueryModel();
        query.setId(96);
        query.setNumber("2102000101900082");
        query.setStatus(CardQueryModel.CARD_STATUS_5);
        query.setDestroyedTime(1352882780L);
        cardDAO.updateCard(query);
    }

    @Test(groups = { "cas-style", "shitou" })
    @DbFit(when = { "CardDAOImpl-batchCancelCard.when.wiki" })
    public void testBatchCancelCard() {
        List<CardQueryModel> list = new ArrayList<CardQueryModel>();
        String[] strNumbers = { "2101000102000006", "2101000102000007", "2101000102000008", "2101000102000013",
                "2101000102000014", "2101000102000015" };
        long time = System.currentTimeMillis();
        for (String number : strNumbers) {
            CardQueryModel card = new CardQueryModel();
            card.setNumber(number);
            card.setStatus(CardQueryModel.CARD_STATUS_5);
            card.setDestroyedTime(time);
            card.setGmtModify(time);
            list.add(card);
        }

        try {
            List<CardQueryModel> listCards = cardDAO.batchCancelCard(list);
            want.bool(listCards.size() == 2).is(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
