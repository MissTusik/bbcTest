package com.bcc.test.trialBack.client.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.bcc.test.common.utils.DateUtil;
import com.bcc.test.common.utils.Page;
import com.bcc.test.common.utils.ResultUtil;
import com.bcc.test.common.utils.StringUtil;
import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialData;
import com.bcc.test.web.client.DiyTrialBackClient;
import com.bcc.test.web.domain.DiySysLogBack;
import com.bcc.test.web.domain.DiyTrialSupper;
import com.bcc.test.web.service.DiySysLogBackService;
import com.bcc.test.web.service.DiyTrialBackService;
import io.terminus.pampas.common.UserUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DiyTrialBackClientImpl
 * @Description:试用活动业务逻辑实现类
 * @author lily
 *
 */
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})//参数类型
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回值类型
@Path("/trialmgr/trial")
public class DiyTrialBackClientImpl implements  DiyTrialBackClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private  DiyTrialBackService     diyTrialBackService;
	
	@Autowired
	private DiySysLogBackService diySysLogBackService;
	
//	分页获取试用活动列表
	@POST
	@GET
	@Path("/getTrialByPage")
	@Override
	public JSONObject getTrialByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize) {
		Page<Map> page=diyTrialBackService.getTrialByPage(pageNum, pageSize);
		return ResultUtil.returnResult(page);
	}

//	新增试用活动
	@POST
	@GET
	@Path("/addTrial")
	@Override
	public JSONObject addTrial(
			@QueryParam("productId")Long productId, 
			@QueryParam("trialNum")Integer trialNum,
			@QueryParam("isChargeDeposit")String isChargeDeposit,
			@QueryParam("pcCover")String pcCover, 
			@QueryParam("mCover")String mCover,
			@QueryParam("base")Integer base, 
			@QueryParam("status")Integer status, 
			@QueryParam("startTime")String startTime,
			@QueryParam("preheatEndTime")String preheatEndTime, 
			@QueryParam("applyEndTime")String applyEndTime, 
			@QueryParam("endTime")String endTime) {
		diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"新增试用活动","试用产品id:"+productId,"DiyTrialBackClientImpl.addTrial()"));
		logger.debug("into addTrial function...");
//		goodsList = "1,2,3";
		DiyTrial diyTrial = new DiyTrial(productId, trialNum, pcCover, mCover, base, 
				DateUtil.formatStringToDate(startTime), DateUtil.formatStringToDate(preheatEndTime),
				DateUtil.formatStringToDate(applyEndTime), DateUtil.formatStringToDate(endTime), isChargeDeposit, null, status);
		return ResultUtil.returnResult(diyTrialBackService.addTrial(diyTrial));
	}

//	修改试用活动信息
	@POST
	@GET
	@Path("/updateTrial")
	@Override
	public JSONObject updateTrial(
			@QueryParam("id")Long id, 
			@QueryParam("productId")Long productId, 
			@QueryParam("trialNum")Integer trialNum,
			@QueryParam("isChargeDeposit")String isChargeDeposit,
			@QueryParam("pcCover")String pcCover, 
			@QueryParam("mCover")String mCover,
			@QueryParam("base")Integer base, 
			@QueryParam("status")Integer status, 
			@QueryParam("startTime")String startTime,
			@QueryParam("preheatEndTime")String preheatEndTime, 
			@QueryParam("applyEndTime")String applyEndTime, 
			@QueryParam("endTime")String endTime) {
		diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"修改试用活动","id:"+id+
			    "商品id:"+productId+",试用数量:"+trialNum,"DiyTrialBackClientImpl.updateTrial()"));
		logger.debug("into updateTrial function...");
		DiyTrial diyTrial = new DiyTrial(id,productId, trialNum, pcCover, mCover, base, 
				DateUtil.formatStringToDate(startTime), DateUtil.formatStringToDate(preheatEndTime),
				DateUtil.formatStringToDate(applyEndTime), DateUtil.formatStringToDate(endTime), isChargeDeposit, null, status);
		return ResultUtil.returnByIntRow(diyTrialBackService.updateTrial(diyTrial));
	}
	
