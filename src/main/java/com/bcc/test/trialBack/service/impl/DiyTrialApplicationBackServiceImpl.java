package com.bcc.test.trialBack.service.impl;

import com.bcc.test.common.utils.*;
import com.bcc.test.core.service.DiyUserService;
import com.bcc.test.core.system.ServiceException;
import com.bcc.test.core.util.DIYSMSUtil;
import com.bcc.test.designer.service.DiyProductModelValueService;
import com.bcc.test.order.service.DiyOrderService;
import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.trial.domain.DiyTrialApplicationCus;
import com.bcc.test.util.SyncUtil;
import com.bcc.test.trialBack.domain.DiyTrialApplicationSupper;
import com.bcc.test.trialBack.domain.DiyTrialSupper;
import com.bcc.test.trialBack.mapper.DiyTrialApplicationBackMapper;
import com.bcc.test.trialBack.mapper.DiyTrialBackMapper;
import com.bcc.test.trialBack.service.DiyTrialApplicationBackService;
import net.sf.json.JSONArray;
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
 * ClassName:DiyTrialUserBackServiceImpl
 * @Description:试用活动申请用户业务逻辑实现类
 * @author lily
 * @date 2017-05-09
 */
@Service
public class DiyTrialApplicationBackServiceImpl implements DiyTrialApplicationBackService{
	
	private static final Log LOG = LogFactory.getLog(DiyTrialApplicationBackServiceImpl.class);
	
	@Autowired
	private DiyTrialBackMapper  diyTrialBackMapper;
	@Autowired
	private DiyTrialApplicationBackMapper  diyTrialApplicationBackMapper;
	@Autowired
	private DiyProductModelValueService diyProductModelValueService;
	@Autowired
	private DiyUserService diyUserService;
	@Autowired
	private DiyOrderService diyOrderService;

//	分页获取指定试用活动的全部申请用户列表
	@Override
	public Page<Map> getTrialUserByPage(Long trialId, Integer pageNum, Integer pageSize) {
		if (trialId == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
		}
		int total=diyTrialApplicationBackMapper.getTrialUserCount(trialId,null);
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<Map<String,Object>> list=diyTrialApplicationBackMapper.getTrialUserByPage(trialId,page.getStartItem(),page.getPageSize());
		if(list!=null&&list.size()>0){
			for(Map<String,Object> map:list){
				map.put("headImg",
						StringUtil.covertFullImg(map.get("headImg")==null?"":String.valueOf(map.get("headImg"))));
			}
		}else{
			list=new ArrayList<Map<String,Object>>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		return page;
	}
	
//	修改用户中签状态
	@Override
	public int updateTrialUserBallot(List<DiyTrialApplication> diyTrialUserList) {
		int result=0;
		for (DiyTrialApplication diyTrialUser : diyTrialUserList) {
			if (StringUtil.isEmpty(diyTrialUser.getIsBallot())) {
				throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + diyTrialUser.getId());
			}
			result = diyTrialApplicationBackMapper.updateDiyTrialUser(diyTrialUser);
		}
		return result;
	}

//	删除用户申请------只是针对
	@Override
	public int deleteTrialUser(Long id) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		return diyTrialApplicationBackMapper.deleteDiyTrialUser(id);
	}
	
