package com.bcc.test.trial.mapper;

import com.haier.diy.trial.domain.DiyTrial;
import com.haier.diy.trial.domain.DiyTrialCollect;
import com.haier.diy.trial.domain.DiyTrialCollectCus;
import com.haier.diy.trial.domain.DiyTrialCus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DiyTrialMapper 
 * @Description: 试用产品
 * @author lily
 * @date 2017-05-23
 */
public interface DiyTrialMapper {

	/**
	 * @Description: 查询试用活动总行数
	 * @return   
	 * @return int  
	 * @throws
	 * @author lily
	 * @date 2017-5-23
	 */
	public int getTrialCount();

	/**
	 * @Description: 分页查询试用产品列表
	 * @param page
	 * @return   
	 * @return List<DiyTrialCus>  
	 * @throws
	 * @author lily
	 * @date 2017-5-23
	 */
	@SuppressWarnings("rawtypes")
	public List<DiyTrialCus> getTrialByPage(@Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);


	/**
	 * 获取指定的试用活动详细信息
	 * @param id  				试用活动主键
	 * @return    DiyTrialCus	试用活动详细信息
	 * @author lily
	 * @date 2017-05-05
	 */
	public DiyTrialCus selectTrialCusById(@Param("id") Long id, @Param("userId") Long userId);

	/**
	 * 获取指定的试用活动基础信息
	 * @param id  				试用活动主键
	 * @author lily
	 * @date 2017-06-01
	 */
	public DiyTrial selectTrialById(Long id);

	/**
	 * 获取指定试用活动的产品信息
	 * @param trialId
	 * @return
	 * @author lily
	 * @date 2017-06-08
	 */
	public Map<String,Object>  getTrialProductInfo(Long trialId);

	/**
	 * 获取指定用户收藏试用活动的个数
	 * @param userId     用户id
	 * @return
	 * @author lily
	 * @date: 2017-06-02
	 */
	public Integer selectCollectCount(Long userId);

	/**
	 * 获取指定用户收藏试用活动的个数
	 * @param userId     用户id
	 * @return
	 * @author lily
	 * @date: 2017-06-02
	 */
	public Integer getCollectCount(Long userId);

	/**
	 * 查询分页的收藏记录
	 * @Description:
	 * @param: @param userId 用户标识
	 * @param: @param startItem 开始行数
	 * @param: @param pageSize 页大小
	 * @param: @return
	 * @return: List<DiyTrialCollectCus>  分页查询出的结果集
	 * @throws
	 * @author: lily
	 * @date: 2017-06-02
	 */
	public List<DiyTrialCollectCus> selectCollectByPage(@Param("userId") Long userId, @Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);


	/**
	 * 获取指定类型的收藏信息
	 * @param userId      用户标识id
	 * @param trialId	  试用活动id
	 * @return DiyTrialCollect
	 * @author lily
	 * @date 2017-5-27
	 */
	public  DiyTrialCollect   selectTrialCollectByFrom(@Param("userId") Long userId, @Param("trialId") Long trialId);

	/**
	 * 插入收藏记录
	 * @param diyTrialCollect
	 * @return
	 * @author lily
	 * @date 2017-5-27
	 */
	public  int  insertTrialCollect(DiyTrialCollect diyTrialCollect);

	/**
	 * 删除指定的收藏试用活动信息
	 * @param userId		用户标识id
	 * @param trialId		试用活动id
	 * @return
	 * @date 2017-05-27
	 */
	public  int  deleteTrialCollect(@Param("userId") Long userId, @Param("trialId") Long trialId);

	/**
	 * 获取指定用户的试用收藏列表
	 * @param userId
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public List<Map<String, Object>> getTrialCollectListByUserId(Long userId);

	/**
	 * 批量更新指定试用收藏的userId
	 * @param map  条件
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public Integer updateTrialCollectUserId(Map<String, Object> map);

}
