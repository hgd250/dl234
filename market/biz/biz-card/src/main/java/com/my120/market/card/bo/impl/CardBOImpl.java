package com.my120.market.card.bo.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.my120.commons.ResultList;
import com.my120.commons.util.ToolKitUtils;
import com.my120.market.card.bo.ICardBO;
import com.my120.market.card.dao.ICardDAO;
import com.my120.market.card.utils.CardNumUtil;
import com.my120.market.model.CardQueryModel;
import com.my120.market.utils.ExcelPOIUtil;

/**
 * @author cai.yc
 * 
 *         2012-11-20
 */
public class CardBOImpl implements ICardBO {

    // 最大生成数量
    public static final int MAX_CARD_NUMBER = 999999;
    // 批量插入Size
    public static final int BATCH_INSERT_SIZE = 10000;
    @Autowired
    ICardDAO cardDAO;

    /*
     * (non-Javadoc)
     * 
     * @seecom.my120.market.card.bo.ICardBO#createCard(com.my120.market.model.
     * CardQueryModel)
     */
    @Override
    public int createCard(CardQueryModel query) {
        // 1 取得当前类型最大卡号
        String maxId = cardDAO.getMaxCardNumber(query);
        // 2 确定起始卡号
        int startNum = 0;
        if (maxId != null && !"".equals(maxId)) {
            startNum = Integer.parseInt(maxId.substring(10));
        }
        String cardNum = "";
        // 3 卡号前缀
        String prefix = query.getCardPrefix();
        int i = 0;
        // 4 设置卡号生成时间
        query.setGmtCreate(System.currentTimeMillis());
        // 5设置卡类型ID
        query.setType(Integer.parseInt(query.getTypeCode().substring(0, 2)));
        // 6 生成卡
        List<String> cardNumberList = new ArrayList<String>();
        int successCount = 0;
        for (i = 0; i < query.getCreateCount() & startNum < MAX_CARD_NUMBER; i++) {
            // 6.1 新卡序列号(后六位)
            startNum++;
            // 6.2 新卡号（全部16位）
            cardNum = prefix + CardNumUtil.getNumberString(startNum);
            // 6.3 缓存卡号
            cardNumberList.add(cardNum);
            // 6.4 批量插入数据库
            if (BATCH_INSERT_SIZE == cardNumberList.size()) {
                try {
                    successCount += cardDAO.batchInsert(cardNumberList, query);
                } catch (Exception e) {
                    return successCount;
                }
                cardNumberList.clear();
            }
        }
        // 7 将剩余卡号批量插入数据库
        if (cardNumberList.size() > 0) {
            try {
                successCount += cardDAO.batchInsert(cardNumberList, query);
            } catch (Exception e) {
                return successCount;
            }
            cardNumberList.clear();
        }
        // 8生成成功数量
        return successCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @seecom.my120.market.card.bo.ICardBO#cardIdLeft(com.my120.market.model.
     * CardQueryModel)
     */
    @Override
    public int cardIdLeft(CardQueryModel query) {
        // 1 取得当前类型最大卡号
        String maxId = cardDAO.getMaxCardNumber(query);
        int startNum = 0;
        if (maxId != null && !"".equals(maxId)) {
            startNum = Integer.parseInt(maxId.substring(10));
        }
        // 2 剩余卡号数量
        return MAX_CARD_NUMBER - startNum;
    }

    public ICardDAO getCardDAO() {
        return cardDAO;
    }

