package com.bcc.test.trial.domain;

import com.bcc.test.common.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * 试用申请用户信息Bean
 * ClassName: DiyTrialUser 
 * @Description: 试用活动
 * @author lily
 * @date 2017-05-09
 */
public class DiyTrialApplication implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//申请记录id
	private Long id; 
	
	//试用活动id
	private Long trialId; 	
	
	//申请用户id
	private Long userId; 	
	
	//申请信息-姓名
	private String name;
	
	//申请信息-性别
	private Integer sex; 
	
	//申请信息-手机号
	private String mobile; 
	
	//申请信息-申请理由
	private String reason; 
	
    //申请理由所占权重	
	private Integer reasonWeight;
	
	//是否中签	Y/N
	private String isBallot; 
	
	//是否公示	Y/N
	private String isPublic;
	
	//公示时间
	private Date publicTime; 
	private String publicTimeStr; 
	
	//付款开始时间
	private Date payStartTime;
	private String payStartTimeStr;
	
	//付款结束时间
	private Date payEndTime;
	private String payEndTimeStr;
	
	//订单主键id
	private Long orderId;
	
	//收货地址id
	private Long addressId;
	
	//收货地址
	private  String  address;
	
	//快递公司名称
	private String expressCompany;
	
	//快递公司名称
	private String expressNo;
	
	//创建时间
	private Date createTime;
	
	//是否显示申请理由
	private String isShowReason;
	
	//是否删除
	private String isDelete;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTrialId() {
		return trialId;
	}

	public void setTrialId(Long trialId) {
		this.trialId = trialId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getReasonWeight() {
		return reasonWeight;
	}

	public void setReasonWeight(Integer reasonWeight) {
		this.reasonWeight = reasonWeight;
	}

	public String getIsBallot() {
		return isBallot;
	}

	public void setIsBallot(String isBallot) {
		this.isBallot = isBallot;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public Date getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(Date payStartTime) {
		this.payStartTime = payStartTime;
	}

	public String getPayStartTimeStr() {
		if(this.payStartTime != null){
			this.payStartTimeStr = DateUtil.formatDateToString(this.payStartTime);
		}
		return payStartTimeStr;
	}

	public void setPayStartTimeStr(String payStartTimeStr) {
		this.payStartTimeStr = payStartTimeStr;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	public String getPayEndTimeStr() {
		if(this.payEndTime != null){
			this.payEndTimeStr = DateUtil.formatDateToString(this.payEndTime);
		}
		return payEndTimeStr;
	}

	public void setPayEndTimeStr(String payEndTimeStr) {
		this.payEndTimeStr = payEndTimeStr;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPublicTimeStr() {
		if(this.publicTime != null){
			this.publicTimeStr = DateUtil.formatDateToString(this.publicTime);
		}
		return publicTimeStr;
	}

	public void setPublicTimeStr(String publicTimeStr) {
		this.publicTimeStr = publicTimeStr;
	}

	public String getIsShowReason() {
		return isShowReason;
	}

	public void setIsShowReason(String isShowReason) {
		this.isShowReason = isShowReason;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}
