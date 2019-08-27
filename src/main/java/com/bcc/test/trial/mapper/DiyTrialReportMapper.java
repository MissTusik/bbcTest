package com.bcc.test.trial.mapper;

import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trial.domain.DiyTrialReportCus;
import com.bcc.test.trial.domain.DiyTrialReportLike;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DiyTrialReportMapper 
 * @Description: 试用报告
 * @author lily
 * @date 2017-05-24
 */
public interface DiyTrialReportMapper {

	/**
	 * @Description: 多条件查询试用报告数量
	 * @param  trialId      试用报告id
	 * @param  isRecommend  是否推荐标识 Y/N
	 * @return   
	 * @return int  
	 * @throws
	 * @author lily
	 * @date 2017-5-23
	 */
	public int getTrialReportCount(@Param("trialId") Long trialId, @Param("isRecommend") String isRecommend);


	/**
	 * @Description: 分页多条件查询试用报告的所有报告列表
	 * @param page
	 * @param trialId       试用报告id
	 * @param isRecommend   是否推荐标识 Y/N
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 * @author lily
	 * @date 2017-5-23
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> getTrialReportByPage(
            @Param("startItem") Integer startItem,
            @Param("pageSize") Integer pageSize,
            @Param("trialId") Long trialId,
            @Param("isRecommend") String isRecommend);


	/**
	 * 获取指定的试用报告详情
	 * @param id  试用报告主键
	 * @param userId  当前用户id
	 * @return
	 * @author lily
	 * @date 2015-05-25
	 */
	public DiyTrialReportCus getTrialReportById(@Param("id") Long id, @Param("userId") Long userId);

	/**
	 * 获取指定的试用报告基础信息
	 * @param id  试用报告主键
	 * @return
	 * @author lily
	 * @date 2015-05-25
	 */
	public DiyTrialReport selectTrialReportById(Long id);

	/**
	 * 获取指定试用申请的试用报告
	 * @param applicationId
	 * @return
	 * @author lily
	 * @date 2015-05-25
	 */
	public DiyTrialReport getTrialReportByApplicationId(Long applicationId);

	/**
	 * 新增试用报告
	 * @param diyTrialReport
	 * @return
	 * @author lily
	 * @date 2015-05-25
	 */
    public int insertTrialReport(DiyTrialReport diyTrialReport);

	/**
	 * 更新试用报告
	 * @param diyTrialReport
	 * @return
	 * @author lily
	 * @date 2015-05-25
	 */
	public int updateTrialReport(DiyTrialReport diyTrialReport);

	/**
	 * 更新试用报告 点赞、点踩、评论数量
	 * @param diyTrialReport
	 * @return
	 * @author lily
	 * @date 2015-05-25
	 */
	public int updateTrialReportNum(DiyTrialReport diyTrialReport);




//	--------------------------------下面为试用报告点赞或点踩-----------------------------
	/**
	 * 获取指定的试用活动基本信息
	 * @param id  试用活动主键
	 * @return    DiyTrial
	 * @author lily
	 * @date 2015-05-25
	 */
	public DiyTrialReportLike selectReportLikeByUserId(@Param("reportId") Long reportId, @Param("userId") Long userId, @Param("type") Integer type);
	
	/**
	 * 点赞
	 * @Description: 
	 * @param: @param diyTrialReportLike 点赞记录
	 * @param: @return   
	 * @return: int  
	 * @throws
	 * @author: lily
	 * @date 2015-05-26
	 */
	public  int  insertLike(DiyTrialReportLike diyTrialReportLike);


	/**
	 * 获取指定用户的试用报告列表
	 * @param userId  用户标识
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public List<DiyTrialReport> getReportListByUserId(Long userId);

	/**
	 * 批量更新指定试用报告的userId
	 * @param map
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public Integer updateReportUserId(Map<String, Object> map);


	







}
