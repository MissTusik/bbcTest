package com.bcc.test.trial.domain;

import java.util.Date;

public class DiyTrialData {
//	主键
	private  Long id;
//	试用活动id
	private  Long trialId;
//	推荐商品id
	private Long relationId;
//	创建时间
	private Date createTime;
	
/*****************商品信息****************/
	private String cover;		//推荐商品图片
	private String proName;		//推荐商品名称
	private String proPrice;	//推荐商品价格

	public DiyTrialData() {
		super();
	}

	public  DiyTrialData(Long id,Long trialId,Long relationId){
		super();
		this.id=id;
		this.trialId=trialId;
		this.relationId=relationId;
	}
	
	public  DiyTrialData(Long trialId,Long relationId){
		super();
		this.trialId=trialId;
		this.relationId=relationId;
	}
	
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
	public Long getRelationId() {
		return relationId;
	}
	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProPrice() {
		return proPrice;
	}

	public void setProPrice(String proPrice) {
		this.proPrice = proPrice;
	}
	

}
