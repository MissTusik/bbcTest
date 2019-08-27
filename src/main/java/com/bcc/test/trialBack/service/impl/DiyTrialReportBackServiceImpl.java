package com.bcc.test.trialBack.service.impl;

import com.bcc.test.common.utils.BeanToMapUtil;
import com.bcc.test.common.utils.Message;
import com.bcc.test.common.utils.Page;
import com.bcc.test.common.utils.StringUtil;
import com.bcc.test.core.system.ServiceException;
import com.bcc.test.core.util.DIYSMSUtil;
import com.bcc.test.order.service.DiyOrderService;
import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trialBack.domain.DiyTrialReportSupper;
import com.bcc.test.trialBack.mapper.DiyTrialBackMapper;
import com.bcc.test.trialBack.mapper.DiyTrialReportBackMapper;
import com.bcc.test.trialBack.service.DiyTrialReportBackService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName:DiyTrialBackServiceImpl
 * @Description:试用报告业务逻辑实现类
 * @author lily
 * @date 2017-05-04
 */
@Service
public class DiyTrialReportBackServiceImpl implements DiyTrialReportBackService{
	private static final Log LOG = LogFactory.getLog(DiyTrialReportBackServiceImpl.class);
	
	@Autowired
	private DiyTrialBackMapper  diyTrialBackMapper;
	@Autowired
	private DiyTrialReportBackMapper  diyTrialReportBackMapper;
	@Autowired
	private DiyOrderService  diyOrderService;

//	分页获取所有公示的试用报告列表
	@Override
	public Page<Map> getPublicTrialReportByPage(Integer pageNum, Integer pageSize) {
		int total=diyTrialReportBackMapper.getPublicTrialReportCount(null);
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<Map<String,Object>> list=diyTrialReportBackMapper.getPublicTrialReportByPage(page.getStartItem(),page.getPageSize());
		if(list!=null&&list.size()>0){
//			判断各试用报告当前所处节点
			for(Map<String,Object> map:list){
				if(map.get("cover")!=null){
					map.put("cover",StringUtil.covertFullImg(map.get("cover").toString()));
				}
				map.put("url",StringUtil.URL_TRIAL_REPORT_FIX+map.get("id"));
			}
		}else{
			list=new ArrayList<Map<String,Object>>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		return page;
	}
	
//	分页获取指定试用报告所有报告列表
	@Override
	public JSONObject getTrialReportByPage(Long trialId,Integer pageNum, Integer pageSize) {
		if(trialId==null){
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
		}
		DiyTrial  diyTrial= diyTrialBackMapper.selectTrialById(trialId);
		if(diyTrial==null)
			throw new ServiceException(Message.ADMIN_2012001.getCode(), Message.ADMIN_2012001.getMsg() + ",trialId:" + trialId);
		
//		当前该试用活动已公示的报告数量
		 int publicReportNum=diyTrialReportBackMapper.getPublicTrialReportCount(trialId);
		
		int total=diyTrialReportBackMapper.getTrialReportCount(trialId);
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<Map<String,Object>> list=diyTrialReportBackMapper.getTrialReportByPage(trialId, page.getStartItem(),page.getPageSize());
		if(list!=null&&list.size()>0){
//			判断各试用报告当前所处节点
			for(Map<String,Object> map:list){
				if(map.get("cover")!=null){
					map.put("cover",StringUtil.covertFullImg(map.get("cover").toString()));
				}
			}
		}else{
			list=new ArrayList<Map<String,Object>>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		
		JSONObject  json=JSONObject.fromObject(page);
		json.put("trialNum", diyTrial.getTrialNum());					//申请数量标识
		json.put("publicReportNum", publicReportNum);					//已公示的试用报告数量标识
		return json;
	}
	

//	获取指定的试用报告信息
	@Override
	public DiyTrialReportSupper getTrialReportById(Long id) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		DiyTrialReportSupper diyTrialReportSupper = diyTrialReportBackMapper.selectTrialReportById(id);
		diyTrialReportSupper.setCover(StringUtil.covertFullImg(diyTrialReportSupper.getCover()));
		return diyTrialReportSupper;
	}

	
//	 审核试用报告
	@Override
	public DiyTrialReportSupper auditTrialReport(Long id, Integer type) {
		int result=0;
		// 校验参数
		if(id==null || type==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id+",type:"+type);
		DiyTrialReportSupper  diyTrialReportSupper=diyTrialReportBackMapper.selectTrialReportById(id);
		
		DiyTrial diyTrial=diyTrialBackMapper.selectTrialById(diyTrialReportSupper.getTrialId());
		if(diyTrial==null)
			throw new ServiceException(Message.ADMIN_2012001.getCode());
//		首先判断活动是否已经结束
		if(new Date().before(diyTrial.getEndTime())){//活动未结束
//			操作类型 type   1：合格   2允许修改    3：不合格
//			试用报告状态：0 未审核  1 合格  2允许修改   3不合格
//			判断试用报告是支付押金的 还是不支付押金的
			if("Y".equals(diyTrial.getIsChargeDeposit())){//---------------支付押金
//				判断试用报告当前状态     0和2允许操作
				if(diyTrialReportSupper.getStatus()==null||diyTrialReportSupper.getStatus()==0||diyTrialReportSupper.getStatus()==2){
					Boolean flag=false;
//					判断操作类型
					if(type==1){//---------合格
//						押金退回申请
						String reason="用户已提交试用报告，运营审核通过，根据活动规则为其退还押金。";
					    flag= diyOrderService.stageOrderRefund(diyTrialReportSupper.getHbaseOrderId(), reason,diyTrialReportSupper.getHbaseUserId());
//						推送短信并修改报告状态
						if(flag){
							diyTrialReportSupper.setStatus(1);
							result=diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReportSupper);
							sendAuditReportMsg(diyTrial.getIsChargeDeposit(), type, null, diyTrialReportSupper.getMobile());
						}
					}else if(type==2){//------允许修改
						diyTrialReportSupper.setStatus(2);
						result=diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReportSupper);
//						推送短信
						if(result!=0)
							sendAuditReportMsg(diyTrial.getIsChargeDeposit(), type,diyTrial.getEndTimeStr(), diyTrialReportSupper.getMobile());
					}else if(type==3){//------------不合格
//						扣除押金
						flag=diyOrderService.orderRetainageOverdue(diyTrialReportSupper.getHbaseOrderId());
//						推送短信 并修改报告状态
						if(flag){
							diyTrialReportSupper.setStatus(3);
							result=diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReportSupper);
							sendAuditReportMsg(diyTrial.getIsChargeDeposit(), type, null, diyTrialReportSupper.getMobile());
						}
					}
				}else{//不允许操作
					throw new ServiceException(Message.ADMIN_2012025.getCode());
				}			
			}else{//---------------不支付押金
//				判断试用报告当前状态     0和2允许操作
				if(diyTrialReportSupper.getStatus()==null||diyTrialReportSupper.getStatus()==0||diyTrialReportSupper.getStatus()==2){
//					判断操作类型
					if(type==1){//---------合格
						diyTrialReportSupper.setStatus(1);
					}else if(type==2){//------允许修改
						diyTrialReportSupper.setStatus(2);
					}else if(type==3){//------------不合格
						diyTrialReportSupper.setStatus(3);
					}
					result=diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReportSupper);
					if(result!=0){
						sendAuditReportMsg(diyTrial.getIsChargeDeposit(), type, diyTrial.getEndTimeStr(), diyTrialReportSupper.getMobile());
					}
				}else{//不允许操作
					throw new ServiceException(Message.ADMIN_2012025.getCode());
				}
			}
		}else{//活动已结束
			throw new ServiceException(Message.ADMIN_2012022.getCode());
		}
		
		return diyTrialReportSupper;
	}

