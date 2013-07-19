package com.my120.market.card.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.my120.commons.ResultList;
import com.my120.market.model.CardQueryModel;

/**
 * 卡片管理DAO
 * 
 * @author cai.yc 2012-11-20
 */
public interface ICardDAO {

    /**
     * 插入卡
     * 
     * @param query
     *            对应字段插入数据库,cd_status不必输入，SQL中强置为0
     * @author cai.yc 2012-11-20
     * 
     */
    public void insertCard(CardQueryModel query);

    /**
     * 批量生成卡信息
     * 
     * @author cai.yc 2012-11-22
     * @param cardNumberList
     *            卡号列表
     * @param card
     *            卡的模板信息<br>
     *            参数有:<br>
     * 
     *            card.type：卡类型<br>
     *            card.gmtCreate：发卡时间,可选<br>
     *            [card.createIp]:发卡IP<br>
     *            [card.createUserId]:发卡用户ID,可选<br>
     * <br>
     *            (cd_cdkey) 卡是否有效 1<br>
     *            (cd_status)是否状态 0
     * @return 成功生成数量
     * @throws SQLException
     */
    int batchInsert(List<String> cardNumberList, CardQueryModel card) throws SQLException;

    /**
     * 获取相应类型的最大卡号
     * 
     * @param query
     *            query.cityCode : 六位城市代码,如201000<br>
     *            query.typeCode : 四位类型服务级别代码,如0102表示类型为01,服务级别为02<br>
     * @return 最大卡号
     * @author cai.yc 2012-11-20
     */
    public String getMaxCardNumber(CardQueryModel query);

    /**
     * 分页查询健康卡信息集合
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:10:29
     * 
     */
    public ResultList<CardQueryModel> selectCardInfoList(Map<String, Object> paramMap);

    /**
     * 根据卡号查询卡信息
     * 
     * @param
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:10:55
     * 
     */
    public CardQueryModel selectCardInfoByCardNumber(CardQueryModel query);

    /**
     * 修改卡信息
     * 
     * @param query
     * @return
     * @author 韩友军<hanyoujun@gmail.com>
     * @since 2012-11-20上午9:47:59
     * 
     */
    int updateCard(CardQueryModel query);

    /**
     * 批量注销健康卡，并返回注销失败的健康卡列表
     * 
     * @param list
     * @return
     * @author 韩友军<hanyoujun@gmail.com>
     * @since 2012-11-22上午9:41:14
     * 
     */
    List<CardQueryModel> batchCancelCard(List<CardQueryModel> list) throws Exception;

}
