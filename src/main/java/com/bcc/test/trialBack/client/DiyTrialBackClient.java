package com.bcc.test.trialBack.client;

import io.terminus.pampas.client.Export;
import net.sf.json.JSONObject;

/**
 * 试用活动接口
 * ClassName:DiyTrialBackClient
 * @Description:试用活动业务接口
 * @author lily
 * @date 2017-05-04
 *
 */
public interface DiyTrialBackClient {
	/**
	 * 分页查询试用活动列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	@Export(paramNames={"pageNum", "pageSize"})
	public JSONObject getTrialByPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 新增试用活动
	 * @param productId				产品id
	 * @param trialNum				试用数量
	 * @param isChargeDeposit		是否交付押金
	 * @param pcCover				pc产品图
	 * @param mCover				移动版产品图
	 * @param base					申请用户基数
	 * @param status				启用状态  1启用 0禁用
	 * @param startTime				活动开始时间
	 * @param preheatEndTime		预热结束时间
	 * @param applyEndTime			申请结束时间
	 * @param endTime				活动结束时间
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	@Export(paramNames={"productId", "trialNum","isChargeDeposit", "pcCover","mCover", "base",
			"status","startTime", "preheatEndTime","applyEndTime", "endTime"})
	public JSONObject addTrial(Long productId, Integer trialNum, String isChargeDeposit, String pcCover, String mCover,
                               Integer base, Integer status, String startTime, String preheatEndTime, String applyEndTime, String endTime);

	/**
	 * 获取指定的试用活动信息
	 * @param id   试用活动主键
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	@Export(paramNames={"id"})
	public JSONObject getTrialById(Long id);

	/**
	 * 修改试用活动
	 * @param productId				产品id
	 * @param trialNum				试用数量
	 * @param isChargeDeposit		是否交付押金
	 * @param pcCover				pc产品图
	 * @param mCover				移动版产品图
	 * @param base					申请用户基数
	 * @param rule					活动规则
	 * @param status				启用状态  1启用 0禁用
	 * @param startTime				活动开始时间
	 * @param preheatEndTime		预热结束时间
	 * @param applyEndTime			申请结束时间
	 * @param endTime				活动结束时间
	 * @param goodsList				推荐商品列表
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	@Export(paramNames={"id","productId", "trialNum","isChargeDeposit", "pcCover","mCover", "base",
			"status","startTime", "preheatEndTime","applyEndTime", "endTime"})
	public JSONObject updateTrial(Long id, Long productId, Integer trialNum, String isChargeDeposit, String pcCover, String mCover,
                                  Integer base, Integer status, String startTime, String preheatEndTime, String applyEndTime, String endTime);

	/**
	 * 更新试用活动规则
	 * @param id
	 * @param rule
	 * @return
	 * @author lily
	 * @date 2017-06-09
	 */
	@Export(paramNames={"id","rule"})
	public  JSONObject  updateTrialRule(Long id, String rule);

	/**
	 * 更新试用活动推荐商品
	 * @param goodsList
	 * @return
	 * @author lily
	 * @date 2017-06-09
	 */
	@Export(paramNames={"id","goodsList"})
	public JSONObject   updateTrialData(Long id, String goodsList);


	/**
	 * 修改试用活动排序
	 * @param id
	 * @param sort
	 * @return
	 * @author lily
	 * @date  2017-05-05
	 */
	@Export(paramNames={"id","sort"})
	public JSONObject  updateTrialSort(Long id, Integer sort);

	/**
	 * 启用/禁用试用活动
	 * @param id        试用活动id
	 * @param status    状态（1：启用   2：禁用）
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	@Export(paramNames={"id", "status"})
	public JSONObject onAndOffTrial(Long id, Integer status);
	
	/**
	 * 删除试用活动
	 * @param id   试用活动id
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	@Export(paramNames={"id"})
	public JSONObject deleteTrial(Long id);
	


}
