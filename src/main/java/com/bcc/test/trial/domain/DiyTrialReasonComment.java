package com.bcc.test.trial.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 试用申请理由评论Bean
 * ClassName: DiyTrialReasonComment 
 * @Description: 试用申请理由评论Bean
 * @author lily
 * @date 2017-05-21
 */
public class DiyTrialReasonComment implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Long id;		//id标识

	private Long trialApplicationId;	//试用报告id
	
    private Long userId;	//发表话题的用户id

    private Long parentId;	//指定上级话题的标识，默认为0则没有指定

    private Long toUserId;	//指定回复用户标识，，默认为0则没有指定


    private String content;	//话题内容

    private Date createTime;//创建时间
    
//    非数据库表中的字段
    private String nickname;
    
    private String toNickname;

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

	public Long getTrialApplicationId() {
		return trialApplicationId;
	}

	public void setTrialApplicationId(Long trialApplicationId) {
		this.trialApplicationId = trialApplicationId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getToNickname() {
		return toNickname;
	}

	public void setToNickname(String toNickname) {
		this.toNickname = toNickname;
	}
    
	
    

 
}