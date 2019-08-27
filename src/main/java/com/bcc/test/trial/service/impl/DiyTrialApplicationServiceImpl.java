package com.bcc.test.trial.service.impl;

import com.bcc.test.common.utils.*;
import com.bcc.test.common.utils.hbase.HbaseConf;
import com.bcc.test.common.utils.hbase.HbaseOrderUtil;
import com.bcc.test.core.domain.DiyDeliveryAddress;
import com.bcc.test.core.domain.DiyUser;
import com.bcc.test.core.service.DiyUserService;
import com.bcc.test.core.system.ServiceException;
import com.bcc.test.core.util.DIYSMSUtil;
import com.bcc.test.order.mapper.DiyDeliveryAddressMapper;
import com.bcc.test.order.service.DiyOrderService;
import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.trial.domain.DiyTrialApplicationCus;
import com.bcc.test.trial.domain.DiyTrialReasonComment;
import com.bcc.test.trial.mapper.DiyTrialApplicationMapper;
import com.bcc.test.trial.mapper.DiyTrialMapper;
import com.bcc.test.trial.service.DiyTrialApplicationService;
import com.bcc.test.user.api.MessageConsts;
import com.bcc.test.user.service.DiyMessageService;
import com.bcc.test.user.service.DiyReportService;
import io.terminus.pampas.common.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DiyTrialApplicationServiceImpl
 * @Description:试用活动申请业务逻辑实现类
 * @author lily
 * @date 2017-05-23
 */
@Service
public class DiyTrialApplicationServiceImpl  implements  DiyTrialApplicationService{
	
	private static final Log LOG  = LogFactory.getLog(DiyTrialApplicationServiceImpl.class); 
	
	public final static Integer FIRSTNUM=3;
	
	public final static String  TRIALVERIFICATIONMSG="trial_verification_msg";
	
	@Autowired
	private DiyTrialMapper  diyTrialMapper;
	
	@Autowired
	private DiyTrialApplicationMapper  diyTrialApplicationMapper;
	
	@Autowired
	private DiyReportService diyReportService;
	
	@Autowired
	private DiyMessageService diyMessageService;
	
	@Autowired
	private DiyUserService diyUserService;
	
	@Autowired
	private DiyDeliveryAddressMapper diyDeliveryAddressMapper;
	
