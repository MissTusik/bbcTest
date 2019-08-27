package com.bcc.test.trial.domain;

import java.util.List;

/**
 * 试用活动拓展类
 * @author lily
 * @date 2017-05-23
 *
 */
public class DiyTrialCus extends  DiyTrial{
	
	private static final long serialVersionUID = 1L;
	
	private String name;								//产品名称
	private String title;								//产品标题
	private String productPcCover;						//产品pc版详情介绍
	private String productMCover;						//产品m版详情介绍
	private Integer stage;								//当前阶段
	private Integer trialUserNum;						//当前申请人数(基数+实际申请人数)
	private Integer ballotUserNum;						//中签用户人数
	private Integer reportNum;							//试用报告数量
	private Double  price;								//产品价格
	private String RemainTime;							//剩余时间
	private Integer topicNum;							//话题数量
	private Integer isCollect;							//当前用户是否收藏
	private Integer isApply;							//当前用户是否申请
	private List<DiyTrialData>  trialDataItems;			//推荐商品列表

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public Integer getTrialUserNum() {
		return trialUserNum;
	}
	public void setTrialUserNum(Integer trialUserNum) {
		this.trialUserNum = trialUserNum;
	}
	
	public Integer getBallotUserNum() {
		return ballotUserNum;
	}
	public void setBallotUserNum(Integer ballotUserNum) {
		this.ballotUserNum = ballotUserNum;
	}
	public Integer getReportNum() {
		return reportNum;
	}
	public void setReportNum(Integer reportNum) {
		this.reportNum = reportNum;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemainTime() {
		return RemainTime;
	}
	public void setRemainTime(String remainTime) {
		RemainTime = remainTime;
	}
	public List<DiyTrialData> getTrialDataItems() {
		return trialDataItems;
	}
	public void setTrialDataItems(List<DiyTrialData> trialDataItems) {
		this.trialDataItems = trialDataItems;
	}
	public Integer getTopicNum() {
		return topicNum;
	}
	public void setTopicNum(Integer topicNum) {
		this.topicNum = topicNum;
	}

	public String getProductPcCover() {
		return productPcCover;
	}
	public void setProductPcCover(String productPcCover) {
		this.productPcCover = productPcCover;
	}
	public String getProductMCover() {
		return productMCover;
	}
	public void setProductMCover(String productMCover) {
		this.productMCover = productMCover;
	}
	public Integer getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}
	
	public Integer getIsApply() {
		return isApply;
	}
	public void setIsApply(Integer isApply) {
		this.isApply = isApply;
	}

}
