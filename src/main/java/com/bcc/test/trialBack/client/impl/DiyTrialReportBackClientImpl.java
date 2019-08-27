package com.bcc.test.trialBack.client.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.haier.diy.common.utils.Message;
import com.haier.diy.common.utils.Page;
import com.haier.diy.common.utils.ResultUtil;
import com.haier.diy.common.utils.StringUtil;
import com.haier.diy.trial.domain.DiyTrialReport;
import com.haier.diy.web.client.DiyTrialReportBackClient;
import com.haier.diy.web.domain.DiySysLogBack;
import com.haier.diy.web.domain.DiyTrialReportSupper;
import com.haier.diy.web.service.DiySysLogBackService;
import com.haier.diy.web.service.DiyTrialReportBackService;
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
 * ClassName:DiyTrialReportBackClientImpl
 * @Description:试用报告业务逻辑实现类
 * @author lily
 *
 */
@Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_PLAIN_UTF_8})//参数类型
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})//返回值类型
@Path("/trialmgr/report")
public class DiyTrialReportBackClientImpl implements  DiyTrialReportBackClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private  DiyTrialReportBackService     diyTrialReportBackService;
	
	@Autowired
	private DiySysLogBackService diySysLogBackService;
	
//	分页获取所有公示的试用报告列表
	@POST
	@GET
	@Path("/getPublicTrialReportByPage")
	@Override
	public JSONObject getPublicTrialReportByPage(@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize) {
		Page<Map> page=diyTrialReportBackService.getPublicTrialReportByPage(pageNum, pageSize);
		return ResultUtil.returnResult(page);
	}

//	修改试用报告排序
	@POST
	@GET
	@Path("/updateTrialReportSort")
	@Override
	public JSONObject updateTrialReportSort(@QueryParam("id")Long id, @QueryParam("sort")Integer sort) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"试用报告排序","试用报告id:"+id+",序号:"+sort,"DiyTrialReportBackClientImpl.updateTrialReportSort()"));
		return ResultUtil.returnByIntRow(diyTrialReportBackService.updateTrialReportSort(id, sort));
	}
	
//	启用/禁用试用报告
	@POST
	@GET
	@Path("/onAndOffTrialReport")
	@Override
	public JSONObject onAndOffTrialReport(@QueryParam("id")Long id, @QueryParam("isShow")String isShow) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"启用/暂停试用报告","试用报告id:"+id+",状态:"+isShow,"DiyTrialReportBackClientImpl.onAndOffTrialReport()"));
		logger.debug("into onAndOffTrialReport function...");
		return ResultUtil.returnByIntRow(diyTrialReportBackService.onAndOffTrialReport(id, isShow));
	}
	
//	推荐/不推荐试用报告
	@POST
	@GET
	@Path("/recommendTrialReport")
	@Override
	public JSONObject recommendTrialReport(@QueryParam("id")Long id, @QueryParam("isRecommend")String isRecommend) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"推荐/不推荐试用报告","试用报告id:"+id+",状态:"+isRecommend,"DiyTrialReportBackClientImpl.onAndOffTrialReport()"));
		logger.debug("into recommendTrialReport function...");
		return ResultUtil.returnByIntRow(diyTrialReportBackService.recommendTrialReport(id, isRecommend));
	}

//	删除试用报告
	@POST
	@GET
	@Path("/deleteTrialReport")
	@Override
	public JSONObject deleteTrialReport(@QueryParam("id")Long id) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"删除试用报告","试用报告id:"+id,"DiyTrialReportBackClientImpl.deleteTrialReport()"));
		logger.debug("into deleteTrialReport function..., id ==== " + id);
		return ResultUtil.returnByIntRow(diyTrialReportBackService.deleteTrialReport(id));
	}
	
//	分页获取指定试用活动的所有试用报告列表
	@POST
	@GET
	@Path("/getTrialReportByPage")
	@Override
	public JSONObject getTrialReportByPage(@QueryParam("trialId")Long trialId,@QueryParam("pageNum")Integer pageNum, @QueryParam("pageSize")Integer pageSize) {
		JSONObject json=new JSONObject();
		JSONObject result=diyTrialReportBackService.getTrialReportByPage(trialId, pageNum, pageSize);
		json.put(StringUtil.STATUS, 0);
		json.put(StringUtil.DATA, result);
		json.put(StringUtil.SUCCESS, true);
		json.put(StringUtil.EXCEPTION_MSG, StringUtil.SUCCESS);
		json.put(StringUtil.FILEDOMAIN, Message.ATTACHMENT_URL.getMsg());
		return json;
	}

	