//	分页获取中签用户列表
	@Override
	public JSONObject getTrialBallotUserByPage(Long trialId, String isBallot, Integer pageNum, Integer pageSize) {
		if (trialId == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
		}
		if(StringUtil.isEmpty(isBallot))
			isBallot="Y";
		
		DiyTrialSupper  diyTrialSupper=diyTrialBackMapper.selectTrialSupperById(trialId);
		if(diyTrialSupper==null)
			throw new ServiceException(Message.ADMIN_2012001.getCode(), Message.ADMIN_2012001.getMsg() + ",trialId:" + trialId);
		
//		当前有效用户数
		int validUserNum=0;
		int total=diyTrialApplicationBackMapper.getTrialUserCount(trialId,isBallot);
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<DiyTrialApplicationSupper> list=diyTrialApplicationBackMapper.getTrialBallotUserByPage(trialId,isBallot,page.getStartItem(),page.getPageSize());
		if(list!=null&&list.size()>0){
			for(DiyTrialApplicationSupper diyTrialUserSupper:list){
				diyTrialUserSupper.setHeadImg(StringUtil.covertFullImg(diyTrialUserSupper.getHeadImg()));
//				判断用户状态（0无   1正常   2失效）    第一步：是否公示
				if("Y".equals(diyTrialUserSupper.getIsPublic())){//公示的
//					申请活动有无押金
					if("Y".equals(diyTrialSupper.getIsChargeDeposit())){//--------------有押金
//						判断依据说明    	正常状态1：订单已付款、超期未付尾款、已退款    付款时间内未下单或者下单未付款     无效2：付款时间内取消订单、超过付款时间未下单或下单未付款
//						判断是否下单
						if(StringUtil.isEmpty(diyTrialUserSupper.getOrderStatus())){//--------------未下单           判断是否在付款时间范围内
							Date currentTime=new Date();
							if(currentTime.before(diyTrialSupper.getEndTime())&&currentTime.before(diyTrialUserSupper.getPayEndTime())){//当前时间在付款时间内且在活动结束前
								diyTrialUserSupper.setStatus(1);		//状态正常
								validUserNum+=1;
							}else{//不在付款时间内或者活动已经结束
								diyTrialUserSupper.setStatus(2);		//状态无效
							}
						}else{//----------------------已下单
//							判断是否已付款
							if(diyTrialUserSupper.getOrderStatus().equals("99")){//----已付押金
								diyTrialUserSupper.setStatus(1);		//状态正常
								validUserNum+=1;	
							}else if(diyTrialUserSupper.getReportStatus()!=null&&diyTrialUserSupper.getReportStatus()==3&&diyTrialUserSupper.getOrderStatus().equals("15")){//----报告不合格且扣除押金
								diyTrialUserSupper.setStatus(1);		//状态正常
								validUserNum+=1;	
							}else if(diyTrialUserSupper.getReportStatus()!=null&&diyTrialUserSupper.getReportStatus()==1&&diyTrialUserSupper.getOrderStatus().equals("-1")){//----报告合格且申请退款
								diyTrialUserSupper.setStatus(1);		//状态正常
								validUserNum+=1;	
							}else if(diyTrialUserSupper.getReportStatus()!=null&&diyTrialUserSupper.getReportStatus()==1&&diyTrialUserSupper.getOrderStatus().equals("-10")){//---报告合格且退款成功
								diyTrialUserSupper.setStatus(1);		//状态正常
								validUserNum+=1;	
							}else{
//								判断是否在付款时间内
								Date currentTime=new Date();
								if(currentTime.before(diyTrialSupper.getEndTime())&&currentTime.before(diyTrialUserSupper.getPayEndTime())){//-----------当前时间在付款时间内且在活动结束前
									if(diyTrialUserSupper.getOrderStatus().equals("0")){	//未付款的
										diyTrialUserSupper.setStatus(1);		//状态正常
										validUserNum+=1;
									}else{//
										diyTrialUserSupper.setStatus(2);		//状态无效
									}
								}else{
									diyTrialUserSupper.setStatus(2);		//状态无效
								}	
							}
						}
					}else{//---------------无押金
//						判断有无收货地址
						if(diyTrialUserSupper.getAddressId()!=null){//-----------有收货地址
							diyTrialUserSupper.setStatus(1);		//状态正常
							validUserNum+=1;
						}else{//--------------无收货地址
//							判断是否在公示后48小时以内  且有无收货地址
							Date currentTime=new Date();
							if(currentTime.before(diyTrialSupper.getEndTime())&&(DateUtil.getDiffHours(diyTrialUserSupper.getPublicTime(),currentTime)<=48)){//-----------48小时内且在活动结束前
									diyTrialUserSupper.setStatus(1);		//状态正常
									validUserNum+=1;
							}else {//---------------48小时以外或者活动已经结束
								diyTrialUserSupper.setStatus(2);		//状态无效
							}	
						}
					}
				}else{//未公示的
					diyTrialUserSupper.setStatus(0);
				}
			}
			
		}else{
			list=new ArrayList<DiyTrialApplicationSupper>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		JSONObject json=JSONObject.fromObject(page);
		json.put("trialId", diyTrialSupper.getId());						//试用活动id
		json.put("isChargeDeposit", diyTrialSupper.getIsChargeDeposit());	//支付押金标识
		json.put("trialNum", diyTrialSupper.getTrialNum());					//申请数量标识
		json.put("validUserNum", validUserNum);								//有效用户数量标识
//		如果是支付押金的试用
		if(diyTrialSupper.getIsChargeDeposit().equals("Y")){
//			获取预售商品的开始时间与结束时间
//			众创商品模型信息
			Map<String,Object> modelMap=diyProductModelValueService.getProModelValue(diyTrialSupper.getProductId());
			json.put("startTime", modelMap.get("startTime"));					//预售开始时间
			json.put("endTime", modelMap.get("endTime"));						//预售结束时间
		}else{
			json.put("startTime", DateUtil.formatDateToString(diyTrialSupper.getStartTime()));				//活动开始时间
			json.put("endTime", DateUtil.formatDateToString(diyTrialSupper.getEndTime()));					//活动结束时间	
		}
		return json;
	}

//	修改中签用户公示状态
	@Override
	public int updateTrialUserPublic(Long trialId,Integer validUserNum,List<DiyTrialApplication> diyTrialUserList) {
		int result=0;
		
//		获取试用活动     
		DiyTrial diyTrial=diyTrialBackMapper.selectTrialSupperById(trialId);
		if(diyTrial==null)
			throw new ServiceException(Message.ADMIN_2012001.getCode());
		
//	    判断是否为支付押金活动，超过活动时间 不允许再进行公示
		Date currentTime=new Date();
		if("Y".equals(diyTrial.getIsChargeDeposit())){//-------------支付押金
			Map<String,Object> modelMap=diyProductModelValueService.getProModelValue(diyTrial.getProductId());
			Date startTime=DateUtil.formatStringToDate(modelMap.get("startTime")==null?"":String.valueOf(modelMap.get("startTime")));
			Date endTime=DateUtil.formatStringToDate(modelMap.get("endTime")==null?"":String.valueOf(modelMap.get("endTime")));
			if(currentTime.before(startTime)||currentTime.after(endTime))
				throw new ServiceException(Message.ADMIN_2012016.getCode());
		}else{//---------------不支付押金
			if(currentTime.after(diyTrial.getEndTime()))
				throw new ServiceException(Message.ADMIN_2012015.getCode());
		}
//		判断已设置的公示有效用户数是否超过了试用数量
		if(diyTrial.getTrialNum()>validUserNum){//----------人数小于试用数量
//			判断现添加人数+已有人数 是否超过试用数量
			if(diyTrial.getTrialNum()>=(validUserNum+diyTrialUserList.size())){//-------------公示总人数未超过试用产品数量
				for (DiyTrialApplication application : diyTrialUserList) {
					if("Y".equals(diyTrial.getIsChargeDeposit())){
						if(application.getPayStartTime()==null||application.getPayEndTime()==null)
							throw new ServiceException(Message.ADMIN_2012017.getCode(),Message.ADMIN_2012017.getMsg()+"用户申请id:"+application.getId());
					}
					result = diyTrialApplicationBackMapper.updateDiyTrialUser(application);
					if(result!=0){
						sendPubishUserMsg(diyTrial.getIsChargeDeposit(),application.getPayEndTimeStr(),application.getMobile());
					}
				}
			}else{
				throw new ServiceException(Message.ADMIN_2012014.getCode());
			}
		}else{//人数等于或大于试用数量
			throw new ServiceException(Message.ADMIN_2012013.getCode());
		}
		return result;
	}

//	获取指定的试用活动信息
	@Override
	public DiyTrialApplicationSupper getTrialUserById(Long id) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		DiyTrialApplicationSupper diyTrialUserSupper = diyTrialApplicationBackMapper.selectDiyTrialUserById(id);
		diyTrialUserSupper.setHeadImg(StringUtil.covertFullImg(diyTrialUserSupper.getHeadImg()));
//		判断用户公示    0  未付款   1 已付款    2 已超时
//		对于显示物流信息
//		支付押金：   0和2  不显示任何物流信息      1：物流单号为空显示选择物流公司     物流单号不为空 显示物流信息
//		不支付押：   0和2  不显示任务物流信息      1：物流单号为空选择物流公司             物流单号不为空 显示物流信息   
			if("Y".equals(diyTrialUserSupper.getIsPublic())){//公示的
//				申请活动有无押金
				if("Y".equals(diyTrialUserSupper.getIsChargeDeposit())){//--------------有押金
//					判断依据说明    	正常状态1：订单已付款、超期未付尾款、已退款    付款时间内未下单或者下单未付款     无效2：付款时间内取消订单、超过付款时间未下单或下单未付款
//					判断是否下单
					if(StringUtil.isEmpty(diyTrialUserSupper.getOrderStatus())){//--------------未下单           判断是否在付款时间范围内
						Date currentTime=new Date();
						if(currentTime.after(diyTrialUserSupper.getPayStartTime())&&currentTime.before(diyTrialUserSupper.getPayEndTime())){//当前时间在付款时间内
							diyTrialUserSupper.setStatus(0);		//未下单未付款
						}else{
							diyTrialUserSupper.setStatus(2);		//已超时
						}
					}else{//----------------------已下单
//						判断是否已付款
						if(diyTrialUserSupper.getOrderStatus().equals("99")){//----已付押金
							diyTrialUserSupper.setStatus(1);		//已下单（已付款）-----------给出物流信息
							if(StringUtil.isEmpty(diyTrialUserSupper.getExpressCompany())||StringUtil.isEmpty(diyTrialUserSupper.getExpressNo())){
								diyTrialUserSupper.setExpressCompanyList(getExpressCompanyList());
							}
						}else if(diyTrialUserSupper.getReportStatus()!=null&&diyTrialUserSupper.getReportStatus()==3&&diyTrialUserSupper.getOrderStatus().equals("15")){//----报告不合格且扣除押金
							diyTrialUserSupper.setStatus(1);		//已下单（已付款）
						}else if(diyTrialUserSupper.getReportStatus()!=null&&diyTrialUserSupper.getReportStatus()==1&&diyTrialUserSupper.getOrderStatus().equals("-1")){//----报告合格且申请退款
							diyTrialUserSupper.setStatus(1);		//已下单（已付款）
						}else if(diyTrialUserSupper.getReportStatus()!=null&&diyTrialUserSupper.getReportStatus()==1&&diyTrialUserSupper.getOrderStatus().equals("-10")){//---报告合格且退款成功
							diyTrialUserSupper.setStatus(1);		//已下单（已付款）
						}else{
//							判断是否在付款时间内
							Date currentTime=new Date();
							if(currentTime.after(diyTrialUserSupper.getPayStartTime())&&currentTime.before(diyTrialUserSupper.getPayEndTime())){//-----------当前时间在付款时间内
								if(diyTrialUserSupper.getOrderStatus().equals("0")){	//未付款的
									diyTrialUserSupper.setStatus(0);		//已下单未付款
								}else{//
									diyTrialUserSupper.setStatus(2);		//已超时
								}
							}else{
								diyTrialUserSupper.setStatus(2);		//已超时
							}	
						}
					}
				}else{//---------------无押金
//					判断有无收货地址
					if(diyTrialUserSupper.getAddressId()!=null){//-----------有收货地址
						diyTrialUserSupper.setStatus(1);		//未超时
						if(StringUtil.isEmpty(diyTrialUserSupper.getExpressCompany())||StringUtil.isEmpty(diyTrialUserSupper.getExpressNo())){
							diyTrialUserSupper.setExpressCompanyList(getExpressCompanyList());
						}
					}else{//--------------无收货地址
//						判断是否在公示后48小时以内  且有无收货地址
						Date currentTime=new Date();
						if(DateUtil.getDiffHours(diyTrialUserSupper.getPublicTime(),currentTime)<=48){//-----------48小时内
								diyTrialUserSupper.setStatus(1);		//未超时
						}else {//---------------48小时以外
							diyTrialUserSupper.setStatus(2);		//已超时
						}	
					}
				}
			}else{//未公示的
				diyTrialUserSupper.setStatus(0);
			}
		return diyTrialUserSupper;
	}
	
	
