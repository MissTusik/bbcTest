package com.bcc.test.trial.service.impl;

import com.haier.diy.common.utils.*;
import com.haier.diy.core.domain.DiyUser;
import com.haier.diy.core.service.DiyUserService;
import com.haier.diy.core.system.ServiceException;
import com.haier.diy.goods.domain.DiyProductHistory;
import com.haier.diy.goods.service.DiyProductHistoryService;
import com.haier.diy.trial.domain.*;
import com.haier.diy.trial.mapper.DiyTrialApplicationMapper;
import com.haier.diy.trial.mapper.DiyTrialMapper;
import com.haier.diy.trial.service.DiyTrialService;
import com.haier.diy.user.service.DiyMessageService;
import com.haier.diy.user.service.DiyReportService;
import io.terminus.pampas.common.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName:DiyTrialBackServiceImpl
 * @Description:试用活动业务逻辑实现类
 * @author lily
 * @date 2017-05-23
 */
@Service
public class DiyTrialServiceImpl implements DiyTrialService{
	
	public final static Integer FIRSTNUM=3;
	
	@Autowired
	private DiyTrialMapper  diyTrialMapper;
	
	@Autowired
	private DiyTrialApplicationMapper  diyTrialApplicationMapper;
	
	@Autowired
	private DiyProductHistoryService  diyProductHistoryService;
	
	@Autowired
	private DiyReportService diyReportService;
	
	@Autowired
	private DiyMessageService diyMessageService;
	