    public void setCardDAO(ICardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    /**
     * 分页查询健康卡信息
     * 
     * @param query
     *            检索条件
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:05:26
     * 
     */
    @Override
    public Map<String, Object> selectCardInfoList(CardQueryModel query) throws Exception {

        String number = query.getNumber();
        Integer type = query.getType();
        String cityCode = query.getCityCode();
        Integer status = query.getStatus();
        String createUserId = query.getCreateUserId();
        String startTimeStr = query.getStartTime();
        String endTimeStr = query.getEndTime();
        String pageindexStr = query.getPageindex();
        String pagesizeStr = query.getPagesize();

        if (null != number) {
            number = number.trim();
        }
        Long startTime = null;
        Long endTime = null;
        if (null != startTimeStr && !"".equals(startTimeStr)) {
            startTime = convertStringToLong(startTimeStr, true);
        }
        if (null != endTimeStr && !"".equals(endTimeStr)) {
            endTime = convertStringToLong(endTimeStr, false);
        }

        Integer pageindex = null;
        Integer pagesize = null;
        pageindex = Integer.parseInt(pageindexStr);
        if (pageindex < 1) {
            pageindex = 1;
        }
        pagesize = Integer.parseInt(pagesizeStr);
        if (pagesize < 1) {
            pagesize = 20;
        }

        // 参数集合
        Map<String, Object> param = new HashMap<String, Object>();
        // 搜索的起始与终止
        param.put("number", number);
        param.put("type", type);
        param.put("cityCode", cityCode);
        param.put("status", status);
        param.put("createUserId", createUserId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("start", (pageindex - 1) * pagesize);
        param.put("end", pagesize);
        param.put("isLimit", query.getIsLimit());
        // added by cai
        param.put("gmtCreate", query.getGmtCreate());

        ResultList<CardQueryModel> resultList = cardDAO.selectCardInfoList(param);
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (CardQueryModel cardQueryModel : resultList.getResult()) {
            Map<String, String> resultMap = new HashMap<String, String>();
            resultMap.put("id", cardQueryModel.getId().toString());
            resultMap.put("number", cardQueryModel.getNumber());
            resultMap.put("cardTypeName", cardQueryModel.getCardTypeName());
            resultMap.put("cardPrice", cardQueryModel.getCardPrice() != null ? cardQueryModel.getCardPrice().toString()
                    : "");
            resultMap.put("cityName", getCityName(cardQueryModel.getNumber()));
            resultMap.put("status", getStatusName(cardQueryModel.getStatus()));
            resultMap.put(
                    "gmtCreate",
                    cardQueryModel.getGmtCreate() == null ? "" : ToolKitUtils.format(
                            new Date(cardQueryModel.getGmtCreate()), "yyyy-MM-dd HH:mm"));
            resultMap.put("createIp", cardQueryModel.getCreateIp());
            resultMap.put("cdkey", cardQueryModel.getCdkey());
            resultMap.put("createUserId", cardQueryModel.getCreateUserId());
            data.add(resultMap);
        }
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", data);
        modelMap.put("count", resultList.getSize().toString());
        modelMap.put("sumcount", resultList.getSize().toString());
        return modelMap;
    }

    /**
     * 根据卡号查询健康卡信息
     * 
     * @param query
     *            卡号
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:05:26
     * 
     */
    public Map<String, Object> getCardInfoByCardNumber(CardQueryModel query) {
        CardQueryModel cardInfo = cardDAO.selectCardInfoByCardNumber(query);
        // 封装结果集
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("number", cardInfo.getNumber());
        resultMap.put("cardTypeName", cardInfo.getCardTypeName());
        resultMap.put("cardPrice", cardInfo.getCardPrice() != null ? cardInfo.getCardPrice().toString() : "");
        resultMap.put("cityName", getCityName(cardInfo.getNumber()));
        resultMap.put("status", getStatusName(cardInfo.getStatus()));
        resultMap.put("statusFlag", cardInfo.getStatus() + "");

        resultMap.put(
                "activatedTime",
                cardInfo.getActivatedTime() == null ? "" : ToolKitUtils.format(new Date(cardInfo.getActivatedTime()),
                        "yyyy-MM-dd HH:mm"));
        resultMap.put(
                "freezedTime",
                cardInfo.getFreezedTime() == null ? "" : ToolKitUtils.format(new Date(cardInfo.getFreezedTime()),
                        "yyyy-MM-dd HH:mm"));
        resultMap.put(
                "destroyedTime",
                cardInfo.getDestroyedTime() == null ? "" : ToolKitUtils.format(new Date(cardInfo.getDestroyedTime()),
                        "yyyy-MM-dd HH:mm"));
        resultMap.put(
                "rejectedTime",
                cardInfo.getRejectedTime() == null ? "" : ToolKitUtils.format(new Date(cardInfo.getRejectedTime()),
                        "yyyy-MM-dd HH:mm"));
        resultMap.put(
                "serviceEndTime",
                cardInfo.getServiceEndTime() == null ? "" : ToolKitUtils.format(new Date(cardInfo.getServiceEndTime()),
                        "yyyy-MM-dd HH:mm"));

        resultMap.put(
                "gmtCreate",
                cardInfo.getGmtCreate() == null ? "" : ToolKitUtils.format(new Date(cardInfo.getGmtCreate()),
                        "yyyy-MM-dd HH:mm"));
        resultMap.put("intro", cardInfo.getIntro() == null ? "" : cardInfo.getIntro());
        resultMap.put("userName", cardInfo.getUserName() == null ? "" : cardInfo.getUserName());
        resultMap.put("patientName", cardInfo.getPatientName() == null ? "" : cardInfo.getPatientName());
        resultMap.put("sex", cardInfo.getSex() == null ? "" : cardInfo.getSex());
        resultMap.put("age", cardInfo.getAge() == null ? "" : cardInfo.getAge());
        resultMap.put("idNumber", cardInfo.getIdNumber() == null ? "" : cardInfo.getIdNumber());
        resultMap.put("phone", cardInfo.getPhone() == null ? "" : cardInfo.getPhone());
        resultMap.put("email", cardInfo.getEmail() == null ? " " : cardInfo.getEmail());
        resultMap.put(
                "piLocationDetail",
                getLocationDetail(cardInfo.getProvinceName(), cardInfo.getCityName(), cardInfo.getDistrictName(),
                        cardInfo.getTownName(), cardInfo.getVillageName(), cardInfo.getPiLocationDetail()));
        resultMap.put("isShowUserInfo", cardInfo.getStatus() == 1 ? "1" : "0");

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("data", resultMap);
        return modelMap;
    }

    /**
     * 拼接详细地址
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:07:40
     * 
     */
    public String getLocationDetail(String rProvinceName, String rCityName, String rDistrictName, String rTownName,
            String rVillageName, String piLocationDetail) {
        StringBuffer locationStr = new StringBuffer();
        if (null != rProvinceName && !rProvinceName.isEmpty()) {
            locationStr.append(rProvinceName + " ");
        }
        if (null != rCityName && !rCityName.isEmpty()) {
            locationStr.append(rCityName + " ");
        }
        if (null != rDistrictName && !rDistrictName.isEmpty()) {
            locationStr.append(rDistrictName + " ");
        }
        if (null != rTownName && !rTownName.isEmpty()) {
            locationStr.append(rTownName + " ");
        }
        if (null != rVillageName && !rVillageName.isEmpty()) {
            locationStr.append(rVillageName + " ");
        }
        if (null != piLocationDetail && !piLocationDetail.isEmpty()) {
            locationStr.append(piLocationDetail);
        }
        return locationStr.toString();
    }

    /**
     * 根据卡号返回城市名
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:08:44
     * 
     */
    public String getCityName(String number) {
        if (null != number && !"".equals(number) && number.length() > 6) {
            String cityCode = number.substring(0, 6);
            if ("210100".equals(cityCode)) {
                return "沈阳市";
            } else if ("210200".equals(cityCode)) {
                return "大连市";
            }
        }
        return "";
    }

    /**
     * 根据卡状态返回对应的状态值
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:09:07
     * 
     */
    public String getStatusName(int statusCode) {
        String statusName = "";
        switch (statusCode) {
            case 0:
                statusName = "未激活";
                break;
            case 1:
                statusName = "已激活";
                break;
            case 2:
                statusName = "已冻结";
                break;
            case 3:
                statusName = "已到期";
                break;
            case 4:
                statusName = "已退卡";
                break;
            case 5:
                statusName = "已注销";
                break;
        }
        return statusName;
    }

    /**
     * 转换特定日期时间戳
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:09:48
     * 
     */
    public long convertStringToLong(String time, boolean day) throws ParseException {
        // if (day == true) {
        // time = time + " 00:00:00";
        // } else {
        // time = time + " 23:59:59";
        // }
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time);
        return date.getTime();
    }

    @Override
    public Map<String, Object> cancelCard(String path) throws Exception {
        Map<String, Object> mapResult = new HashMap<String, Object>();
        String[] strsColumn = { "number", "cdkey", "cardTypeName", "cardPrice", "cityName", "status", "gmtCreate",
                "createIp" };
        List<Map<String, Object>> listNumber = ExcelPOIUtil.readExcel(path, strsColumn, true);

        List<CardQueryModel> listCards = getListCard(listNumber);

        // 批量注销
        List<CardQueryModel> errorCards = cardDAO.batchCancelCard(listCards);

        int intErrorCount = errorCards.size();// 失败数量
        int intSuccessCount = listCards.size() - intErrorCount;// 成功数量
        StringBuffer sbErrorNumber = new StringBuffer();
        for (CardQueryModel card : errorCards) {
            sbErrorNumber.append(card.getNumber()).append(",");
        }

        mapResult.put("successCount", intSuccessCount);
        mapResult.put("errorCount", intErrorCount);
        mapResult.put("errorNumber", sbErrorNumber.toString());

        return mapResult;
    }

    /**
     * 循环从Excel中取出的卡号并创建卡列表
     * 
     * @param listNumber
     * @return List<CardQueryModel>List<CardQueryModel>
     * @author 韩友军<hanyoujun@gmail.com>
     * @since 2012-11-28下午1:01:12
     * 
     */
    private List<CardQueryModel> getListCard(List<Map<String, Object>> listNumber) {
        List<CardQueryModel> listCards = new ArrayList<CardQueryModel>();
        long time = System.currentTimeMillis();
        for (Map<String, Object> map : listNumber) {
            String strNumber = map.get("number").toString();
            CardQueryModel card = new CardQueryModel();
            card.setNumber(strNumber);
            card.setStatus(CardQueryModel.CARD_STATUS_5);
            card.setDestroyedTime(time);
            card.setGmtModify(time);
            listCards.add(card);
        }
        return listCards;
    }
}
