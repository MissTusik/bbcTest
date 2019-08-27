package com.bcc.test.trialBack.mapper;

import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialData;
import com.bcc.test.trialBack.domain.DiyTrialSupper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: DiyTrialBackMapper 
 * @Description: 试用产品管理
 * @author lily
 * @date 2017-05-03
 */
public interface DiyTrialBackMapper {

	/**
	 * @Description: 查询试用产品总行数
	 * @return   
	 * @return int  
	 * @throws
	 * @author lily
	 * @date 2017-5-4
	 */
	public int getTrialCount();

	/**
	 * @Description: 分页查询试用产品列表
	 * @param page
	 * @return   
	 * @return List<DiyTrialSupper>  
	 * @throws
	 * @author lily
	 * @date 2017-5-4
	 */
	@SuppressWarnings("rawtypes")
	public List<DiyTrialSupper> getTrialByPage(@Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);

	/**
	 * 新增试用活动
	 * @param diyTrial  试用活动信息
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public int insertDiyTrial(DiyTrial diyTrial);

	/**
	 * 获取指定的试用活动详细信息
	 * @param id  试用活动主键
	 * @return    DiyTrialSupper
	 * @author lily
	 * @date 2015-05-05
	 */
	public DiyTrialSupper selectTrialSupperById(Long id);
	/**
	 * 获取指定的试用活动基本信息
	 * @param id  试用活动主键
	 * @return    DiyTrial
	 * @author lily
	 * @date 2015-05-05
	 */
	public DiyTrial selectTrialById(Long id);


	/**
	 * 修改试用活动信息
	 * @param diyTrial
	 * @return int  数据库受影响行数
	 * @author lily
	 * @date  2017-05-04
	 */
	public int updateDiyTrial(DiyTrial diyTrial);

	/**
	 * 删除试用活动信息
	 * @param id   试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-04
	 */
	public int deleteDiyTrial(Long id);


//	********************有关试用活动推荐商品的操作**************************
	/**
	 * 新增试用活动推荐商品记录
	 * @param diyTrialData	试用活动推荐商品
	 * @return
	 * @author lily
	 * @date 2015-05-05
	 */
	public int insertDiyTrialData(DiyTrialData diyTrialData);

	/**
	 * 删除试用活动的推荐商品数据
	 * @param id		推荐主键id
	 * @param trialId	试用活动id
	 * @return
	 * @author lily
	 * @date 2015-05-05
	 */
	public int deleteDiyTrialData(@Param("id") Long id, @Param("trialId") Long trialId);

	/**
	 * 查询指定试用活动的推荐商品列表
	 * @param trialId   试用活动id
	 * @return
	 * @author lily
	 * @date 2015-05-05
	 */
	public List<DiyTrialData> selectDiyTrialData(Long trialId);



}
