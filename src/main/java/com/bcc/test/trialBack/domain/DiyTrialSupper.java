package com.bcc.test.trialBack.domain;

import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialData;

import java.util.ArrayList;
import java.util.List;

public class DiyTrialSupper extends DiyTrial {

	private static final long serialVersionUID = 1L;
//  商品名称
	private String   productName;
//	商品价格
	private String   price;
//	申请用户数量
	private Integer  trialUserNum;
//	试用报告数量
	private Integer  reportNum;
//	当前节点
	private Integer   stage;
	
//	访问链接
    private  String   url;
	
//	推荐商品集合
	private List<DiyTrialData> diyTrialDataList=new ArrayList<DiyTrialData>();
	
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getTrialUserNum() {
		return trialUserNum;
	}
	public void setTrialUserNum(Integer trialUserNum) {
		this.trialUserNum = trialUserNum;
	}
	public Integer getReportNum() {
		return reportNum;
	}
	public void setReportNum(Integer reportNum) {
		this.reportNum = reportNum;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	public List<DiyTrialData> getDiyTrialDataList() {
		return diyTrialDataList;
	}
	public void setDiyTrialDataList(List<DiyTrialData> diyTrialDataList) {
		this.diyTrialDataList = diyTrialDataList;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
