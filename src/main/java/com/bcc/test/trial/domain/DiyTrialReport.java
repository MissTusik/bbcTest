package com.bcc.test.trial.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 试用报告Bean
 * ClassName: DiyTrialReport 
 * @Description: 试用报告
 * @author lily
 * @date 2017-05-16
 */
public class DiyTrialReport implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//申请记录id
	private Long id; 
	
	//试用活动id
	private Long trialId; 	
	
	//申请记录id
	private Long trialApplicationId; 	
	
	//用户id
	private Long userId; 	
	
	//报告封面图
	private String cover;
	
	//报告-产品概述
	private String summary; 
	
	//报告内容
	private String content; 
	
	//综合评分0-5
	private Integer comprehensiveScore; 
	
    //功能评分0-5	
	private Integer functionScore;
	
	//外观评分0-5	
	private Integer appearanceScore;
	
	//性能评分0-5	
	private Integer performanceScore;
	
	//报告状态   0：待审核   1：合格    2：允许修改  3：不合格
	private Integer status; 
	
	//是否公示	Y/N
	private String isPublic;
	
	//公示时间
	private Date publicTime; 
	
	//是否推荐  Y/N
	private String isRecommend;
	
	//点赞人数
	private Integer likeNum;
	
	//点踩人数
	private Integer dislikeNum;
	
	//评论人数
	private Integer commentNum;
	
	//排序
	private Integer sort;
	
	//创建时间
	private Date createTime;
	
	//是否显示
	private String isShow;
	
    //pc商品购买链接
	private String  pcUrl;
	
	//m商品购买链接
	private String  mUrl;

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

	public Long getTrialApplicationId() {
		return trialApplicationId;
	}

	public void setTrialApplicationId(Long trialApplicationId) {
		this.trialApplicationId = trialApplicationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getComprehensiveScore() {
		return comprehensiveScore;
	}

	public void setComprehensiveScore(Integer comprehensiveScore) {
		this.comprehensiveScore = comprehensiveScore;
	}

	public Integer getFunctionScore() {
		return functionScore;
	}

	public void setFunctionScore(Integer functionScore) {
		this.functionScore = functionScore;
	}

	public Integer getAppearanceScore() {
		return appearanceScore;
	}

	public void setAppearanceScore(Integer appearanceScore) {
		this.appearanceScore = appearanceScore;
	}

	public Integer getPerformanceScore() {
		return performanceScore;
	}

	public void setPerformanceScore(Integer performanceScore) {
		this.performanceScore = performanceScore;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getLikeNum() {
		return likeNum;
	}

	public void setLikeNum(Integer likeNum) {
		this.likeNum = likeNum;
	}

	public Integer getDislikeNum() {
		return dislikeNum;
	}

	public void setDislikeNum(Integer dislikeNum) {
		this.dislikeNum = dislikeNum;
	}

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getPcUrl() {
		return pcUrl;
	}

	public void setPcUrl(String pcUrl) {
		this.pcUrl = pcUrl;
	}

	public String getmUrl() {
		return mUrl;
	}

	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}


	
}