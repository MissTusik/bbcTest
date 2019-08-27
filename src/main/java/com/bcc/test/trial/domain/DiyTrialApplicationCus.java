package com.bcc.test.trial.domain;

import java.util.Date;

public class DiyTrialApplicationCus extends  DiyTrialApplication{

	private static final long serialVersionUID = 1L;
	
	private  String  pcCover;			//	pc版产品图
	
	private  String  mCover;			//	m版产品图
	
	private  String  productName;		//	产品名称
	
	private  String  title;				//	产品标题
	
	private  Double  price;				//	产品价格
	
	private  Double  depositPrice;		//	押金
	
	private  Long  	 productId;			//	产品Id
	
	private  Long  	 packageId;			//	包装Id
	
	private  Date    startTime;			//	活动开始时间
	private  String  startTimeStr;
	
	private  Date  	 endTime;			//	活动结束时间
	private  String  endTimeStr;
	
	private  Integer trialNum;			//	产品试用数量
	
	private  String  isChargeDeposit;	//	是否支付押金
	
	private  String  orderStatus;		//	订单状态
	
	private  Long    reportId;			//试用报告Id

	private Integer  reportStatus;		//	报告状态
	
	private Integer  status;			//申请所处各种状态
	
	private Long     hbaseOrderId;		//订单hbaseId
	
	private String    payUrl;			//付款链接
	
	private String nickname;			//用户昵称
	
	private String headImg;				//用户头像

	public String getPcCover() {
		return pcCover;
	}

	public void setPcCover(String pcCover) {
		this.pcCover = pcCover;
	}

	public String getmCover() {
		return mCover;
	}

	public void setmCover(String mCover) {
		this.mCover = mCover;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDepositPrice() {
		return depositPrice;
	}

	public void setDepositPrice(Double depositPrice) {
		this.depositPrice = depositPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getTrialNum() {
		return trialNum;
	}

	public void setTrialNum(Integer trialNum) {
		this.trialNum = trialNum;
	}

	public String getIsChargeDeposit() {
		return isChargeDeposit;
	}

	public void setIsChargeDeposit(String isChargeDeposit) {
		this.isChargeDeposit = isChargeDeposit;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Integer getReportStatus() {
		return reportStatus;
	}

	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getHbaseOrderId() {
		return hbaseOrderId;
	}

	public void setHbaseOrderId(Long hbaseOrderId) {
		this.hbaseOrderId = hbaseOrderId;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

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
	
	
	
}
