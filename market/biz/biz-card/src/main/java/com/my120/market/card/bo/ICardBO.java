package com.my120.market.card.bo;

import java.util.Map;

import com.my120.market.model.CardQueryModel;

/**
 * CardBO接口
 * 
 * @author cai.yc
 * 
 *         2012-11-15
 */
public interface ICardBO {

	/**
	 * 生成卡
	 * 
	 * @param query
	 *            query.createCount : 生成数量,为必填项目<br>
	 *            query.cityCode : 六位城市代码,如201000<br>
	 *            query.typeCode : 四位类型服务级别代码,如0102表示类型为01,服务级别为02<br>
	 * 
	 * @return 成功卡号数量
	 * @author cai.yc 2012-11-20
	 */
	public int createCard(CardQueryModel query);

	/**
	 * 查询卡余量
	 * 
	 * @param query
	 *            query.cityCode : 六位城市代码,如201000<br>
	 *            query.typeCode : 四位类型服务级别代码,如0102表示类型为01,服务级别为02<br>
	 * @return 剩余卡号数量
	 * @author cai.yc 2012-11-20
	 */
	public int cardIdLeft(CardQueryModel query);

    /**
     * 分页查询健康卡信息
     * @param query 检索条件
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:05:26
     * 
    */
	public Map<String, Object> selectCardInfoList(CardQueryModel query) throws Exception;

    /**
     * 根据卡号查询健康卡信息
     * @param query 卡号
     * @return
     * @author 王磊<wangl1990@sina.cn>
     * @since 2012-11-21上午10:05:26
     * 
    */
	public Map<String, Object> getCardInfoByCardNumber(CardQueryModel query);

	/**
	 * 根据excel文件地址，注销卡
	 * 
	 * @param path
	 * @return
	 * @author 韩友军<hanyoujun@gmail.com>
	 * @since 2012-11-20上午9:48:38
	 * 
	 */
	Map<String, Object> cancelCard(String path) throws Exception;
}
