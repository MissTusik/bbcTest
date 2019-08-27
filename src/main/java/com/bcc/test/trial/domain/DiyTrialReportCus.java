package com.bcc.test.trial.domain;

public class DiyTrialReportCus  extends DiyTrialReport{
	
	private static final long serialVersionUID = 1L;

	private String nickname;		//昵称
	
	private String headImg;			//头像
	
	private String productName;		//产品名称
	
	private Long productId;			//产品id
	
	private String pcCover;			//pc版产品图
	
	private String mCover;			//m版产品图
	
	private Integer base;	//申请基数
	
	private Integer trialUserNum;	//申请人数
	
	private Integer reportNum;		//试用报告数量
	
	private Integer  isLike;		//是否点过赞
	
	private Integer isDislike;		//是否点过踩
	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public Integer getIsLike() {
		return isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public Integer getIsDislike() {
		return isDislike;
	}

	public void setIsDislike(Integer isDislike) {
		this.isDislike = isDislike;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	

}
