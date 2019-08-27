package com.bcc.test.trialBack.domain;

import com.bcc.test.trial.domain.DiyTrialReport;

public class DiyTrialReportSupper extends DiyTrialReport {

	private static final long serialVersionUID = 1L;
//  申请人昵称
	private String   nickname;
//	申请人电话
	private String   mobile;
	
//	hbase订单id
	private Long hbaseOrderId;
	
//	hbase用户id
	private Long hbaseUserId;
	
//	订单状态
	private String orderStatus; 
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getHbaseOrderId() {
		return hbaseOrderId;
	}
	public void setHbaseOrderId(Long hbaseOrderId) {
		this.hbaseOrderId = hbaseOrderId;
	}
	
	public Long getHbaseUserId() {
		return hbaseUserId;
	}
	public void setHbaseUserId(Long hbaseUserId) {
		this.hbaseUserId = hbaseUserId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
