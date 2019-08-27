package com.bcc.test.trial.client.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.bcc.test.common.utils.Page;
import com.bcc.test.common.utils.ResultUtil;
import com.bcc.test.core.system.Role;
import com.bcc.test.core.system.Roles;
import com.bcc.test.trial.client.DiyTrialClient;
import com.bcc.test.trial.service.DiyTrialService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.Map;

/**
 * 试用活动业务逻辑实现类
 * @author lily
 * @date 2017-05-23
 *
 */
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})//参数类型
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回值类型
@Path("/trial")
public class DiyTrialClientImpl implements DiyTrialClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private  DiyTrialService     diyTrialService;

//	分页获取试用活动列表
	@GET
	@POST
	@Path("/getTrialByPage")
	@Override
	public JSONObject getTrialByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize ) {
		Page<Map> resultPage=diyTrialService.getTrialByPage(pageNum, pageSize);
		if(resultPage==null){
			return ResultUtil.returnResult(null, true);
		}
		return ResultUtil.returnResult(resultPage);
	}

//	获取试用活动详情
	@GET
	@POST
	@Path("/getTrialDetailById")
	@Override
	public JSONObject getTrialDetailById(@QueryParam("trialId")Long trialId) {
		Map<String,Object> resultMap=diyTrialService.getTrialDetailById(trialId);
		return ResultUtil.returnResult(resultMap);
	}

//	收藏试用活动
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/collectTrial")
	@Override
	public JSONObject collectTrial(@QueryParam("trialId")Long trialId) {
		int result =diyTrialService.collectTrial(trialId);
		return ResultUtil.returnByIntRow(result);
	}

//	取消试用收藏
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/cancelCollectTrial")
	@Override
	public JSONObject cancelCollectTrial(@QueryParam("trialId")Long trialId) {
		int result =diyTrialService.cancelCollectTrial(trialId);
		return ResultUtil.returnByIntRow(result);
	}
	
//	分页获取试用收藏列表----------个人中心页面
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/getCollectByPage")
	@Override
	public JSONObject getCollectByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize) {
		Page<Map> resultPage=diyTrialService.getCollectByPage(pageNum, pageSize);
		return ResultUtil.returnResult(resultPage);
	}


//	获取试用产品信息与用户信息---------公用
	@GET
	@POST
	@Role(value=Roles.Base)
	@Path("/getTrialProductAndUserInfo")
	@Override
	public JSONObject getTrialProductAndUserInfo(@QueryParam("trialId")Long trialId) {
		Map<String,Object> map =diyTrialService.getTrialProductAndUserInfo(trialId);
		return ResultUtil.returnResult(map);
	}	



	
	

}