//	更新试用报告排序
	@Override
	public int updateTrialReportSort(Long id, Integer sort) {
		if (id == null || sort==null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",sort:" + sort);
		}
		DiyTrialReport diyTrialReport = new DiyTrialReport();
		diyTrialReport.setId(id);
		diyTrialReport.setSort(sort);
		return diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReport);
	}
	
//	推荐指定的试用报告
	@Override
	public int recommendTrialReport(Long id, String isRecommend) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		DiyTrialReport diyTrialReport = new DiyTrialReport();
		diyTrialReport.setId(id);
		diyTrialReport.setIsRecommend(isRecommend);
		return diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReport);
	}
	
//	启用/禁用试用报告
	@Override
	public int onAndOffTrialReport(Long id, String isShow) {
		if (id == null || StringUtil.isEmpty(isShow)) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",isShow:" + isShow);
		}
		DiyTrialReport diyTrialReport = new DiyTrialReport();
		diyTrialReport.setId(id);
		diyTrialReport.setIsShow(isShow);
		return diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReport);
	}

//	删除试用报告
	@Override
	public int deleteTrialReport(Long id) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		return diyTrialReportBackMapper.deleteDiyTrialReport(id);
	}

//	上传试用报告封面图
	@Override
	public int uploadTrialReportCover(Long id, String cover) {
		if (id == null || StringUtil.isEmpty(cover)) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",cover:" + cover);
		}
		DiyTrialReport diyTrialReport = new DiyTrialReport();
		diyTrialReport.setId(id);
		diyTrialReport.setCover(cover);
		return diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReport);
	}

