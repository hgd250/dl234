package com.my120.market.card.bo.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Mock;
import mockit.Mocked;
import mockit.Mockit;

import org.jtester.unitils.inject.Inject;
import org.testng.annotations.Test;

import com.my120.commons.ResultList;
import com.my120.market.card.bo.ICardBO;
import com.my120.market.card.dao.ICardDAO;
import com.my120.market.card.utils.CardNumUtil;
import com.my120.market.model.CardQueryModel;
import com.my120.market.utils.ExcelPOIUtil;
import com.my120.test.BaseBOTest;

/**
 * 
 * @author cai.yc
 * 
 *         2012-11-21
 */
public class CardBOImplTest extends BaseBOTest {

    private ICardBO cardBo = new CardBOImpl();

    @Mocked
    CardNumUtil util;

    @Mocked
    @Inject(targets = "cardBo", properties = "cardDAO")
    ICardDAO cardDAO;

    @Test(groups = { "cas-style", "cai.yc" })
    public void testCreateCard() throws SQLException {
        final CardQueryModel query = new CardQueryModel();
        query.setCityCode("210000");
        query.setTypeCode("0101");
        query.setCreateCount(3);
        final String maxId = "2100000101900002";
        final List<String> list = new ArrayList<String>();
        list.add("2100000101900003");
        list.add("2100000101900004");
        list.add("2100000101900005");
        new NonStrictExpectations() {
            {
                cardDAO.getMaxCardNumber(query);
                returns(maxId);
            }
            {
                util.getNumberString(900003);
                returns("900003");
            }
            {
                util.getNumberString(900004);
                returns("900004");
            }
            {
                util.getNumberString(900005);
                returns("900005");
            }
            {
                cardDAO.batchInsert(the.object().lenientEq(list).wanted(ArrayList.class), the.object().lenientEq(query)
                        .wanted(CardQueryModel.class));
                returns(3);
            }
        };
        want.number(cardBo.createCard(query)).isEqualTo(3);
    }

    @Test(groups = { "cas-style", "cai.yc" })
    public void testCardIdLeft() {
        final CardQueryModel query = new CardQueryModel();
        query.setCityCode("210000");
        query.setTypeCode("0101");
        query.setCreateCount(10);
        final String maxId = "2102000101900002";
        new NonStrictExpectations() {
            {
                cardDAO.getMaxCardNumber(query);
                returns(maxId);
            }
        };
        want.number(cardBo.cardIdLeft(query)).isEqualTo(999999 - 900002);
    }

    @Test(groups = { "cas-style", "shitou" })
    public void testGetCityName() {
        final String number_001 = "2102000101000001";
        final String number_002 = "2101000101000001";
        CardBOImpl cardBoImpl = new CardBOImpl();
        want.string(cardBoImpl.getCityName(number_001)).isEqualTo("大连市");
        want.string(cardBoImpl.getCityName(number_002)).isEqualTo("沈阳市");
    }

    @Test(groups = { "cas-style", "shitou" })
    public void testGetStatusName() {
        CardBOImpl cardBoImpl = new CardBOImpl();
        want.string(cardBoImpl.getStatusName(0)).isEqualTo("未激活");
        want.string(cardBoImpl.getStatusName(1)).isEqualTo("已激活");
        want.string(cardBoImpl.getStatusName(2)).isEqualTo("已冻结");
        want.string(cardBoImpl.getStatusName(3)).isEqualTo("已到期");
        want.string(cardBoImpl.getStatusName(4)).isEqualTo("已退卡");
        want.string(cardBoImpl.getStatusName(5)).isEqualTo("已注销");
    }

    @Test(groups = { "cas-style", "shitou" })
    public void testGetLocationDetail() {
        CardBOImpl cardBoImpl = new CardBOImpl();
        want.string(cardBoImpl.getLocationDetail("辽宁", "大连市", "高新区", "百合", "益新街", "是的")).isEqualTo(
                "辽宁 大连市 高新区 百合 益新街 是的");
    }

    @Test(groups = { "cas-style", "shitou" })
    public void testConvertStringToLong() throws ParseException {
        CardBOImpl cardBoImpl = new CardBOImpl();
        want.number(cardBoImpl.convertStringToLong("2012-11-19 12:12:12", false)).notNull();
        want.number(cardBoImpl.convertStringToLong("2012-11-19 12:12:12", true)).notNull();
    }

    @Test(groups = { "cas-style", "han" })
    public void testCancelCard() throws Exception {
        final String path = "d:/1353294054255.xls";
        String[] numbers = { "2102000101900002", "2102000101900003", "2102000101900004", "2102000101900005",
                "2102000101900006", "", "sdfsdfsdfdsf" };
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (String n : numbers) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("number", n);
            list.add(map);
        }

        final CardQueryModel objQuery = new CardQueryModel();
        objQuery.setNumber("2102000101900002");
        objQuery.setStatus(CardQueryModel.CARD_STATUS_0);

        final List<CardQueryModel> listCards = new ArrayList<CardQueryModel>();
        final List<CardQueryModel> errorListCards = new ArrayList<CardQueryModel>();
        long time = System.currentTimeMillis();
        for (String strNumber : numbers) {
            CardQueryModel card = new CardQueryModel();
            card.setNumber(strNumber);
            card.setStatus(CardQueryModel.CARD_STATUS_5);
            card.setDestroyedTime(time);
            card.setGmtModify(time);
            listCards.add(card);
            if (!strNumber.equals(objQuery.getNumber())) {
                errorListCards.add(card);
            }
        }

