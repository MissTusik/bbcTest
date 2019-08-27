package com.bcc.test.trialBack.domain;

import com.bcc.test.trial.domain.DiyTrialApplication;

import java.util.List;
import java.util.Map;

public class DiyTrialApplicationSupper extends DiyTrialApplication {

	private static final long serialVersionUID = 1L;
//  申请人昵称
	private String   nickname;
//	申请人头像
	private String   headImg;
	
//	申请用户状态    0无    1正常    2失效
	private Integer  status;
	
//  订单状态	
	private String  orderStatus;
	
//	报告状态
	private Integer  reportStatus;
	
//	试用申请是否支付押金
	private  String  isChargeDeposit;
	
//	物流公司下拉列表
	
	private List<Map<String,Object>> expressCompanyList;
	
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getIsChargeDeposit() {
		return isChargeDeposit;
	}
	public void setIsChargeDeposit(String isChargeDeposit) {
		this.isChargeDeposit = isChargeDeposit;
	}

	public List<Map<String, Object>> getExpressCompanyList() {
		return expressCompanyList;
	}
	public void setExpressCompanyList(List<Map<String, Object>> expressCompanyList) {
		this.expressCompanyList = expressCompanyList;
	}


}