//	选择指定的试用报告列表
	@Override
	public List<DiyTrialReport> selectTrialReportByIds(List<Long> idsList) {
		return diyTrialReportBackMapper.selectTrialReportByIds(idsList);
	}

//	公示试用报告
	@Override
	public int publicTrialReport(Long trialId,
			List<DiyTrialReport> diyTrialReportList) {
		int result=0;
		
		DiyTrial diyTrial =diyTrialBackMapper.selectTrialById(trialId);
		if(diyTrial==null)
			throw new ServiceException(Message.ADMIN_2012001.getCode());
		
		int publicReportNum=diyTrialReportBackMapper.getPublicTrialReportCount(trialId);
		
//		判断已设置的公示报告是否超过了试用数量
		if(diyTrial.getTrialNum()>=(publicReportNum+diyTrialReportList.size())){//-------------未超过试用数量
			for(DiyTrialReport report:diyTrialReportList){
//				判断该报告的状态   合格报告才可以进行公示
				if(report.getStatus()!=null&& report.getStatus()==1){
					if("Y".equals(report.getIsPublic())){
						throw new ServiceException(Message.ADMIN_2012019.getCode(),Message.ADMIN_2012019.getMsg()+",报告id："+report.getId());
					}else{
						report.setIsPublic("Y");
						report.setPublicTime(new Date());
						report.setIsShow("Y");
					    result=diyTrialReportBackMapper.updateDiyTrialReport(report);
					}
				}else{
					throw new ServiceException(Message.ADMIN_2012018.getCode(),Message.ADMIN_2012018.getMsg()+",报告id："+report.getId());
				}
			}
		}else{//--------------超过了试用产品数量
			throw new ServiceException(Message.ADMIN_2012020.getCode());
		}
		
		return result;
	}
	/**
	 *发送审批短信
	 * @param actId    
	 * @author lily
	 * @date  2017-05-15
	 */
	private void   sendAuditReportMsg(String isChargeDeposit,Integer type,String date, String mobile){
//			
				List<String> params=new ArrayList<String>();
				String  messageName="";
				if("Y".equals(isChargeDeposit)){//---------支付押金的活动
					if(type==1){//合格
						messageName="deposit_report_qualified";
					}else if(type==2){//修改
						messageName="deposit_report_revise";
						params.add(date);
					}else if(type==3){//不合格
						messageName="deposit_report_unqualified";
					}
				}else{//-----------不支付押金的活动
					if(type==1){
						messageName="noDeposit_report_qualified";
					}else if(type==2){
						messageName="noDeposit_report_revise";
						params.add(date);
					}else if(type==3){
						messageName="noDeposit_report_unqualified";
					}
				}
				
				JSONObject  resultJson=DIYSMSUtil.sendMessageAndSave(mobile, messageName, null,1, params);
				String resultFlag=resultJson.get("success").toString();
				if(resultFlag.equals("true")){
					LOG.info(" 试用报告完成审核。接收手机 :" + mobile);
				}else{
					LOG.info("完成审批短信发送失败" + resultJson.get("msg").toString());
				}					
	}
	/**
	 *添加/修改购买链接
	 * @param id
	 * @param pcUrl
	 * @param mUrl    
	 * @author lily
	 * @date  2018-04-16
	 */
	@Override
	public int updateTrialReportBuyUrl(Long id, String pcUrl, String mUrl) {
		if (id == null ) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		if(StringUtil.isEmpty(pcUrl)&&StringUtil.isEmpty(mUrl)){
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",pcUrl:" + pcUrl+"mUrl:"+mUrl);
		}
		DiyTrialReport diyTrialReport = new DiyTrialReport();
		diyTrialReport.setId(id);
		diyTrialReport.setPcUrl(pcUrl);
		diyTrialReport.setmUrl(mUrl);;
		return diyTrialReportBackMapper.updateDiyTrialReport(diyTrialReport);
	}





}