        Mockit.setUpMock(ExcelPOIUtil.class, new Object() {
            @Mock
            public List<Map<String, Object>> readExcel(String filePath, String[] columnKey, boolean isExistTitle) {
                return list;
            }
        });

        new NonStrictExpectations() {
            {
                cardDAO.batchCancelCard(listCards);
                returns(errorListCards);
            }
        };

        new Expectations(cardBo) {
            {
                this.invoke(cardBo, "getListCard", list);
                result = listCards;
            }
        };

        Map<String, Object> mapResult = cardBo.cancelCard(path);
        Integer successCount = (Integer) mapResult.get("successCount");
        Integer errorCount = (Integer) mapResult.get("errorCount");
        want.bool(successCount == 1).is(true);
        want.bool(errorCount == 6).is(true);
    }

    @Test(groups = { "cas-style", "shitou" })
    public void testGetCardInfoByCardNumber() {
        final CardQueryModel query = new CardQueryModel();
        query.setNumber("2102000101000001");
        final CardQueryModel model = new CardQueryModel();
        model.setNumber("2102000101000001");
        model.setCardTypeName("百元卡（500块）");
        model.setCardPrice(new BigDecimal(200.00));
        model.setCityName("大连市");
        model.setStatus(1);
        model.setGmtCreate(1352857885012L);
        model.setIntro("");
        model.setUserName("hzliqilin01");
        model.setPatientName("其林");
        model.setSex("男");
        model.setAge("20");
        model.setIdNumber("210282198803164369");
        model.setPhone("15140611215");
        model.setEmail("");
        model.setPiLocationDetail("哪");
        model.setProvinceName("辽宁省");
        model.setDistrictName("普兰店市");
        model.setTownName("城子坦镇");
        model.setVillageName("碧流河村");
        new NonStrictExpectations() {
            {
                cardDAO.selectCardInfoByCardNumber(query);
                returns(model);
            }
        };
        // 封装结果集
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("number", "2102000101000001");
        resultMap.put("cardTypeName", "百元卡（500块）");
        resultMap.put("cardPrice", "200.00");
        resultMap.put("cityName", "大连市");
        resultMap.put("status", "已激活");
        resultMap.put("gmtCreate", "2012-11-14 09:51");
        resultMap.put("intro", "");
        resultMap.put("userName", "hzliqilin01");
        resultMap.put("patientName", "其林");
        resultMap.put("sex", "男");
        resultMap.put("age", "20");
        resultMap.put("idNumber", "210282198803164369");
        resultMap.put("phone", "15140611215");
        resultMap.put("email", "");
        resultMap.put("piLocationDetail", "辽宁省 大连市 普兰店市 城子坦镇 碧流河村 哪");
        resultMap.put("isShowUserInfo", "1");

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", resultMap);
        want.map(cardBo.getCardInfoByCardNumber(query)).hasKeys("data");
    }

    @Test(groups = { "cas-style", "shitou" })
    public void testSelectCardInfoList() throws Exception {
        List<CardQueryModel> list = new ArrayList<CardQueryModel>();
        final CardQueryModel query = new CardQueryModel();
        query.setNumber("2102000101000001");
        query.setType(1);
        query.setCityCode("210200");
        query.setStatus(1);
        query.setStartTime(null);
        query.setEndTime(null);
        query.setPageindex("0");
        query.setPagesize("20");
        // 参数集合
        final Map<String, Object> param = new HashMap<String, Object>();
        // 搜索的起始与终止
        param.put("number", "2102000101000001");
        param.put("type", 1);
        param.put("cityCode", "210200");
        param.put("status", 1);
        param.put("createUserId", null);
        param.put("startTime", null);
        param.put("endTime", null);
        param.put("start", 0);
        param.put("end", 20);
        param.put("isLimit", null);
        // added by cai
        param.put("gmtCreate", query.getGmtCreate());

        final ResultList<CardQueryModel> resultList = new ResultList<CardQueryModel>();
        CardQueryModel model = new CardQueryModel();
        model.setId(112);
        model.setNumber("2102000101000001");
        model.setCardTypeName("百元卡（500块）");
        model.setCardPrice(new BigDecimal(200.00));
        model.setCityName("大连市");
        model.setStatus(1);
        model.setGmtCreate(1352857885012L);
        model.setIntro("");
        model.setUserName("hzliqilin01");
        model.setPatientName("其林");
        model.setSex("男");
        model.setAge("20");
        model.setIdNumber("210282198803164369");
        model.setPhone("15140611215");
        model.setEmail("");
        model.setPiLocationDetail("哪");
        model.setProvinceName("辽宁省");
        model.setDistrictName("普兰店市");
        model.setTownName("城子坦镇");
        model.setVillageName("碧流河村");
        list.add(model);
        resultList.setSize(1);
        resultList.setResult(list.toArray(new CardQueryModel[] {}));
        new NonStrictExpectations() {
            {
                cardDAO.selectCardInfoList(the.object().lenientEq(param).wanted(param.getClass()));
                returns(resultList);
            }
        };
        want.map(cardBo.selectCardInfoList(query)).hasKeys("data");
    }

}
