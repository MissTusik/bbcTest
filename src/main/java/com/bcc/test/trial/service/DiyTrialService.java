package com.bcc.test.trial.service;

import com.bcc.test.common.utils.Page;

import java.util.Map;

public interface DiyTrialService {
	/**
	 * 分页查询试用活动列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-23
	 */
	public Page<Map> getTrialByPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 获取指定的试用活动详情
	 * @param trialId
	 * @return
	 * @author lily
	 * @date  2017-05-24
	 */
	public Map<String,Object>  getTrialDetailById(Long trialId);
	

	/**
	 * 收藏试用活动
	 * @param trialId	试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-27
	 */
	public int collectTrial(Long trialId);

	/**
	 * 取消收藏试用活动
	 * @param trialId	试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-27
	 */
	public int cancelCollectTrial(Long trialId);

	/**
	 * 分页查询试用活动收藏列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-06-02
	 */
	public Page<Map> getCollectByPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 获取指定试用的商品信息和当前用户信息
	 * @param trialId   试用id
	 * @return
	 * @author lily
	 * @date 2017-06-08
	 */
	public Map<String, Object> getTrialProductAndUserInfo(Long trialId);
	

	
}
