package com.bcc.test.trial.domain;

import java.util.Date;

/**
 * 试用收藏扩展类
 * @author lily
 * @date  2017-06-05
 */
public class DiyTrialCollectCus extends  DiyTrialCollect{
	
	private  String name;				//产品名称
	
	private  Double price;				//产品价格
	
	private  String pcCover;			//pc版产品图
	
	private  String mCover;				//m版产品图
	
	private  Date startTime;			//活动开始时间
	
	private  Date preheatEndTime;		//预热结束时间
	
	private  Date applyEndTime;			//申请结束时间
	
	private  Date endTime;				//活动结束时间
	
	private  Integer stage;				//当前所处阶段
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getPreheatEndTime() {
		return preheatEndTime;
	}
	public void setPreheatEndTime(Date preheatEndTime) {
		this.preheatEndTime = preheatEndTime;
	}
	public Date getApplyEndTime() {
		return applyEndTime;
	}
	public void setApplyEndTime(Date applyEndTime) {
		this.applyEndTime = applyEndTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}

	
}