//	设置试用申请付款时间
	@Override
	public int updateTrialUserPayTime(Long id,  String payStartTime,String payEndTime) {
		if (id == null || StringUtil.isEmpty(payStartTime)||StringUtil.isEmpty(payEndTime)) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",payStartTime:" + payStartTime+",payEndTime"+payEndTime);
		}
		Date payStart=DateUtil.formatStringToDate(payStartTime);
		Date payEnd=DateUtil.formatStringToDate(payEndTime);
//		判断付款结束时间是否小于付款开始时间
		if(payEnd.before(payStart))
			throw new ServiceException(Message.ADMIN_2012010.getCode(), Message.ADMIN_2012010.getMsg());
		if(payEnd.before(new Date()))
			throw new ServiceException(Message.ADMIN_2012023.getCode(), Message.ADMIN_2012023.getMsg());
		
		DiyTrialApplication trialUser=diyTrialApplicationBackMapper.getDiyTrialUserById(id);
		if(trialUser==null)
			throw new ServiceException(Message.ADMIN_2012024.getCode(), Message.ADMIN_2012024.getMsg() + ",id:" + id );
		DiyTrial diyTrial=diyTrialBackMapper.selectTrialSupperById(trialUser.getTrialId());
		if(diyTrial==null)
			throw new ServiceException(Message.ADMIN_2012001.getCode(), Message.ADMIN_2012001.getMsg() + ",id:" + id );
