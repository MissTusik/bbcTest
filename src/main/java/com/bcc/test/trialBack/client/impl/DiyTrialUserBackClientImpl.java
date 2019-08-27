package com.bcc.test.trialBack.client.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.bcc.test.common.utils.Message;
import com.bcc.test.common.utils.Page;
import com.bcc.test.common.utils.ResultUtil;
import com.bcc.test.common.utils.StringUtil;
import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.web.client.DiyTrialUserBackClient;
import com.bcc.test.web.domain.DiySysLogBack;
import com.bcc.test.web.domain.DiyTrialApplicationSupper;
import com.bcc.test.web.service.DiySysLogBackService;
import com.bcc.test.web.service.DiyTrialApplicationBackService;
import com.bcc.test.web.service.DiyTrialBackService;
import io.terminus.pampas.common.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DiyTrialUserBackClientImpl
 * @Description:试用活动业务逻辑实现类
 * @author lily
 *
 */
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})//参数类型
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回值类型
@Path("/trialmgr/user")
public class DiyTrialUserBackClientImpl implements  DiyTrialUserBackClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private  DiyTrialBackService     diyTrialBackService;
	@Autowired
	private  DiyTrialApplicationBackService     diyTrialUserBackService;
	
	@Autowired
	private DiySysLogBackService diySysLogBackService;
	
//	分页获取试用申请用户列表
	@POST
	@GET
	@Path("/getTrialUserByPage")
	@Override
	public JSONObject getTrialUserByPage(
			@QueryParam("trialId")Long trialId,
			@QueryParam("pageNum")Integer pageNum, 
			@QueryParam("pageSize")Integer pageSize) {
		Page<Map> page=diyTrialUserBackService.getTrialUserByPage(trialId,pageNum, pageSize);
		return ResultUtil.returnResult(page);
	}
	
//	设置用户中签
	@POST
	@GET
	@Path("/updateTrialUserBallot")
	@Override
	public JSONObject updateTrialUserBallot(@QueryParam("ids")String ids) {
		diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"更新所选用户中签状态","用户申请ids:"+ids,"DiyTrialUserBackClientImpl.updateTrialUserBallot()"));
		logger.debug("into updateTrialUserBallot function...");
//		将字符串ids = "1,2,3"转成DiyTrialUser集合
		List<DiyTrialApplication> diyTrialUserList = this.convertToTrialUser(ids,1);
		if(diyTrialUserList.size() != 0)
			diyTrialUserBackService.updateTrialUserBallot(diyTrialUserList);
		return ResultUtil.returnResult();
	}
	
//	显示或隐藏申请理由
	@POST
	@GET
	@Path("/showAndhHiddenReason")
	@Override
	public JSONObject showAndhHiddenReason(@QueryParam("id")Long id,@QueryParam("isShowReason")String isShowReason) {
				diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"隐藏或显示用户申请理由","id:"+id+"isShowReason"+isShowReason,"DiyTrialUserBackClientImpl.showAndhHiddenReason()"));
		logger.debug("into showAnhHiddenReason function...");
		return ResultUtil.returnByIntRow(diyTrialUserBackService.showAndHiddenReason(id,isShowReason));
	}
	
//	删除试用申请用户
	@POST
	@GET
	@Path("/deleteTrialUser")
	@Override
	public JSONObject deleteTrialUser(@QueryParam("id")Long id) {
		diySysLogBackService.addLogBack(
		new DiySysLogBack(UserUtil.getUserId().intValue(),"删除试用申请","试用申请id:"+id,"DiyTrialUserBackClientImpl.deleteDiyTrialUser()"));
		logger.debug("into deleteTrialUser function..., id ==== " + id);
		return ResultUtil.returnByIntRow(diyTrialUserBackService.deleteTrialUser(id));
	}
	
	
//	分页获取指定活动中签用户列表
	@POST
	@GET
	@Path("/getBallotTrialUserByPage")
	@Override
	public JSONObject getBallotTrialUserByPage(
			@QueryParam("trialId")Long trialId,
			@QueryParam("isBallot")String isBallot,
			@QueryParam("pageNum")Integer pageNum, 
			@QueryParam("pageSize")Integer pageSize) {
		JSONObject json=new JSONObject();
		JSONObject result=diyTrialUserBackService.getTrialBallotUserByPage(trialId,isBallot,pageNum, pageSize);
		json.put(StringUtil.STATUS, 0);
		json.put(StringUtil.DATA, result);
		json.put(StringUtil.SUCCESS, true);
		json.put(StringUtil.EXCEPTION_MSG, StringUtil.SUCCESS);
		json.put(StringUtil.FILEDOMAIN, Message.ATTACHMENT_URL.getMsg());
		return json;
	}