	@Autowired
	private DiyOrderService diyOrderService;
    @Autowired
    private RedisTemplate redisTemplate;
//	获取指定条件的申请用户列表
	@Override
	public List<Map> getTrialUserByPage(Integer pageNum, Integer pageSize,
			Long trialId, String isPublic) {
		if(trialId==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
		if(pageNum==null) pageNum=1;
		if(pageSize==null) pageSize=13;
		Integer start=(pageNum.intValue()-1)*pageSize.intValue();
		List<DiyTrialApplicationCus> list=diyTrialApplicationMapper.getTrialUserByPage(trialId, null, start, pageSize);
		if(list!=null&& list.size()>0){
			for(DiyTrialApplicationCus cus:list){
					cus.setHeadImg(StringUtil.covertFullImg(cus.getHeadImg()));
			}
		}
		return BeanToMapUtil.convertList(list, false);
	}

	//分页获取申请理由
		@Override
		public Page<Map> getTrialReasonByPage(Integer pageNum,
				Integer pageSize, Long trialId) {
			if(trialId==null)
				throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
			int total=diyTrialApplicationMapper.getTrialReasonCount(trialId);
			Page<Map> page=new Page<Map>(pageNum,pageSize,total);
			List<Map<String,Object>> list=diyTrialApplicationMapper.getTrialReasonByPage(trialId,page.getStartItem(), page.getPageSize());
			if(list!=null&& list.size()>0){
				for(Map<String,Object> map:list){
					if(map.get("headImg")!=null){
						map.put("headImg", StringUtil.covertFullImg(map.get("headImg").toString()));
					}
					if(map.get("id")!=null){
//						获取子评论总数
						Long trialApplicationId=Long.valueOf(map.get("id").toString());
						
//						获取每个申请理由的前三条评论
						List<Map> commentList=getTrialReasonCommentByPage(1, FIRSTNUM, trialApplicationId).getItems();
						if(commentList!=null&& commentList.size()>0){
							map.put("commentItems", commentList);
						}
//						申请理由被回复数量
						int  totalCount=diyTrialApplicationMapper.getTrialReasonCommentCount(trialApplicationId);
						map.put("replyCount", totalCount);		
//						计算评论剩余数
						int count=totalCount-FIRSTNUM;
						map.put("surplusNum", count<0?0:count);	
						
					}
					
				}
			}
			page.setItems(BeanToMapUtil.convertList(list, false));
			return page;
		}
		
//		分页获取指定申请理由的评论
		@Override
		public  Page<Map> getTrialReasonCommentByPage(Integer pageNum,
				Integer pageSize, Long trialApplicationId) {
			int  total=diyTrialApplicationMapper.getTrialReasonCommentCount(trialApplicationId);
			Page<Map> page= new Page<Map>(pageNum, pageSize, total);
			List<Map<String, Object>> list=diyTrialApplicationMapper.getTrialReasonCommentByPage(trialApplicationId, page.getStartItem(), page.getPageSize());
			if(list!=null&&list.size()>0){
				for(Map<String,Object> commentMap:list){
					if(commentMap.get("headImg")!=null){
						commentMap.put("headImg", StringUtil.covertFullImg(commentMap.get("headImg").toString()));
					}
				}
			}
			List<Map> items=BeanToMapUtil.convertList(list, false);
			page.setItems(items);
			return page;
		}

//		发表或回复指定申请理由的评论
		@Override
		public DiyTrialReasonComment createTrialReasonComment(Long trialApplicationId, Long parentId,
				String content) {
			//校验当前用户是否被举报
			Long userId=UserUtil.getUserId();
//			Long userId=20003l;
			if(userId==null)
				throw new ServiceException(Message.SYSTEM_10102.getMsg());
			diyReportService.getCheckUserFunResult(IntegerUtil.SIX,userId);
			if (trialApplicationId==null || StringUtil.isEmpty(content))
				throw new ServiceException(Message.SYSTEM_10104.getCode());
			DiyTrialReasonComment diyTrialReasonComment= new DiyTrialReasonComment();
			diyTrialReasonComment.setTrialApplicationId(trialApplicationId);
			diyTrialReasonComment.setUserId(userId);
			diyTrialReasonComment.setContent(content);
			if(parentId==null){
				diyTrialReasonComment.setParentId(new Long(0));
				diyTrialReasonComment.setToUserId(new Long(0));
			}else{
				DiyTrialReasonComment comment=diyTrialApplicationMapper.getTrialReasonCommentById(parentId);
				if(comment==null)
					throw new ServiceException(Message.SYSTEM_10105.getCode());
				diyTrialReasonComment.setToUserId(comment.getUserId());
				diyTrialReasonComment.setParentId(comment.getId());
			}
			int result=diyTrialApplicationMapper.insertTrialReasonComment(diyTrialReasonComment);
			if (result!=0) {
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("id", trialApplicationId);
				DiyTrialApplication trialApplication= diyTrialApplicationMapper.selectTrialApplicationByCondition(map);
				if(diyTrialReasonComment.getToUserId() !=null 
						&& diyTrialReasonComment.getToUserId() != (long)IntegerUtil.ZERO 
						&& !diyTrialReasonComment.getUserId().equals(diyTrialReasonComment.getToUserId())){
					diyMessageService.addMessage(6,"您的试用理由评论被回复了","",StringUtil.URL_TRIAL_REPORT_FIX+trialApplication.getTrialId(),
							diyTrialReasonComment.getContent(),diyTrialReasonComment.getUserId(),diyTrialReasonComment.getToUserId(),0L,
							diyTrialReasonComment.getTrialApplicationId(),MessageConsts.MSG_TRIAL);
				}
				diyTrialReasonComment=diyTrialApplicationMapper.getTrialReasonCommentById(diyTrialReasonComment.getId());
			}else{
				diyTrialReasonComment=null;
			}
			
			return diyTrialReasonComment;
		}

//		验证手机号并发送验证码
		@Override
		public JSONObject sendVerificationCode(String mobile, Long trialId) {
			if(!StringUtil.checkPhone(mobile)||trialId==null)
				throw new ServiceException(Message.SYSTEM_10104.getCode());
//			判断活动存在与否 并是否已结束
			DiyTrial  trial= diyTrialMapper.selectTrialById(trialId);
			if(trial==null)
				throw new ServiceException(Message.PUBLIC_1012001.getCode());
			Date currentTime=new Date();
			if(currentTime.before(trial.getPreheatEndTime()))
				throw new ServiceException(Message.PUBLIC_1012005.getCode());
			if(currentTime.after(trial.getApplyEndTime()))
				throw new ServiceException(Message.PUBLIC_1012006.getCode());
//			判断该手机号是否已经申请过该活动
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("trialId", trialId);
			params.put("mobile", mobile);
			DiyTrialApplication application=diyTrialApplicationMapper.selectTrialApplicationByCondition(params);
			if(application!=null)
				throw new ServiceException(Message.PUBLIC_1012007.getCode());
//			发送验证短信
			JSONObject json=DIYSMSUtil.sendVerificationCode(mobile, TRIALVERIFICATIONMSG+"&&"+trialId);
			return  json;
		}

//		保存申请信息
		@Override
		public int createTrialApplication(Long trialId, String name, Integer sex,
				String mobile, String verificationCode, String reason) {
			
			if(trialId==null||!StringUtil.checkPhone(mobile)||StringUtil.isEmpty(verificationCode)||StringUtil.isEmpty(reason))
				throw new ServiceException(Message.SYSTEM_10104.getCode());
			
//			判断活动存在与否 并是否已结束
			DiyTrial  trial= diyTrialMapper.selectTrialById(trialId);
			if(trial==null)
				throw new ServiceException(Message.PUBLIC_1012001.getCode());
			Date currentTime=new Date();
			if(currentTime.before(trial.getPreheatEndTime()))
				throw new ServiceException(Message.PUBLIC_1012005.getCode());
			if(currentTime.after(trial.getApplyEndTime()))
				throw new ServiceException(Message.PUBLIC_1012006.getCode());
			
//			 验证码验证
			if (!checkVerificationCode(TRIALVERIFICATIONMSG+"&&"+trialId, mobile, verificationCode)) {
				throw new ServiceException(Message.PC_10010006.getCode());
			}
			
			Long userId = UserUtil.getUserId();
//			Long userId=11783l;
			DiyUser user = diyUserService.getDiyUserById(userId);
			if (user == null) {
				throw new ServiceException(Message.SYSTEM_10102.getCode());
			}
			
//			判断该用户是否已经申请过该活动
			Map<String,Object> paramsMap=new HashMap<String,Object>();
			paramsMap.put("trialId", trialId);
			paramsMap.put("userId", userId);
			DiyTrialApplication application=diyTrialApplicationMapper.selectTrialApplicationByCondition(paramsMap);
			if(application!=null){
				throw new ServiceException(Message.PUBLIC_1012008.getCode());
			}else{
				application= new DiyTrialApplication();
				application.setMobile(mobile);
				application.setName(name);
				application.setReason(reason);
				application.setSex(sex==null?0:sex);
				application.setTrialId(trialId);
				application.setUserId(userId);
			}
			int result=diyTrialApplicationMapper.insertTrialApplication(application);
//			移除全局map存放的验证码
			if(result!=0){
				removeVerificationCode(TRIALVERIFICATIONMSG + "&&" + trialId, mobile);
			}
			return result;
		}

//		分页获取我的试用申请
		@Override
		public Page<Map> getMyApplicationByPage(Integer pageNum,
				Integer pageSize, Integer type) {
			if(type==null)
				type=1;
			Long userId=UserUtil.getUserId();
//			Long userId=37l;
			if(userId==null)
				throw new ServiceException(Message.SYSTEM_10102.getMsg());
			int  total=diyTrialApplicationMapper.getMyApplicationCount(userId,type);
			Page<Map> page= new Page<Map>(pageNum, pageSize, total);
			List<DiyTrialApplicationCus> list=diyTrialApplicationMapper.getMyApplicationByPage(userId, type,page.getStartItem(), page.getPageSize());
			if(list!=null&&list.size()>0){
				for(DiyTrialApplicationCus cus:list){
					cus.setPcCover(StringUtil.covertFullImg(cus.getPcCover()));
					cus.setmCover(StringUtil.covertFullImg(cus.getmCover()));
					cus.setStartTimeStr(DateUtil.datetoStr(cus.getStartTime(), DateUtil.GLOBAL_DATE_PATTERN));
					cus.setEndTimeStr(DateUtil.datetoStr(cus.getEndTime(), DateUtil.GLOBAL_DATE_PATTERN));
//					对申请各状态进行划分   0：未审核   1:失败   
//					2：未填写收货地址（无押金）  未支付押金（有押金）  3：地址已提交未发货（无押金）  已支付押金未发货（有押金）
//					4：商家已发货（有无押金公用）      5：报告已提交等待审核（有无押金公用） 6.报告通过审核（有无押金稍微区分）
//					7：报告不合格修改（有无押金公用）   8：未收到试用报告（有无押金共用） 9.未收到合格的试用报告（共用） 10、放弃试用资格（共用）
					if(type==1){
						cus.setStatus(0);
					}else if(type==3){
						cus.setStatus(1);
					}else if(type==2){
//						先判断是付押金试用还是无押金试用
						Date currentTime=new Date();
						if("N".equals(cus.getIsChargeDeposit())){//----------------无押金试用
								if(cus.getAddressId()==null){//---用户已公示但未填写收货地址
//									判断是在48小时内  还是48小时之外
									if(currentTime.before(cus.getEndTime())&&(DateUtil.getDiffHours(cus.getPublicTime(), currentTime)<48)){//活动未结束且在48小时之内----出现修改报告按钮
										cus.setStatus(2);
									}else{//---------活动已结束或在48小时以外 ，放弃试用资格
										cus.setStatus(10);
									}
								}else{//-----用户已填写收货地址
									if(StringUtil.isEmpty(cus.getExpressNo())){//--------地址已提交，商家未发货
										cus.setStatus(3);
									}else {//-----------商家已发货
										if(cus.getReportId()==null){//-----------用户未提交试用报告
											if(currentTime.before(cus.getEndTime())){//活动未结束----提交报告按钮
												cus.setStatus(4);
											}else{//---------活动已结束---未收到试用报告
												cus.setStatus(8);
											}
										}else{//----------------用户已提交试用报告
											if(cus.getReportStatus()==0){//-----------报告未审核状态
												if(currentTime.before(cus.getEndTime())){//活动未结束----------报告待审核
													cus.setStatus(5); 
												}else{//活动已结束----------报告不合格
													cus.setStatus(9);
												}
											}else if(cus.getReportStatus()==1){//-----------报告合格状态
												cus.setStatus(6);
											}else if(cus.getReportStatus()==2){//------------报告需要修改状态
												if(currentTime.before(cus.getEndTime())){//活动未结束----------出现修改报告按钮
													cus.setStatus(7); 
												}else{//活动已结束----------报告不合格
													cus.setStatus(9);
												}
											}else if(cus.getReportStatus()==3){//报告不合格
												cus.setStatus(9);
											}
										}
									}
								}
							
						}else if("Y".equals(cus.getIsChargeDeposit())){//---------------有押金试用
							if(cus.getOrderId()==null){//---用户未下单
//								判断是在付款日期前还是付款日期后
								if(currentTime.before(cus.getEndTime())&&currentTime.before(cus.getPayEndTime())){//-----活动未结束且在付款结束前
									cus.setStatus(2);
								}else{//---------活动已结束或超过付款结束日期之后 ，放弃试用资格
									cus.setStatus(10);
								}
							}else{//-----用户已下单
//								进一步判断订单是否已经付款
								if(cus.getOrderStatus().equals("0")){//------------订单未支付
//									判断是在付款日期前还是付款日期后
									if(currentTime.before(cus.getEndTime())&&currentTime.before(cus.getPayEndTime())){//---活动未结束且在付款结束前
										cus.setStatus(2);
										cus.setPayUrl(HbaseConf.PAY_URL+HbaseConf.ORDER_PAY_URL 
												+ "?orderIds="+cus.getHbaseOrderId()
												+"&sign="+HbaseOrderUtil.getOrderSign(cus.getHbaseOrderId().toString()));
									}else{//---------活动已结束或超过付款结束日期之后 ，放弃试用资格
										cus.setStatus(10);
									}
								}else if(cus.getOrderStatus().equals("99")){//----------订单已支付押金
									if(StringUtil.isEmpty(cus.getExpressNo())){//--------已支付押金，商家未发货
										cus.setStatus(3);
									}else {//-----------商家已发货
										if(cus.getReportId()==null){//-----------用户未提交试用报告
											if(currentTime.before(cus.getEndTime())){//活动未结束----提交报告按钮
												cus.setStatus(4);
											}else{//---------活动已结束---未收到试用报告
												cus.setStatus(8);
											}
										}else{//----------------用户已提交试用报告
											if(cus.getReportStatus()==0){//-----------报告未审核状态
												if(currentTime.before(cus.getEndTime())){//活动未结束----------报告待审核
													cus.setStatus(5); 
												}else{//活动已结束----------报告不合格
													cus.setStatus(9);
												}
											}else if(cus.getReportStatus()==1){//-----------报告合格状态
												cus.setStatus(6);
											}else if(cus.getReportStatus()==2){//------------报告需要修改状态
												if(currentTime.before(cus.getEndTime())){//活动未结束----------出现修改报告按钮
													cus.setStatus(7); 
												}else{//活动已结束----------报告不合格
													cus.setStatus(9);
												}
											}else if(cus.getReportStatus()==3){//报告不合格
												cus.setStatus(9);
											}
										}
									}
								}else if(cus.getOrderStatus().equals("-6")||cus.getOrderStatus().equals("-7")){//----------订单已取消  放弃试用资格
									cus.setStatus(10);
								}else if(cus.getOrderStatus().equals("-1")||cus.getOrderStatus().equals("-10")){//----------订单已申请退款、已退款
									cus.setStatus(6);
								}else if(cus.getOrderStatus().equals("15")){//----------订单扣押金
//									区分是否提交了试用报告
									if(cus.getReportId()==null){//-----------------未提交试用报告
										cus.setStatus(8);
									}else{	//---------------------------------提交了试用报告,报告不合格
										cus.setStatus(9);
									}
								}
							}							
						}
					}
					
					
				}
			}
			List<Map> items=BeanToMapUtil.convertList(list, false);
			page.setItems(items);
			return page;
		}
		
//		删除指定的试用申请
		@Override
		public int deleteTrialApplication(Long id) {
			if(id==null)
				throw new ServiceException(Message.SYSTEM_10104.getMsg());
			Long userId=UserUtil.getUserId();
//			Long userId=37l;
			if(userId==null)
				throw new ServiceException(Message.SYSTEM_10102.getMsg());
			DiyTrialApplication application=new DiyTrialApplication();
			application.setId(id);
			application.setIsDelete("Y");
			int result=diyTrialApplicationMapper.updateTrialApplication(application);
			return result;
		}

//		设置试用活动收货地址
		@Override
		public int updatTrialApplicationAddress(Long applicationId,
				Long addressId) {
			if(applicationId==null ||  addressId==null)
				throw new ServiceException(Message.SYSTEM_10104.getMsg());
			Map<String,Object> map= new HashMap<String,Object>();
			map.put("id", applicationId);
			DiyTrialApplication  application=diyTrialApplicationMapper.selectTrialApplicationByCondition(map);
			if(application==null)
				throw  new ServiceException(Message.PUBLIC_1012002.getMsg());
			if(application.getUserId().longValue()!=UserUtil.getUserId().longValue())
				throw  new ServiceException(Message.PUBLIC_1012012.getMsg());
			DiyDeliveryAddress  address=diyDeliveryAddressMapper.selectDeliveryAddressById(addressId);
			if(address==null)
				throw new ServiceException(Message.PC_1005015.getMsg());
			application.setAddressId(addressId);
			String addressStr=address.getProvince()+address.getCity()+address.getRegion()+address.getDetailAddress();
			application.setAddress(addressStr);
			int result= diyTrialApplicationMapper.updateTrialApplication(application);
			return result;
		}
//		获取指定id的试用申请记录
		@Override
		public DiyTrialApplication getTrialApplicationById(
				Long trialApplicationId) {
			if(trialApplicationId==null)
				throw new ServiceException(Message.SYSTEM_10104.getMsg());
			Map<String,Object> map= new HashMap<String,Object>();
			map.put("id", trialApplicationId);
			DiyTrialApplication  application=diyTrialApplicationMapper.selectTrialApplicationByCondition(map);
			return application;
		}
    public  boolean checkVerificationCode(String actName, String mobile, String code) {
        Object lastCodeInfoObj =  redisTemplate.opsForValue().get(mobile + actName);
        // 之前未获取到验证码的情况
        if(lastCodeInfoObj == null){
            return false;
        }
        String lastCodeInfo = lastCodeInfoObj.toString();
        String[] lastCodeInfos = lastCodeInfo.split("&&");
        if(lastCodeInfos.length < 2){
            return false;
        }
        String lastCode = lastCodeInfos[0];
        Long lastStamp = Long.valueOf(lastCodeInfos[1]);
        Long nowStamp = System.currentTimeMillis() / 1000;
        // 验证码已过时
        if((nowStamp - lastStamp) > 300){
            return false;
        }

        // 验证码不对
        if(!lastCode.equals(code)){
            return false;
        }
        return true;
    }

    /**
     * 验证结束后，删除存储的验证码
     * @param actName
     * @param mobile
     */
    public  void removeVerificationCode(String actName, String mobile){
        redisTemplate.delete(mobile + actName);
    }

//    获取指定用户的试用申请记录
	@Override
	public List<DiyTrialApplication> getApplicationListByUserId(Long userId) {
		if(userId == null){
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		}
		List<DiyTrialApplication> list=diyTrialApplicationMapper.getApplicationListByUserId(userId);
		return list;
	}

//	批量修改指定申请的userId
	@Override
	public int updateApplicationUserId(List<Long> list, Long userId) {
		if (list == null || list.size() <= 0 ) 
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		if (userId == null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("userId", userId);
		Integer result = diyTrialApplicationMapper.updateApplicationUserId(map);
		return result;
	}
}
