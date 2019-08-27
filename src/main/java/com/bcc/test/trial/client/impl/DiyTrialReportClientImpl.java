package com.bcc.test.trial.client.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.bcc.test.common.utils.BeanToMapUtil;
import com.bcc.test.common.utils.Message;
import com.bcc.test.common.utils.Page;
import com.bcc.test.common.utils.ResultUtil;
import com.bcc.test.core.system.Role;
import com.bcc.test.core.system.Roles;
import com.bcc.test.core.system.ServiceException;
import com.bcc.test.trial.client.DiyTrialReportClient;
import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trial.domain.DiyTrialReportComment;
import com.bcc.test.trial.domain.DiyTrialReportCommentCus;
import com.bcc.test.trial.domain.DiyTrialReportCus;
import com.bcc.test.trial.service.DiyTrialReportCommentService;
import com.bcc.test.trial.service.DiyTrialReportService;
import com.bcc.test.trial.service.impl.DiyTrialReportCommentServiceImpl;
import io.terminus.pampas.common.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 试用报告业务逻辑实现类
 * @author lily
 * @date 2017-05-24
 *
 */
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})//参数类型
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回值类型
@Path("/trial/report")
public class DiyTrialReportClientImpl implements DiyTrialReportClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private  DiyTrialReportService     diyTrialReportService;
	@Autowired
	private  DiyTrialReportCommentService     diyTrialReportCommentService;

//	分页多条件获取试用报告列表
	@GET
	@POST
	@Path("/getTrialReportByPage")
	@Override
	public JSONObject getTrialReportByPage(
			@QueryParam("pageNum")Integer pageNum, 
			@QueryParam("pageSize")Integer pageSize,
			 @QueryParam("trialId")Long trialId,
			 @QueryParam("isRecommend")String isRecommend ) {
		Page<Map> resultPage=diyTrialReportService.getTrialReportByPage(pageNum, pageSize,trialId,isRecommend);
		if(resultPage==null){
			return ResultUtil.returnResult(null, true);
		}
		return ResultUtil.returnResult(resultPage);
	}

//	获取指定的试用报告详情
	@GET
	@POST
	@Path("/getTrialReportById")
	@Override
	public JSONObject getTrialReportById(@QueryParam("reportId")Long reportId) {
		DiyTrialReportCus   report= diyTrialReportService.getTrialReportById(reportId);
		return  ResultUtil.returnResult(report);
	}
	
//	获取指定的试用报告基本信息
	@GET
	@POST
	@Path("/selectTrialReportById")
	@Override
	public JSONObject selectTrialReportById(@QueryParam("reportId")Long reportId) {
		DiyTrialReport   report= diyTrialReportService.selectTrialReportById(reportId);
		return  ResultUtil.returnResult(report);
	}

//	新增试用报告
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/addTrialReport")
	@Override
	public JSONObject addTrialReport(@QueryParam("report")String report) {
		int result=diyTrialReportService.addTrialReport(report);
		return ResultUtil.returnByIntRow(result);
	}

//	修改试用报告
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/updateTrialReport")
	@Override
	public JSONObject updateTrialReport(@QueryParam("report")String report) {
		int result=diyTrialReportService.updateTrialReport(report);
		return ResultUtil.returnByIntRow(result);
	}
	
//	获取指定试用报告的评论列表
	@GET
	@POST
	@Path("/getTrialReportCommentByPage")
	@Override
	public JSONObject getTrialReportCommentByPage(
			@QueryParam("pageNum")Integer pageNum,
			@QueryParam("pageSize")Integer pageSize, 
			@QueryParam("reportId")Long reportId) {
		Page<Map> resultPage=diyTrialReportCommentService.getTrialReportCommentByPage(pageNum, pageSize, reportId);
		if(resultPage==null){
			return ResultUtil.returnResult();
		}
		return ResultUtil.returnResult(resultPage);
	}

//	获取指定试用报告的指定评论的子评论列表
	@GET
	@POST
	@Path("/getChildReportCommentByPage")
	@Override
	public JSONObject getChildReportCommentByPage(
			@QueryParam("pageNum")Integer pageNum,
			@QueryParam("pageSize")Integer pageSize, 
			@QueryParam("reportId")Long reportId, 
			@QueryParam("groupId")Long groupId) {
		Page<DiyTrialReportCommentCus> page=diyTrialReportCommentService.getChildCommentByPage(groupId, pageNum, pageSize, reportId);
		if(page==null){
			return ResultUtil.returnResult();
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", page.getTotal());
		map.put("totalPage", page.getTotalPage());
		map.put("pageNum", page.getPageNum());
		map.put("pageSize", page.getPageSize());
		map.put("startItem", page.getStartItem());
		map.put("items", BeanToMapUtil.convertList(page.getItems(), false));
		//计算子级话题剩余数量
		int count = page.getTotal()-((pageNum-1)*pageSize+DiyTrialReportCommentServiceImpl.FIRSTNUM);
		map.put("surplusNum", count<0?0:count);
		return ResultUtil.returnResult(map);
	}
	
//	发表报告评论
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/createTrialReportComment")
	@Override
	public JSONObject createTrialReportComment(@QueryParam("reportId")Long reportId,@QueryParam("content") String content) {
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		if (userId==null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		DiyTrialReportComment  diyTrialReportComment=new DiyTrialReportComment();
		diyTrialReportComment.setReportId(reportId);
		diyTrialReportComment.setUserId(userId);
		diyTrialReportComment.setContent(content);
		DiyTrialReportCommentCus   commentCus=diyTrialReportCommentService.createTrialReportCommentAndGet(diyTrialReportComment);
		return ResultUtil.returnResult(commentCus);
	}

//	回复报告评论
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/replyTrialReportComment")
	@Override
	public JSONObject replyTrialReportComment(
			@QueryParam("reportId")Long reportId,
			@QueryParam("parentId") Long parentId,
			@QueryParam("content")String content) {
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		if (userId==null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		DiyTrialReportComment  diyTrialReportComment=new DiyTrialReportComment();
		diyTrialReportComment.setReportId(reportId);
		diyTrialReportComment.setUserId(userId);
		diyTrialReportComment.setParentId(parentId);
		diyTrialReportComment.setContent(content);
		DiyTrialReportCommentCus   commentCus=diyTrialReportCommentService.createTrialReportCommentAndGet(diyTrialReportComment);
		return ResultUtil.returnResult(commentCus);
	}

//	试用报告点赞/点踩
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/likeOrDislikeTrialReport")
	@Override
	public JSONObject likeOrDislikeTrialReport(@QueryParam("reportId")Long reportId, @QueryParam("type")Integer type) {
		int result= diyTrialReportService.addLike(reportId,type);
		return  ResultUtil.returnByIntRow(result);
	}

}
