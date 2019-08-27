package com.bcc.test.trial.domain;

import java.util.Date;

/**
 * 试用活动收藏Bean
 * @author lily
 * @date 2017-05-27
 */
public class DiyTrialCollect {
	
	private Long id;
	
	private Long userId;
	
	private Long trialId;
	
	private Date createTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTrialId() {
		return trialId;
	}
	public void setTrialId(Long trialId) {
		this.trialId = trialId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
