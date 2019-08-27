package com.bcc.test.trial.client;

import io.terminus.pampas.client.Export;
import net.sf.json.JSONObject;

/**
 * 试用活动业务逻辑接口
 * @author lily
 * @date 2017-05-23
 *
 */
public interface DiyTrialClient {
	
	/**
	 * 分页获取试用活动列表
	 * @param pageSize    当前页码
	 * @param pageNum	    每页显示个数
	 * @return
	 * @author lily
	 * @date 2017-05-23
	 */
	@Export(paramNames={"pageNum","pageSize"})
	public JSONObject   getTrialByPage(Integer pageNum, Integer pageSize);

	/**
	 * 获取试用详情
	 * @param trialId 试用活动id
	 * @return
	 * @author lily
	 * @date 2017-05-24
	 */
	@Export(paramNames={"trialId"})
	public JSONObject   getTrialDetailById(Long trialId);

	/**
	 * 收藏试用
	 * @param trialId	试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-27
	 */
	@Export(paramNames={"trialId"})
	public JSONObject   collectTrial(Long trialId);

	/**
	 * 取消收藏试用
	 * @param trialId 试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-27
	 */
	@Export(paramNames={"trialId"})
	public JSONObject   cancelCollectTrial(Long trialId);

	/**
	 * 分页获取试用收藏列表
	 * @param pageSize    当前页码
	 * @param pageNum	    每页显示个数
	 * @return
	 * @author lily
	 * @date 2017-06-02
	 */
	@Export(paramNames={"pageNum","pageSize"})
	public JSONObject   getCollectByPage(Integer pageNum, Integer pageSize);
	

	/**
	 * 获取试用产品信息与当前登录用户信息
	 * @param trialId
	 * @return
	 * @author lily
	 * @date 2017-06-08
	 */
	@Export(paramNames={"trialId"})
	public JSONObject   getTrialProductAndUserInfo(Long trialId);
	

	


}
