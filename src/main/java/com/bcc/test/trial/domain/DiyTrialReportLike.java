package com.bcc.test.trial.domain;

import java.util.Date;

/**
 * 试用报告点赞/点踩Bean
 * @author lily
 * @date 2017-05-26
 *
 */
public class DiyTrialReportLike {
//	主键id
	private Long id;
//	试用报告id
	private Long reportId;
//	点赞/点踩用户id
	private Long userId;
//  操作类型  1：点赞   2：点踩	
	private Integer type;
//	创建时间
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
