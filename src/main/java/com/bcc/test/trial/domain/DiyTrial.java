package com.bcc.test.trial.domain;

import com.bcc.test.common.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * 试用活动Bean
 * ClassName: DiyTrial
 * @Description: 试用活动
 * @author lily
 * @date 2017-04-27
 */
public class DiyTrial implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//试用活动id
	private Long id; 
	
	//试用产品id
	private Long productId; 	
	
	//试用数量
	private Integer trialNum;
	
	// pc端产品图
	private String pcCover; 
	
	// m端产品图
	private String mCover; 
	
	// 申请基数
	private Integer base; 
	
	// 活动开始时间
	private Date startTime; 
	private String startTimeStr; 

	// 预热结束时间
	private Date preheatEndTime;
	private String preheatEndTimeStr;
	
	// 申请结束时间
	private Date applyEndTime;
	private String applyEndTimeStr;
	
	// 活动结束时间
	private Date endTime;
	private String endTimeStr;
	
	//是否收取押金
	private String isChargeDeposit;
	
	//	活动规则
	private String rule;
	
	//活动状态   1启用  2禁用
	private Integer status;
	
	//	创建时间
	private Date createTime;
	
	//排序序号
	private Integer sort;
	
	public DiyTrial() {
		super();
	}
	
	public DiyTrial(Long id, Long productId, Integer trialNum,String pcCover, String mCover, 
			Integer base,Date startTime,Date preheatEndTime,Date applyEndTime,Date endTime,String isChargeDeposit,
			String rule, Integer status) {
		super();
		this.id = id;
		this.productId = productId;
		this.trialNum = trialNum;
		this.pcCover = pcCover;
		this.mCover = mCover;
		this.base = base;
		this.startTime = startTime;
		this.preheatEndTime = preheatEndTime;
		this.applyEndTime = applyEndTime;
		this.endTime = endTime;
		this.isChargeDeposit = isChargeDeposit;
		this.rule = rule;
		this.status = status;
	}
	public DiyTrial(Long productId, Integer trialNum,String pcCover, String mCover, 
			Integer base,Date startTime,Date preheatEndTime,Date applyEndTime,Date endTime,String isChargeDeposit,
			String rule, Integer status) {
		super();
		this.productId = productId;
		this.trialNum = trialNum;
		this.pcCover = pcCover;
		this.mCover = mCover;
		this.base = base;
		this.startTime = startTime;
		this.preheatEndTime = preheatEndTime;
		this.applyEndTime = applyEndTime;
		this.endTime = endTime;
		this.isChargeDeposit = isChargeDeposit;
		this.rule = rule;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getTrialNum() {
		return trialNum;
	}

	public void setTrialNum(Integer trialNum) {
		this.trialNum = trialNum;
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

	public Integer getBase() {
		return base;
	}

	public void setBase(Integer base) {
		this.base = base;
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

	public String getIsChargeDeposit() {
		return isChargeDeposit;
	}

	public void setIsChargeDeposit(String isChargeDeposit) {
		this.isChargeDeposit = isChargeDeposit;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStartTimeStr() {
		if(this.startTime != null){
			this.startTimeStr = DateUtil.formatDateToString(this.startTime);
		}
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getPreheatEndTimeStr() {
		if(this.preheatEndTime != null){
			this.preheatEndTimeStr = DateUtil.formatDateToString(this.preheatEndTime);
		}
		return preheatEndTimeStr;
	}

	public void setPreheatEndTimeStr(String preheatEndTimeStr) {
		this.preheatEndTimeStr = preheatEndTimeStr;
	}

	public String getApplyEndTimeStr() {
		if(this.applyEndTime != null){
			this.applyEndTimeStr = DateUtil.formatDateToString(this.applyEndTime);
		}
		return applyEndTimeStr;
	}

	public void setApplyEndTimeStr(String applyEndTimeStr) {
		this.applyEndTimeStr = applyEndTimeStr;
	}

	public String getEndTimeStr() {
		if(this.endTime != null){
			this.endTimeStr = DateUtil.formatDateToString(this.endTime);
		}
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	

}
