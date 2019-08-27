package com.bcc.test.trial.mapper;

import com.haier.diy.trial.domain.DiyTrialReportComment;
import com.haier.diy.trial.domain.DiyTrialReportCommentCus;

import java.util.List;
import java.util.Map;

/**
 * 试用报告评论Mapper
 * ClassName: DiyProductTopicMapper 
 * @Description: 试用报告评论Mapper
 * @author Chen Hailin
 * @date 2016-8-9
 */
public interface DiyTrialReportCommentMapper {
	
	/**
	 * 获取顶级试用报告评论总数
	 * @Description: 获取顶级试用报告评论总数
	 * @param reportId 试用报告id
	 * @return   
	 * @return Integer  
	 * @throws
	 * @author lily
	 * @date 2017-05-26
	 */
	public Integer getTrialReportCommentTotal(Long reportId);
	
	/**
	 * 获取指定试用报告评论下的子级试用报告评论总数
	 * @Description: 获取指定试用报告评论下的子级试用报告评论总数
	 * @param map 封装groupId:顶级试用报告评论id 
	 * 			  reportId:试用报告id
	 * @return   
	 * @return Integer  
	 * @throws
	 * @author lily
	 * @date 2017-05-26
	 */
	public Integer getChildTrialReportCommentTotal(Map<String, Object> map);
	
	/**
	 * 获取试用报告评论
	 * @Description: 获取试用报告评论
	 * @param map 封装type:(parent:查询父级试用报告评论，child:子级试用报告评论) 
	 * 			reportId:试用报告id 
	 * 			page:查询试用报告评论分页信息
	 * 			groupId：查询子级试用报告评论需要，顶级试用报告评论id
	 * @return   
	 * @return List<DiyTrialReportCommentCus>  
	 * @throws
	 * @author lily
	 * @date 2016-8-9
	 */
	public List<DiyTrialReportCommentCus> getTrialReportComment(Map<String, Object> map);

	
	/**
	 * 发表试用报告评论
	 * @param diyTrialReportComment
	 * @return
	 */
	public Boolean createTrialReportComment(DiyTrialReportComment diyTrialReportComment);
	/**
	 * 获取指定id的试用报告评论
	 * @param DiyTrialReportComment
	 * @return
	 */
	public DiyTrialReportComment getTrialReportCommentById(Long id);
	
	/**
	 * 获取指定试用报告评论
	 * @Description: 获取指定试用报告评论（包含用户名、头像等等）
	 * @param id 试用报告评论id
	 * @return   
	 * @return DiyTrialReportCommentCus  
	 * @throws
	 * @author Chen Hailin
	 * @date 2016-8-12
	 */
	public DiyTrialReportCommentCus getTrialReportCommentCusById(Long id);

	/**
	 * 获取指定用户的试用报告评论列表
	 * @param userId  用户标识
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public List<DiyTrialReportComment> getReportCommentListByUserId(Long userId);

	/**
	 * 批量更新指定试用报告评论的userId
	 * @param map
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public Integer updateReportCommentUserId(Map<String, Object> map);
	
	
	
}
