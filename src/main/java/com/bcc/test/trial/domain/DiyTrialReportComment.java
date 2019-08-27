package com.bcc.test.trial.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 试用报告评论Bean
 * ClassName: DiyTrialReportComment 
 * @Description: 试用报告评论Bean
 * @author lily
 * @date 2017-05-26
 */
public class DiyTrialReportComment implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Long id;		//id标识

	private Long reportId;	//试用报告id
	
    private Long userId;	//发表话题的用户id

    private Long parentId;	//指定上级话题的标识，默认为0则没有指定

    private Long toUserId;	//指定回复用户标识，，默认为0则没有指定

    private Long groupId;	//分组标识，同一级话题分组标识，为顶级话题评论的id

    private String content;	//话题内容

    private Date createTime;//创建时间


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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

 
}