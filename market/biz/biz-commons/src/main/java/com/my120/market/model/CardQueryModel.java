package com.my120.market.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.my120.commons.AbstractEntity;

/**
 * @author cai.yc
 * 
 * 
 *         2012-11-14
 */
public class CardQueryModel extends AbstractEntity implements Serializable{
	
	private static final long serialVersionUID = -1284205724505325288L;
	
	/** 卡的状态:未激活 */
	public static final Integer CARD_STATUS_0 = 0;
	/** 卡的状态:已激活 */
	public static final Integer CARD_STATUS_1 = 1;
	/** 卡的状态:冻结 */
	public static final Integer CARD_STATUS_2 = 2;
	/** 卡的状态:服务到期 */
	public static final Integer CARD_STATUS_3 = 3;
	/** 卡的状态:已退卡 */
	public static final Integer CARD_STATUS_4 = 4;
	/** 卡的状态:注销 */
	public static final Integer CARD_STATUS_5 = 5;
	
	private Integer id;
	private String number;
	private String cdkey;
	private Integer type;
	private Integer status;
	private String startTime;
	private String endTime;
	private String createUserId;
	private String createIp;
	private String modifyIp;
	private Integer createCount;

	private String cityCode;
	private String typeCode;
	private Long gmtCreate;
	private Long gmtModify;
	
	
	private String cardTypeName;
	private BigDecimal cardPrice;
	private String intro;
	
	private String userName;
	
	private String patientName;
	private String sex;
	private String age;
	private String idNumber;
	private String phone;
	private String email;
	private String piLocationDetail;
	
	private String provinceName;
	private String cityName;
	private String districtName;
	private String townName;
	private String villageName;
	
	private String pageindex;
	private String pagesize;
	private String isLimit;

	// 服务到期时间
	private Long serviceEndTime;
	// 激活时间
	private Long activatedTime;
	// 冻结时间
	private Long freezedTime;
	// 注销时间
	private Long destroyedTime;
	// 退卡时间
	private Long rejectedTime;
	
	public String getCardPrefix() {
		return this.cityCode + this.typeCode;
	}

	public Long getServiceEndTime() {
		return serviceEndTime;
	}


	public void setServiceEndTime(Long serviceEndTime) {
		this.serviceEndTime = serviceEndTime;
	}


	public String getIntro() {
		return intro;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPiLocationDetail() {
		return piLocationDetail;
	}

	public void setPiLocationDetail(String piLocationDetail) {
		this.piLocationDetail = piLocationDetail;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}


	public Long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Long getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Long gmtModify) {
		this.gmtModify = gmtModify;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getCreateCount() {
		return createCount;
	}

	public void setCreateCount(Integer createCount) {
		this.createCount = createCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCdkey() {
		return cdkey;
	}

	public void setCdkey(String cdkey) {
		this.cdkey = cdkey;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getModifyIp() {
		return modifyIp;
	}

	public void setModifyIp(String modifyIp) {
		this.modifyIp = modifyIp;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public BigDecimal getCardPrice() {
		return cardPrice;
	}

	public void setCardPrice(BigDecimal cardPrice) {
		this.cardPrice = cardPrice;
	}

	public String getPageindex() {
		return pageindex;
	}

	public void setPageindex(String pageindex) {
		this.pageindex = pageindex;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getIsLimit() {
		return isLimit;
	}

	public void setIsLimit(String isLimit) {
		this.isLimit = isLimit;
	}


	public Long getActivatedTime() {
		return activatedTime;
	}


	public void setActivatedTime(Long activatedTime) {
		this.activatedTime = activatedTime;
	}


	public Long getFreezedTime() {
		return freezedTime;
	}


	public void setFreezedTime(Long freezedTime) {
		this.freezedTime = freezedTime;
	}


	public Long getDestroyedTime() {
		return destroyedTime;
	}


	public void setDestroyedTime(Long destroyedTime) {
		this.destroyedTime = destroyedTime;
	}


	public Long getRejectedTime() {
		return rejectedTime;
	}


	public void setRejectedTime(Long rejectedTime) {
		this.rejectedTime = rejectedTime;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CardQueryModel)) {
            return false;
        }
        CardQueryModel cardQueryModel = (CardQueryModel) obj;
        return cardQueryModel.getNumber().equals(this.number);
    }

    @Override
    public int hashCode() {
        return 3 * (number == null ? "".hashCode() : number.hashCode());
    }
	
}
