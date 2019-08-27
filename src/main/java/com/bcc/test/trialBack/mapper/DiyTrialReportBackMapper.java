package com.bcc.test.trialBack.mapper;

import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trialBack.domain.DiyTrialReportSupper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DiyTrialReportBackMapper 
 * @Description: 试用报告管理
 * @author lily
 * @date 2017-05-17
 */
public interface DiyTrialReportBackMapper {

	/**
	 * @Description: 查询所有试用产品公示报告数量
	 * @param  trialId  试用活动id
	 * @return   
	 * @return int  
	 * @throws
	 * @author lily
	 * @date 2017-5-17
	 */
	public int getPublicTrialReportCount(@Param("trialId") Long trialId);
	/**
	 * @Description: 查询指定试用报告所有报告数量
	 * @param  trialId  试用报告id
	 * @return
	 * @return int
	 * @throws
	 * @author lily
	 * @date 2017-5-17
	 */
	public int getTrialReportCount(@Param("trialId") Long trialId);

	/**
	 * @Description: 分页查询所有试用产品公示报告列表
	 * @param page
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 * @author lily
	 * @date 2017-5-17
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> getPublicTrialReportByPage(@Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);

	/**
	 * @Description: 分页查询指定试用报告的所有报告列表
	 * @param page
	 * @param trialId   试用报告id
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 * @author lily
	 * @date 2017-5-17
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> getTrialReportByPage(@Param("trialId") Long trialId, @Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);


	/**
	 * 获取指定的试用报告详情
	 * @param id  试用报告主键
	 * @return
	 * @author lily
	 * @date 2015-05-17
	 */
	public DiyTrialReportSupper selectTrialReportById(Long id);


	/**
	 * 修改试用报告信息
	 * @param diyTrialReport
	 * @return int  数据库受影响行数
	 * @author lily
	 * @date  2017-05-17
	 */
	public int updateDiyTrialReport(DiyTrialReport diyTrialReport);

	/**
	 * 删除试用报告信息
	 * @param id   试用报告id
	 * @return
	 * @author lily
	 * @date  2017-05-18
	 */
	public int deleteDiyTrialReport(Long id);

	/**
	 * 获取指定的id试用报告列表
	 * @param idsList
	 * @return
	 * @author lily
	 * @date  2017-05-18
	 */
	public List<DiyTrialReport> selectTrialReportByIds(@Param("idsList") List<Long> idsList);
	

}