//	用户公示
	@POST
	@GET
	@Path("/updateTrialUserPublic")
	@Override
	public JSONObject updateTrialUserPublic(@QueryParam("trialId")Long trialId,@QueryParam("validUserNum")Integer validUserNum, @QueryParam("ids")String ids) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"公示中签用户","试用活动id:"+trialId+",用户id:"+ids,"DiyTrialUserBackClientImpl.updateTrialUserPublic()"));

//		goodsList = "1,2,3";
		//将字符串转成DiyTrialData集合
		List<DiyTrialApplication> diyTrialUserList = this.convertToTrialUser(ids,2);
		if(diyTrialUserList.size() != 0)
			diyTrialUserBackService.updateTrialUserPublic(trialId,validUserNum,diyTrialUserList);
		return ResultUtil.returnResult();
	}

//	设置用户付款时间
	@POST
	@GET
	@Path("/updateTrialUserPayTime")
	@Override
	public JSONObject updateTrialUserPayTime(
			@QueryParam("id")Long id, 
			@QueryParam("payStartTime")String payStartTime,
			@QueryParam("payEndTime")String payEndTime) {
		diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"修付款日期试用活动","id:"+id+
			    "付款开始时间:"+payStartTime+",付款结束时间:"+payEndTime,"DiyTrialUserBackClientImpl.updateTrialUserPayTime()"));
		logger.debug("into updateTrialUserPayTime function...");
		return ResultUtil.returnByIntRow(diyTrialUserBackService.updateTrialUserPayTime(id, payStartTime,payEndTime));
	}
	
//	获取指定中签用户试用申请信息
	@POST
	@GET
	@Path("/getTrialUserById")
	@Override
	public JSONObject getTrialUserById(@QueryParam("id")Long id) {
		DiyTrialApplicationSupper diyTrialUserSupper = diyTrialUserBackService.getTrialUserById(id);
		return ResultUtil.returnResult(diyTrialUserSupper);
	}


	
//	修改申请快递信息
	@POST
	@GET
	@Path("/updateExpressInfo")
	@Override
	public JSONObject updateExpressInfo(@QueryParam("id")Long id, @QueryParam("expressCompany")String expressCompany, @QueryParam("expressNo")String expressNo) {
//		diySysLogBackService.addLogBack(
//				new DiySysLogBack(UserUtil.getUserId().intValue(),"启用/暂停试用活动","试用申请id:"+id+",快递公司:"+expressCompany+",快递单号:"+expressNo,"DiyTrialUserBackClientImpl.updateExpressInfo()"));
		logger.debug("into updateExpressInfo function...");
		return ResultUtil.returnByIntRow(diyTrialUserBackService.updateExpressInfo(id, expressCompany,expressNo));
	}
	/**
	 * 将ids转化为DiyTrialUser
	 * @param ids
	 * @param type    1:设置中签    2:设置公示
	 * @return
	 */
	private  List<DiyTrialApplication>  convertToTrialUser(String ids,Integer type){
		List<DiyTrialApplication>  list= new ArrayList<DiyTrialApplication>();
		if(StringUtil.isNotEmpty(ids)){
			String[] trialUserIds=ids.split(",");
				if(type==1){
					for(String id:trialUserIds){
						DiyTrialApplication  diyTrialUser=new DiyTrialApplication();
						diyTrialUser.setId(Long.valueOf(id));
						diyTrialUser.setIsBallot("Y");
						list.add(diyTrialUser);
					}
				}else{
					List<Long> idsList=new ArrayList<Long>();
					for(String str:trialUserIds){
						idsList.add(Long.valueOf(str));
					}
					list=diyTrialUserBackService.selectTrialUserByIds(idsList);
					if(list!=null&&list.size()!=0){
						for(DiyTrialApplication user:list ){
							user.setIsPublic("Y");
							user.setPublicTime(new Date());
						}
					}
				
				}
		}
		return list;
	}
}
