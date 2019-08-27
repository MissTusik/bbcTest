package com.bcc.test.trial.client.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.bcc.test.common.utils.Page;
import com.bcc.test.common.utils.ResultUtil;
import com.bcc.test.core.system.Role;
import com.bcc.test.core.system.Roles;
import com.bcc.test.trial.client.DiyTrialApplicationClient;
import com.bcc.test.trial.domain.DiyTrialReasonComment;
import com.bcc.test.trial.service.DiyTrialApplicationService;
import com.bcc.test.trial.service.impl.DiyTrialApplicationServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试用活动申请ClientImpl
 * @author lily
 * @date  2017-06-01
 */
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})//参数类型
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回值类型
@Path("/trial/application")
public class DiyTrialApplicationClientImpl  implements  DiyTrialApplicationClient{
	
	@Autowired
	private  DiyTrialApplicationService     diyTrialApplicationService;
	
//	分页获取指定试用活动的申购用户
	@GET
	@POST
	@Path("/getTrialUserByPage")
	@Override
	public JSONObject getTrialUserByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize,
			@QueryParam("trialId")Long trialId, @QueryParam("isPublic")String isPublic) {
		List<Map> list =diyTrialApplicationService.getTrialUserByPage(pageNum, pageSize, trialId, isPublic);
		if(list==null||list.size()==0)
			return ResultUtil.returnResult();
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("items", list);
		return ResultUtil.returnResult(map);
	}

//	分页获取试用申请理由
	@GET
	@POST
	@Path("/getTrialReasonByPage")
	@Override
	public JSONObject getTrialReasonByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize,
			@QueryParam("trialId")Long trialId) {
		Page<Map> resultPage=diyTrialApplicationService.getTrialReasonByPage(pageNum, pageSize, trialId);
		if(resultPage==null){
			return ResultUtil.returnResult();
		}
		return ResultUtil.returnResult(resultPage);
	}

//	分页获取试用申请理由评论
	@GET
	@POST
	@Path("/getTrialReasonCommentByPage")
	@Override
	public JSONObject getTrialReasonCommentByPage(@QueryParam("pageNum")Integer pageNum,
			@QueryParam("pageSize")Integer pageSize, @QueryParam("trialApplicationId")Long trialApplicationId) {
		Page<Map> resultPage=diyTrialApplicationService.getTrialReasonCommentByPage(pageNum, pageSize, trialApplicationId);
		if(resultPage==null){
			return ResultUtil.returnResult();
		}
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("total", resultPage.getTotal());
		map.put("totalPage", resultPage.getTotalPage());
		map.put("pageNum", resultPage.getPageNum());
		map.put("pageSize", resultPage.getPageSize());
		map.put("startItem", resultPage.getStartItem());
		map.put("items", resultPage.getItems());
		//计算子级话题剩余数量
		int count = resultPage.getTotal()-((pageNum-1)*pageSize+DiyTrialApplicationServiceImpl.FIRSTNUM);
		map.put("surplusNum", count<0?0:count);
		return ResultUtil.returnResult(map);
	}

//	发表或回复申请理由评论
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/createTrialReasonComment")
	@Override
	public JSONObject createTrialReasonComment(@QueryParam("trialApplicationId")Long trialApplicationId,
			@QueryParam("parentId")Long parentId, @QueryParam("content")String content) {
		DiyTrialReasonComment comment= diyTrialApplicationService.createTrialReasonComment(trialApplicationId,parentId,content);
		return ResultUtil.returnResult(comment);
	}

//	验证手机号是否已经申请
	@GET
	@POST
	@Path("/sendVerificationCode")
	@Override
	public JSONObject sendVerificationCode(@QueryParam("mobile")String mobile, @QueryParam("trialId")Long trialId) {
		JSONObject  result = diyTrialApplicationService.sendVerificationCode(mobile,trialId);
		return ResultUtil.returnResult();
	}


//	提交申请
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/createTrialApplication")
	@Override
	public JSONObject createTrialApplication(
			@QueryParam("trialId")Long trialId, 
			@QueryParam("name")String name,
			@QueryParam("sex")Integer sex, 
			@QueryParam("mobile")String mobile,
			@QueryParam("verificationCode")String verificationCode,
			@QueryParam("reason")String reason) {
		int result= diyTrialApplicationService.createTrialApplication(trialId,name,sex,mobile,verificationCode,reason);
		return ResultUtil.returnByIntRow(result);
	}

//	分页获取我的申请
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/getMyApplicationByPage")
	@Override
	public JSONObject getMyApplicationByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize,
			@QueryParam("type")Integer type) {
		Page<Map> resultPage=diyTrialApplicationService.getMyApplicationByPage(pageNum,pageSize,type);
		return ResultUtil.returnResult(resultPage);
	}

//	删除指定的我的申请
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/deleteTrialApplication")
	@Override
	public JSONObject deleteTrialApplication(@QueryParam("id")Long id) {
		int result=diyTrialApplicationService.deleteTrialApplication(id);
		return ResultUtil.returnByIntRow(result);
	}

//	设置收货地址
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/updatTrialApplicationAddress")
	@Override
	public JSONObject updatTrialApplicationAddress(Long applicationId,
			Long addressId) {
		int result=diyTrialApplicationService.updatTrialApplicationAddress(applicationId,addressId);
		return ResultUtil.returnByIntRow(result);
	}
}
