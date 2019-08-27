package com.bcc.test.trial.service;

import com.bcc.test.trial.domain.DiyTrialReportComment;
import com.bcc.test.trial.domain.DiyTrialReportCommentCus;
import com.bcc.test.common.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * 试用报告评论Service
 * ClassName: DiyTrialReportCommentService 
 * @Description: 试用报告评论Service
 * @author lily
 * @date 2017-05-26
 */
public interface DiyTrialReportCommentService {
	
	/**
	 * 获取指定试用报告的试用报告评论
	 * @Description: 获取指定试用报告的试用报告评论
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param reportId 试用报告id
	 * @return   
	 * @return List<DiyTrialReportCommentCus>  
	 * @throws
	 * @author lily
	 * @date 2017-05-26
	 */
	public Page<Map> getTrialReportCommentByPage(Integer pageNum, Integer pageSize,
                                                 Long reportId);


	/**
	 * 分页获取子级试用报告评论(纯粹分页获取，不考虑初始页显示的情况)
	 * @Description: 分页获取子级试用报告评论
	 * @param groupId 顶级试用报告评论id/分组id标识
	 * @param pageNum 页码
	 * @param pageSize 当前页
	 * @param reportId 试用报告id
	 * @return
	 * @return List<DiyTrialReportCommentCus>
	 * @throws
	 * @author fanjy
	 * @date 2017-05-26
	 */
	public Page<DiyTrialReportCommentCus> getChildCommentByPage(
            Long groupId,
            Integer pageNum,
            Integer pageSize,
            Long reportId);


	/**
	 * 创建试用报告评论并获取当前试用报告评论
	 * @Description: 创建试用报告评论并获取当前试用报告评论
	 * @param diyTrialReportComment
	 * @return
	 * @return DiyTrialReportCommentCus
	 * @throws
	 * @author lily
	 * @date 2017-05-26
	 */
	public DiyTrialReportCommentCus createTrialReportCommentAndGet(DiyTrialReportComment diyTrialReportComment);


	/**
	 * 获取指定用户的试用报告评论列表
	 * @param userId  用户标识
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public List<DiyTrialReportComment> getReportCommentListByUserId(
            Long userId);


	/**
	 * 批量修改指定试用报告评论的userId
	 * @param list   报告评论id
	 * @param userId  用户标识
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public int updateReportCommentUserId(List<Long> list, Long userId);
	
	
}
