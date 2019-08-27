package com.bcc.test.trial.domain;

/**
 * 话题讨论扩展类
 * ClassName: DiyProductTopicCus 
 * @Description: 话题讨论扩展类
 * @author Chen Hailin
 * @date 2016-8-9
 */
public class DiyTrialReportCommentCus extends DiyTrialReportComment {

	private static final long serialVersionUID = 1L;
	
	private String nickname;		//用户昵称
	
	private String toNickname;		//被回复用户昵称
	
	private String headImg;			//用户头像
	
	private Integer replyCount;		//回复数量
	
	private Integer surplusNum;		//子评论剩余数量
	
	
	private Object item;

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
	
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public Integer getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}
	
	
}
