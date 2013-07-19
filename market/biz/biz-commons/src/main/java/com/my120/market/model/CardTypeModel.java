package com.my120.market.model;

import java.io.Serializable;

import com.my120.commons.AbstractEntity;

/**
 * @author cai.yc
 * 
 *         2012-11-14
 */
public class CardTypeModel extends AbstractEntity implements Serializable{

	private static final long serialVersionUID = 3926074189355284260L;
	// ID
	private Integer id;
	private String name;
	private String service;
	private Integer month;
	private Integer price;
	private Double charge;
	private Integer isRefund;
	private Integer isRenew;
	private Integer isUpgrade;
	private Integer isPresent;
	private String intro;
	private String typeCode;


	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Double getCharge() {
		return charge;
	}

	public void setCharge(Double charge) {
		this.charge = charge;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	public Integer getIsRenew() {
		return isRenew;
	}

	public void setIsRenew(Integer isRenew) {
		this.isRenew = isRenew;
	}

	public Integer getIsUpgrade() {
		return isUpgrade;
	}

	public void setIsUpgrade(Integer isUpgrade) {
		this.isUpgrade = isUpgrade;
	}

	public Integer getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