	@Autowired
	private DiyUserService diyUserService;

//	分页获取试用活动列表
	@Override
	public Page<Map> getTrialByPage(Integer pageNum, Integer pageSize) {
		int total=diyTrialMapper.getTrialCount();
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<DiyTrialCus> list=diyTrialMapper.getTrialByPage(page.getStartItem(),page.getPageSize());
		if(list!=null&&list.size()>0){
//			判断各试用活动当前所处节点
//			活动未开始、预热阶段用户 申请数为0，申请和试用体验以及结束三个阶段用户数为：基数+实际申请用户数
//			处于预热和申请两个阶段，给出剩余时间
			for(DiyTrialCus trial:list){
//				处理申请人数，当实际申请人数小于等于13时，不加基数
				if(trial.getTrialUserNum()>13){
					trial.setTrialUserNum(trial.getBase()+trial.getTrialUserNum());//申请人数
				}
//				判断活动所处阶段
				Date currentTime=new Date();
				if(currentTime.before(trial.getStartTime())){//----活动未开始
					trial.setStage(0);
					trial.setTrialUserNum(0);
				}else if(currentTime.after(trial.getStartTime())&&currentTime.before(trial.getPreheatEndTime())){//---预热阶段
					trial.setStage(1);
					trial.setTrialUserNum(0);
//					设置距离申请开始剩余时间，>1天 按天算  <1天按小时
					if(DateUtil.compareDay(currentTime, trial.getPreheatEndTime())){
						trial.setRemainTime(DateUtil.getDiffDays(currentTime, trial.getPreheatEndTime())+"天");
					}else{
						trial.setRemainTime(DateUtil.getDiffHours(currentTime, trial.getPreheatEndTime())+"小时");				
					}
				}else if(currentTime.after(trial.getPreheatEndTime())&&currentTime.before(trial.getApplyEndTime())){//--申请阶段
					trial.setStage(2);
//					设置距离申请开始剩余时间，>1天 按天算  <1天按小时
					if(DateUtil.compareDay(currentTime, trial.getApplyEndTime())){
						trial.setRemainTime(DateUtil.getDiffDays(currentTime, trial.getApplyEndTime())+"天");
					}else{
						trial.setRemainTime(DateUtil.getDiffHours(currentTime, trial.getApplyEndTime())+"小时");				
					}
				}else if(currentTime.after(trial.getApplyEndTime())&&currentTime.before(trial.getEndTime())){//--体验阶段
					trial.setStage(3);
				}else if(currentTime.after(trial.getEndTime())){//--结束
					trial.setStage(4);
				}
				trial.setPcCover(StringUtil.covertFullImg(trial.getPcCover()));
				trial.setmCover(StringUtil.covertFullImg(trial.getmCover()));
			}
		}else{
			list=new ArrayList<DiyTrialCus>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		return page;
	}

//	获取指定的试用活动详情
	@Override
	public Map<String,Object> getTrialDetailById(Long trialId) {
		if(trialId==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		DiyTrialCus  diyTrialCus=diyTrialMapper.selectTrialCusById(trialId,UserUtil.getUserId());
		if(diyTrialCus==null)
			throw new ServiceException(Message.PUBLIC_1012001.getCode());
//		处理申购人数
		if(diyTrialCus.getTrialUserNum()>13){
			diyTrialCus.setTrialUserNum(diyTrialCus.getBase()+diyTrialCus.getTrialUserNum());//申请人数    不显示试用报告(按钮与标签) 申请按钮可用（已申请则隐藏）
		}
//		-----------封装试用活动信息开始----------
		Date currentTime=new Date();
		if(currentTime.before(diyTrialCus.getStartTime())){//----活动未开始    不显示试用报告(按钮与标签) 申请按钮灰
			diyTrialCus.setStage(0);
			diyTrialCus.setTrialUserNum(0);
		}else if(currentTime.after(diyTrialCus.getStartTime())&&currentTime.before(diyTrialCus.getPreheatEndTime())){//---预热阶段 不显示试用报告(按钮与标签) 申请按钮灰
			diyTrialCus.setStage(1);
			diyTrialCus.setTrialUserNum(0);
//			设置距离申请开始剩余时间，>1天 按天算  <1天按小时
			if(DateUtil.compareDay(currentTime, diyTrialCus.getPreheatEndTime())){
				diyTrialCus.setRemainTime(DateUtil.getDiffDays(currentTime, diyTrialCus.getPreheatEndTime())+"天");
			}else{
				diyTrialCus.setRemainTime(DateUtil.getDiffHours(currentTime, diyTrialCus.getPreheatEndTime())+"小时");				
			}
		}else if(currentTime.after(diyTrialCus.getPreheatEndTime())&&currentTime.before(diyTrialCus.getApplyEndTime())){//--申请阶段
			diyTrialCus.setStage(2);
//			设置距离申请开始剩余时间，>1天 按天算  <1天按小时
			if(DateUtil.compareDay(currentTime, diyTrialCus.getApplyEndTime())){
				diyTrialCus.setRemainTime(DateUtil.getDiffDays(currentTime, diyTrialCus.getApplyEndTime())+"天");
			}else{
				diyTrialCus.setRemainTime(DateUtil.getDiffHours(currentTime, diyTrialCus.getApplyEndTime())+"小时");				
			}
		}else if(currentTime.after(diyTrialCus.getApplyEndTime())&&currentTime.before(diyTrialCus.getEndTime())){//--体验阶段 不显示试用报告(按钮与标签) 申请按钮灰
			diyTrialCus.setStage(3);
		}else if(currentTime.after(diyTrialCus.getEndTime())){//--活动结束   不显示申请按钮 只显示试用报告按钮
			diyTrialCus.setStage(4);
		}
		diyTrialCus.setPcCover(StringUtil.covertFullImg(diyTrialCus.getPcCover()));
		diyTrialCus.setmCover(StringUtil.covertFullImg(diyTrialCus.getmCover()));
		diyTrialCus.setProductPcCover(StringUtil.covertFullImg(diyTrialCus.getProductPcCover()));
		diyTrialCus.setProductMCover(StringUtil.covertFullImg(diyTrialCus.getProductMCover()));
//		处理该试用活动中的推荐商品列表
		List<DiyTrialData> dataList=diyTrialCus.getTrialDataItems();
		if(dataList.size()!=0){
			for(DiyTrialData data:dataList){
				data.setCover(StringUtil.covertFullImg(data.getCover()));
			}
		}
		
//		获取该活动所有中签公示有效用户
		List<DiyTrialApplicationCus> publicUsers=diyTrialApplicationMapper.getTrialUserByPage(diyTrialCus.getId(), "Y", 0, diyTrialCus.getBallotUserNum());
		if(publicUsers.size()!=0){
			for(DiyTrialApplicationCus applicationCus:publicUsers){
					applicationCus.setHeadImg(StringUtil.covertFullImg(applicationCus.getHeadImg()));
			}
			publicUsers=screeningValidUsers(diyTrialCus,publicUsers);
		}
		
//		获取众创历程
//		获取商品成长历程
		List<DiyProductHistory> historys= diyProductHistoryService.getHistorysByProductId(diyTrialCus.getProductId());
		if(historys.size()!=0){
			for(DiyProductHistory history:historys){
				history.setCover(StringUtil.covertFullImg( history.getCover()));
			}
		}
//		仅限转换  并放入公示用户和成长历程
		Map<String,Object> resultMap=BeanToMapUtil.convertBean(diyTrialCus, false);
		
		resultMap.put("trialDataItems", BeanToMapUtil.convertList(dataList, false));
		resultMap.put("publicUserItems", BeanToMapUtil.convertList(publicUsers,false));
		resultMap.put("histroyItems", BeanToMapUtil.convertList(historys, false));
		return resultMap;
	}


//	收藏试用活动
	@Override
	public int collectTrial(Long trialId) {
		if(trialId==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
		Long userId=UserUtil.getUserId();
//		Long userId=230688l;
		if(userId == null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		int result = 0;
		DiyTrialCollect collect = diyTrialMapper.selectTrialCollectByFrom(userId, trialId);
		if(collect == null){
			collect= new DiyTrialCollect();
			collect.setTrialId(trialId);
			collect.setUserId(userId);
			result = diyTrialMapper.insertTrialCollect(collect);
		}
		return result;
	}

//	取消收藏试用活动
	@Override
	public int cancelCollectTrial(Long trialId) {
		if(trialId==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",trialId:" + trialId);
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		if(userId == null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		return diyTrialMapper.deleteTrialCollect(userId, trialId);
	}

//	分页获取当前用户试用活动收藏列表
	@Override
	public Page<Map> getCollectByPage(Integer pageNum, Integer pageSize) {
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		if(userId==null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		Integer total = diyTrialMapper.getCollectCount(userId);	
		Page<Map> page = new Page<Map>(pageNum, pageSize, total);
		List<DiyTrialCollectCus> list = diyTrialMapper.selectCollectByPage(userId, page.getStartItem(), pageSize);
		if(list!=null&& list.size()!=0){
			for(DiyTrialCollectCus collect:list){
				Date currentTime=new Date();
				if(currentTime.before(collect.getStartTime())){//----活动未开始
					collect.setStage(0);
				}else if(currentTime.after(collect.getStartTime())&&currentTime.before(collect.getPreheatEndTime())){//---预热阶段
					collect.setStage(1);
				}else if(currentTime.after(collect.getPreheatEndTime())&&currentTime.before(collect.getApplyEndTime())){//--申请阶段
					collect.setStage(2);
				}else if(currentTime.after(collect.getApplyEndTime())&&currentTime.before(collect.getEndTime())){//--体验阶段
					collect.setStage(3);
				}else if(currentTime.after(collect.getEndTime())){//--结束
					collect.setStage(4);
				}
				collect.setPcCover(StringUtil.covertFullImg(collect.getPcCover()));
				collect.setmCover(StringUtil.covertFullImg(collect.getmCover()));
				
			}
		}else{
			page=new Page<Map>(pageNum, pageSize, 0);
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		return page;
	}

//	获取试用活动商品信息与当前用户信息
	@Override
	public Map<String, Object> getTrialProductAndUserInfo(Long trialId) {
		Map<String,Object>  map=new HashMap<String, Object>();
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		DiyUser  diyUser=diyUserService.getDiyUserById(userId);
		if(diyUser!=null){
			map.put("nickname", diyUser.getNickname());
			map.put("headImg", StringUtil.covertFullImg(diyUser.getHeadImg()));
		}
		Map<String,Object> productMap =diyTrialMapper.getTrialProductInfo(trialId);
		if(productMap!=null){
			map.put("trialId",productMap.get("id"));
			map.put("productId",productMap.get("productId"));
			map.put("pcCover",StringUtil.covertFullImg(productMap.get("pcCover").toString()));
			map.put("mCover",StringUtil.covertFullImg(productMap.get("mCover").toString()));
			map.put("name",productMap.get("name"));
			map.put("title",productMap.get("title"));
		}
		return map;
	}
	
	private  List<DiyTrialApplicationCus>  screeningValidUsers(DiyTrialCus diyTrialCus, List<DiyTrialApplicationCus> publicUsers){
		List<DiyTrialApplicationCus> list=new ArrayList<DiyTrialApplicationCus>();
		if(publicUsers!=null&&publicUsers.size()>0){
			for(DiyTrialApplicationCus diyTrialApplicationCus:publicUsers){
				diyTrialApplicationCus.setHeadImg(StringUtil.covertFullImg(diyTrialApplicationCus.getHeadImg()));
//				判断用户状态（0无   1正常   2失效）    第一步：是否公示
//				申请活动有无押金
				if("Y".equals(diyTrialCus.getIsChargeDeposit())){//--------------有押金
//					判断依据说明    	正常状态1：订单已付款、超期未付尾款、已退款    付款时间内未下单或者下单未付款     无效2：付款时间内取消订单、超过付款时间未下单或下单未付款
//					判断是否下单
					if(StringUtil.isEmpty(diyTrialApplicationCus.getOrderStatus())){//--------------未下单           判断是否在付款时间范围内
						Date currentTime=new Date();
						if(currentTime.before(diyTrialCus.getEndTime())&&currentTime.before(diyTrialApplicationCus.getPayEndTime())){//当前时间在付款时间内且在活动结束前
							list.add(diyTrialApplicationCus);		//状态正常
						}
					}else{//----------------------已下单
//						判断是否已付款
						if(diyTrialApplicationCus.getOrderStatus().equals("99")){//----已付押金
							list.add(diyTrialApplicationCus);   //状态正常
						}else if(diyTrialApplicationCus.getReportStatus()!=null&&diyTrialApplicationCus.getReportStatus()==3&&diyTrialApplicationCus.getOrderStatus().equals("15")){//----报告不合格且扣除押金
							list.add(diyTrialApplicationCus);	
						}else if(diyTrialApplicationCus.getReportStatus()!=null&&diyTrialApplicationCus.getReportStatus()==1&&diyTrialApplicationCus.getOrderStatus().equals("-1")){//----报告合格且申请退款
							list.add(diyTrialApplicationCus);	
						}else if(diyTrialApplicationCus.getReportStatus()!=null&&diyTrialApplicationCus.getReportStatus()==1&&diyTrialApplicationCus.getOrderStatus().equals("-10")){//---报告合格且退款成功
							list.add(diyTrialApplicationCus);	
						}else{
//							判断是否在付款时间内
							Date currentTime=new Date();
							if(currentTime.before(diyTrialCus.getEndTime())&&currentTime.before(diyTrialApplicationCus.getPayEndTime())){//-----------当前时间在付款时间内且在活动结束前
								if(diyTrialApplicationCus.getOrderStatus().equals("0")){	//未付款的
									list.add(diyTrialApplicationCus);
								}
							}
						}
					}
				}else{//---------------无押金
//					判断有无收货地址
					if(diyTrialApplicationCus.getAddressId()!=null){//-----------有收货地址
						list.add(diyTrialApplicationCus);
					}else{//--------------无收货地址
//						判断是否在公示后48小时以内  且有无收货地址
						Date currentTime=new Date();
						if(currentTime.before(diyTrialCus.getEndTime())&&(DateUtil.getDiffHours(diyTrialApplicationCus.getPublicTime(),currentTime)<=48)){//-----------48小时内且在活动结束前
								list.add(diyTrialApplicationCus);
						}
					}
				}				
				}
			}
			
		return list;
	}
	


}