//		判断付款时间是否在预售时间之内
		if(diyTrial.getIsChargeDeposit().equals("Y")){
			Map<String,Object> modelMap=diyProductModelValueService.getProModelValue(diyTrial.getProductId());
			Date startTime=DateUtil.formatStringToDate(modelMap.get("startTime")==null?"":String.valueOf(modelMap.get("startTime")));
			Date endTime=DateUtil.formatStringToDate(modelMap.get("endTime")==null?"":String.valueOf(modelMap.get("endTime")));
			if(payStart.before(startTime)){
				throw new ServiceException(Message.ADMIN_2012006.getCode(), Message.ADMIN_2012006.getMsg());
			}else if(payStart.after(endTime)){
				throw new ServiceException(Message.ADMIN_2012007.getCode(), Message.ADMIN_2012007.getMsg());
			}else if(payEnd.before(startTime)){
				throw new ServiceException(Message.ADMIN_2012008.getCode(), Message.ADMIN_2012008.getMsg());
			}else if(payEnd.before(startTime)){
				throw new ServiceException(Message.ADMIN_2012009.getCode(), Message.ADMIN_2012009.getMsg());
			}
		}
		DiyTrialApplication diyTrialUser = new DiyTrialApplication();
		diyTrialUser.setId(id);
		diyTrialUser.setPayStartTime(DateUtil.formatStringToDate(payStartTime));
		diyTrialUser.setPayEndTime(DateUtil.formatStringToDate(payEndTime));
		return diyTrialApplicationBackMapper.updateDiyTrialUser(diyTrialUser);
	}
	
