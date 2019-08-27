package com.bcc.test.trial.client;

import io.terminus.pampas.client.Export;
import net.sf.json.JSONObject;

/**
 * 试用报告逻辑接口
 * @author lily
 * @date 2017-05-24
 *
 */
public interface DiyTrialReportClient {
	
	/**
	 * 分页获取试用报告列表
	 * @param pageSize        当前页码
	 * @param pageNum	           每页显示个数
	 * @param trialId	           试用活动id
	 * @param isRecommend	   推荐标识 Y/N
	 * @return
	 * @author lily
	 * @date 2017-05-24
	 */
	@Export(paramNames={"pageNum","pageSize","trialId","isRecommend"})
	public JSONObject   getTrialReportByPage(Integer pageNum, Integer pageSize, Long trialId, String isRecommend);

	/**
	 * 获取指定的试用报告详情
	 * @param reportId    试用报告id
	 * @return
	 * @author lily
	 * @date  2017-05-25
	 */
	@Export(paramNames={"reportId"})
	public JSONObject   getTrialReportById(Long reportId);

	/**
	 * 获取报告的基本信息---用于修改试用报告页面
	 * @param reportId
	 * @return
	 */
	@Export(paramNames={"reportId"})
	public JSONObject selectTrialReportById(Long reportId);

	/**
	 * 新增试用报告
	 * @param report		json格式
	 * @return
	 * @author lily
	 * @date  2017-06-07
	 */
	@Export(paramNames={"report"})
	public JSONObject   addTrialReport(String report);

	/**
	 * 修改试用报告
	 * @param report		json格式
	 * @return
	 * @author lily
	 * @date  2017-06-07
	 */
	@Export(paramNames={"report"})
	public JSONObject   updateTrialReport(String report);

	/**
	 * 分页获取指定报告的评论列表
	 * @param pageNum		当前页码
	 * @param pageSize		每页个数
	 * @param reportId		试用报告id
	 * @return
	 * @author lily
	 * @date 2017-05-26
	 */
	@Export(paramNames={"pageNum","pageSize","reportId"})
	public JSONObject   getTrialReportCommentByPage(Integer pageNum, Integer pageSize, Long reportId);

	/**
	 * 分页获取指定顶级评论的子评论列表
	 * @param pageNum		当前页码
	 * @param pageSize		每页个数
	 * @param reportId		试用报告id
	 * @param groupId		顶级评论id
	 * @return
	 * @author lily
	 * @date 2017-05-26
	 */
	@Export(paramNames={"pageNum","pageSize","reportId","groupId"})
	public JSONObject   getChildReportCommentByPage(Integer pageNum, Integer pageSize, Long reportId, Long groupId);

	/**
	 * 发表评论
	 * @param reportId    报告id
	 * @param content	    评论内容
	 * @return
	 * @author lily
	 * @date  2017-05-26
	 */
	@Export(paramNames={"reportId","content"})
	public JSONObject  createTrialReportComment(Long reportId, String content);

	/**
	 * 回复评论
	 * @param reportId		试用报告id
	 * @param parentId		上层评论id
	 * @param content		回复内容
	 * @return
	 * @author lily
	 * @date 2017-05-26
	 */
	@Export(paramNames={"reportId","parentId","content"})
	public JSONObject  replyTrialReportComment(Long reportId, Long parentId, String content);

	/**
	 * 试用报告点赞/点踩
	 * @param reportId   试用报告id
	 * @param type		 操作类型   1：点赞   2：点踩
	 * @return
	 * @author lily
	 * @date 2017-05-26
	 */
	@Export(paramNames={"reportId","type"})
	public JSONObject  likeOrDislikeTrialReport(Long reportId, Integer type);

	

}