//	获取指定试用报告信息
	@POST
	@GET
	@Path("/getTrialReportById")
	@Override
	public JSONObject getTrialReportById(@QueryParam("id")Long id) {
		DiyTrialReportSupper diyTrialReportSupper = diyTrialReportBackService.getTrialReportById(id);
		return ResultUtil.returnResult(diyTrialReportSupper);
	}

//	上传封面图
	@POST
	@GET
	@Path("/uploadTrialReportCover")
	@Override
	public JSONObject uploadTrialReportCover(@QueryParam("id")Long id, @QueryParam("cover")String cover) {
		diySysLogBackService.addLogBack(
		new DiySysLogBack(UserUtil.getUserId().intValue(),"上传试用报告封面图","试用报告id:"+id+",封面图:"+cover,"DiyTrialReportBackClientImpl.uploadTrialReportCover()"));
		logger.debug("into uploadTrialReportCover function...");
		return ResultUtil.returnByIntRow(diyTrialReportBackService.uploadTrialReportCover(id, cover));
	}

//	审批试用报告
	@POST
	@GET
	@Path("/auditTrialReport")
	@Override
	public JSONObject auditTrialReport(@QueryParam("id")Long id, @QueryParam("type")Integer type) {
		diySysLogBackService.addLogBack(
		new DiySysLogBack(UserUtil.getUserId().intValue(),"审批试用报告","试用报告id:"+id+",审批类型:"+type,"DiyTrialReportBackClientImpl.auditTrialReport()"));
		logger.debug("into auditTrialReport function...");
		DiyTrialReportSupper diyTrialReportSupper = diyTrialReportBackService.auditTrialReport(id, type);
		return ResultUtil.returnResult(diyTrialReportSupper);
	}

//	公示试用报告
	@POST
	@GET
	@Path("/publicTrialReport")
	@Override
	public JSONObject publicTrialReport(@QueryParam("trialId")Long trialId, @QueryParam("ids")String ids) {
		diySysLogBackService.addLogBack(
		new DiySysLogBack(UserUtil.getUserId().intValue(),"公示试用报告","试用活动id:"+trialId+",试用报告ids:"+ids,"DiyTrialReportBackClientImpl.publicTrialReport()"));
		logger.debug("into publicTrialReport function...");
		List<DiyTrialReport> diyTrialReportList = this.convertToTrialReport(ids);
		if(diyTrialReportList.size() != 0)
			diyTrialReportBackService.publicTrialReport(trialId,diyTrialReportList);
		return ResultUtil.returnResult();
	}
	
//	
	/**
	 * 将ids转化为DiyTrialUser
	 * @param ids
	 * @param type    1:设置中签    2:设置公示
	 * @return
	 */
	private  List<DiyTrialReport>  convertToTrialReport(String ids){
		List<DiyTrialReport>  list= new ArrayList<DiyTrialReport>();
		if(StringUtil.isNotEmpty(ids)){
			String[] trialReportIds=ids.split(",");
					List<Long> idsList=new ArrayList<Long>();
					for(String id:trialReportIds){
						idsList.add(Long.valueOf(id));
					}
					list=diyTrialReportBackService.selectTrialReportByIds(idsList);
		}
		return list;
	}

//	试用报告添加购买链接
	@Override
	public JSONObject updateTrialReportBuyUrl(@QueryParam("id")Long id, @QueryParam("pcUrl")String pcUrl, @QueryParam("mUrl")String mUrl) {
		diySysLogBackService.addLogBack(
				new DiySysLogBack(UserUtil.getUserId().intValue(),"试用报告添加购买链接","试用报告id:"+id+",pc端购买链接:"+pcUrl+"移动端购买链接:"+mUrl,
						"DiyTrialReportBackClientImpl.addTrialReportBuyUrl()"));
				logger.debug("into addTrialReportBuyUrl function...");
				return ResultUtil.returnByIntRow(diyTrialReportBackService.updateTrialReportBuyUrl(id, pcUrl,mUrl));
	}






}