//	设置试用申请物流信息
	@Override
	public int updateExpressInfo(Long id,  String expressCompany,String expressNo) {
		if (id == null || StringUtil.isEmpty(expressCompany)||StringUtil.isEmpty(expressNo)) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",expressCompany:" + expressCompany+",expressNo"+expressNo);
		}
		DiyTrialApplication diyTrialUser = new DiyTrialApplication();
		diyTrialUser.setId(id);
		diyTrialUser.setExpressCompany(expressCompany);
		diyTrialUser.setExpressNo(expressNo);
		return diyTrialApplicationBackMapper.updateDiyTrialUser(diyTrialUser);
	}
//	显示或隐藏申请理由
	@Override
	public int showAndHiddenReason(Long id, String isShowReason) {
		if (id == null || StringUtil.isEmpty(isShowReason)) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",isShowReason:" + isShowReason);
		}
		DiyTrialApplication diyTrialUser = new DiyTrialApplication();
		diyTrialUser.setId(id);
		diyTrialUser.setIsShowReason(isShowReason);
		return diyTrialApplicationBackMapper.updateDiyTrialUser(diyTrialUser);
	}

//	获取ids的用户列表
	@Override
	public List<DiyTrialApplication> selectTrialUserByIds(List<Long> idsList) {
		return diyTrialApplicationBackMapper.selectTrialUserByIds(idsList);
	}

	/**
	 *发送短信
	 * @param actId    
	 * @author lily
	 * @date  2017-05-15
	 */
	private void   sendPubishUserMsg(String isChargeDeposit,String applyEndTime, String mobile){
//			当前用户发布第一个创意发送短信，如果后面再发布创意就不再发送短信
				String  messageName="";
				List<String> params=new ArrayList<String>();
				if("Y".equals(isChargeDeposit)){
					messageName="deposit_trial_public";
					params.add(applyEndTime);
				}else{
					messageName="noDeposit_trial_public";
				}
				JSONObject  resultJson=DIYSMSUtil.sendMessageAndSave(mobile, messageName, null,1, params);
				String resultFlag=resultJson.get("success").toString();
				if(resultFlag.equals("true")){
					LOG.info(" 用户申请完成公示。接收手机 :" + mobile);
				}else{
					LOG.info("完成公示短信发送失败" + resultJson.get("msg").toString());
				}					
	}
	/**
	 * 获取物流公司列表 
	 * @return
	 * @author lily 
	 * @ 2017-05-15
	 */
	private List<Map<String,Object>>  getExpressCompanyList(){
		String res=SyncUtil.syncExpressCompany();
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		if(StringUtil.isNotEmpty(res)){
			JSONArray  jsonArray=JSONArray.fromObject(res);
			list=JSONArray.toList(jsonArray);
		}
		return list;
	}
	