//	更新试用活动规则
	@POST
	@GET
	@Path("/updateTrialRule")
	@Override
	public JSONObject updateTrialRule(Long id, String rule) {
		diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"修改试用活动规则","id:"+id+
			    ",活动规则:"+rule,"DiyTrialBackClientImpl.updateTrialRule()"));
		logger.debug("into updateTrialData function...");
		return ResultUtil.returnByIntRow(diyTrialBackService.updateTrialRule(id, rule));
	}

//	更新试用活动推荐商品信息
	@POST
	@GET
	@Path("/updateTrialData")
	@Override
	public JSONObject updateTrialData(Long id, String goodsList) {
		diySysLogBackService.addLogBack(
			    new DiySysLogBack(UserUtil.getUserId().intValue(),"修改试用活动规则","id:"+id+
			    ",推荐商品:"+goodsList,"DiyTrialBackClientImpl.updateTrialData()"));
		logger.debug("into updateTrialData function...");
//		goodsList = "1,2,3";
//		将字符串转成DiyTrialData集合
		List<DiyTrialData> diyTrialDataList = this.convertToTrialData(goodsList);
		if(diyTrialDataList.size() != 0)
			diyTrialBackService.updateTrialData(id, diyTrialDataList);
		if(diyTrialDataList.size()==0)
			diyTrialBackService.deleteTrialDataByTrialId(id);
		return ResultUtil.returnResult();
	}
	
//	获取指定试用活动信息
	@POST
	@GET
	@Path("/getTrialById")
	@Override
	public JSONObject getTrialById(@QueryParam("id")Long id) {
		DiyTrialSupper diyTrialSupper = diyTrialBackService.getTrialById(id);
		return ResultUtil.returnResult(diyTrialSupper);
	}

//	修改试用活动排序
	@POST
	@GET
	@Path("/updateTrialSort")
	@Override
	public JSONObject updateTrialSort(@QueryParam("id")Long id, @QueryParam("sort")Integer sort) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"试用活动排序","试用活动id:"+id+",序号:"+sort,"DiyTrialBackClientImpl.updateTrialSort()"));
		return ResultUtil.returnByIntRow(diyTrialBackService.updateTrialSort(id, sort));
	}
	
//	启用/禁用试用活动
	@POST
	@GET
	@Path("/onAndOffTrial")
	@Override
	public JSONObject onAndOffTrial(@QueryParam("id")Long id, @QueryParam("status")Integer status) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"启用/暂停试用活动","试用活动id:"+id+",状态:"+status,"DiyTrialBackClientImpl.onAndOffTrial()"));
		logger.debug("into onAndOffTrial function...");
		return ResultUtil.returnByIntRow(diyTrialBackService.onAndOffTrial(id, status));
	}

//	删除试用活动
	@POST
	@GET
	@Path("/deleteTrial")
	@Override
	public JSONObject deleteTrial(@QueryParam("id")Long id) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"删除试用活动","试用活动id:"+id,"DiyTrialBackClientImpl.deleteTrial()"));
		logger.debug("into deleteTrial function..., id ==== " + id);
		return ResultUtil.returnByIntRow(diyTrialBackService.deleteTrial(id));
	}

	private  List<DiyTrialData>  convertToTrialData(String goodsList){
		List<DiyTrialData>  list= new ArrayList<DiyTrialData>();
		if(StringUtil.isNotEmpty(goodsList)){
			String[] goodIds=goodsList.split(",");
			for(String goodId:goodIds){
				DiyTrialData  diyTrialData=new DiyTrialData(null, Long.valueOf(goodId));
				list.add(diyTrialData);
			}
		}
		return list;
	}



}
