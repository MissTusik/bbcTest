package com.bcc.test.trialBack.service;

import com.bcc.test.common.utils.Page;
import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialData;
import com.bcc.test.trial.service.com;
import com.bcc.test.trialBack.domain.DiyTrialSupper;

import java.util.List;
import java.util.Map;

public interface DiyTrialBackService {
	/**
	 * 分页查询试用活动列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	public Page<Map> getTrialByPage(Integer pageNum, Integer pageSize);

	/**
	 * 新增试用活动
	 * @param diyTrial    		新增试用活动
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public Map<String,Object> addTrial(DiyTrial diyTrial);
	
	/**
	 * 获取指定的试用活动信息
	 * @param id
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public DiyTrialSupper getTrialById(Long id);
	
	/**
	 * 修改试用活动基本信息
	 * @param diyTrial    		修改的试用活动
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public int updateTrial(DiyTrial diyTrial);
	
	/**
	 * 修改试用活动规则
	 * @param id    		试用活动id
	 * @param rule    		试用活动规则
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public int updateTrialRule(Long id, String rule);
	
	/**
	 * 修改试用活动推荐信息
	 * @param id    		        试用活动id
	 * @param diyTrialDataList	试用活动推荐数据
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public int updateTrialData(Long id, List<DiyTrialData> diyTrialDataList);

	/**
	 * 修改指定的试用活动排序
	 * @param id
	 * @param sort
	 * @author lily
	 * @date 2017-05-05
	 * @return
	 */
	public int updateTrialSort(Long id, Integer sort);
		
	/**
	 * 启用/禁用试用活动
	 * @param id  试用活动主键标识
	 * @param status   状态（1：启用    2：禁用）
	 * @return int
	 * @author lily
	 * @date 2017-05-04
	 */
	public int onAndOffTrial(Long id, Integer status);

	/**
	 * 删除试用活动
	 * @param id
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	public int deleteTrial(Long id);

	/**
	 * 删除指定试用活动的推荐商品
	 * @param trialId   试用活动id
	 * @author lily
	 * @date  2017-05-08
	 */
	public int deleteTrialDataByTrialId(Long trialId);




}