//	定时任务功能：试用活动结束，未提交试用报告的，且已付定金的订单自动设置为超期未付尾款
	@Override
	public void autoDeduceDeposit() {
		List<DiyTrialApplicationCus> list=diyTrialApplicationBackMapper.getNoReportDepositApplication();
		if(list!=null&&list.size()!=0){
			for(DiyTrialApplicationCus cus:list){
				if(cus.getReportId()==null){//没有提交试用报告
					Date currentTime=new Date();
					if(currentTime.equals(cus.getEndTime())|| currentTime.after(cus.getEndTime())){//活动结束，系统自动调用
						Boolean flag=diyOrderService.orderRetainageOverdue(cus.getHbaseOrderId());
						if(flag){
							LOG.info("***************autoDeduceDeposit 扣除订单Id:"+cus.getOrderId()+"押金成功**************************");
//							发送短信
							String  messageName="deposit_report_unqualified";
							JSONObject  resultJson=DIYSMSUtil.sendMessageAndSave(cus.getMobile(), messageName,null, 1, null);
							String resultFlag=resultJson.get("success").toString();
							if(resultFlag.equals("true")){
								LOG.info(" 试用报告完成审核。接收手机 :" + cus.getMobile());
							}else{
								LOG.info("完成审批短信发送失败" + resultJson.get("msg").toString());
							}
						}else{
							LOG.info("***************autoDeduceDeposit 扣除订单Id:"+cus.getOrderId()+"押金失败**************************");
						}
					}
				}
			}
		}
		
	}


}
