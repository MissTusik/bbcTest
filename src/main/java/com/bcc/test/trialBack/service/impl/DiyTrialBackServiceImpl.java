package com.bcc.test.trialBack.service.impl;

import com.bcc.test.trial.common.utils.BeanToMapUtil;
import com.bcc.test.trial.common.utils.Message;
import com.bcc.test.trial.common.utils.Page;
import com.bcc.test.trial.common.utils.StringUtil;
import com.bcc.test.trial.core.system.ServiceException;
import com.bcc.test.trial.domain.DiyTrial;
import com.bcc.test.trial.domain.DiyTrialData;
import com.bcc.test.trialBack.domain.DiyTrialSupper;
import com.bcc.test.trialBack.mapper.DiyProductBackMapper;
import com.bcc.test.trialBack.mapper.DiyTrialBackMapper;
import com.bcc.test.trialBack.service.DiyTrialBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * ClassName:DiyTrialBackServiceImpl
 * @Description:试用活动业务逻辑实现类
 * @author lily
 * @date 2017-05-04
 */
@Service
public class DiyTrialBackServiceImpl implements DiyTrialBackService{
	
	@Autowired
	private DiyTrialBackMapper  diyTrialBackMapper;
	@Autowired
	private DiyProductBackMapper  diyProductBackMapper;

//	分页获取试用活动列表
	@Override
	public Page<Map> getTrialByPage(Integer pageNum, Integer pageSize) {
		int total=diyTrialBackMapper.getTrialCount();
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<DiyTrialSupper> list=diyTrialBackMapper.getTrialByPage(page.getStartItem(),page.getPageSize());
		if(list!=null&&list.size()>0){
//			判断各试用活动当前所处节点
			for(DiyTrialSupper trial:list){
				Date currentTime=new Date();
				if(currentTime.before(trial.getStartTime())){
					trial.setStage(0);
				}else if(currentTime.after(trial.getStartTime())&&currentTime.before(trial.getPreheatEndTime())){
					trial.setStage(1);
				}else if(currentTime.after(trial.getPreheatEndTime())&&currentTime.before(trial.getApplyEndTime())){
					trial.setStage(2);
				}else if(currentTime.after(trial.getApplyEndTime())&&currentTime.before(trial.getEndTime())){
					trial.setStage(3);
				}else if(currentTime.after(trial.getEndTime())){
					trial.setStage(4);
				}
				trial.setPcCover(StringUtil.covertFullImg(trial.getPcCover()));
				trial.setmCover(StringUtil.covertFullImg(trial.getmCover()));
				trial.setUrl(StringUtil.URL_TRIAL_FIX+trial.getId());
			}
		}else{
			list=new ArrayList<DiyTrialSupper>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		return page;
	}
	
//	新增试用活动
	@Override
	public Map<String,Object> addTrial(DiyTrial diyTrial) {
//		 校验参数
		checkParams(diyTrial);
		// 新增试用活动信息
		 int result=diyTrialBackMapper.insertDiyTrial(diyTrial);
		 Map<String, Object> map = new HashMap<String, Object>();
		 if(result>0){
			map.put("id", diyTrial.getId());
		 }
		 return map;//添加试用活动后需要返回所添加活动的id，以方便添加其他信息对应到相应的活动中
	}

//	获取指定的试用活动信息
	@Override
	public DiyTrialSupper getTrialById(Long id) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		DiyTrialSupper diyTrialSupper = diyTrialBackMapper.selectTrialSupperById(id);
		diyTrialSupper.setPcCover(StringUtil.covertFullImg(diyTrialSupper.getPcCover()));
		diyTrialSupper.setmCover(StringUtil.covertFullImg(diyTrialSupper.getmCover()));
		List<DiyTrialData> diyTrialDataList = diyTrialSupper.getDiyTrialDataList();
		for (DiyTrialData diyTrialData : diyTrialDataList) {
			diyTrialData.setCover(StringUtil.covertFullImg(diyTrialData.getCover()));
		}
		diyTrialSupper.setDiyTrialDataList(diyTrialDataList);
		return diyTrialSupper;
	}

//	修改试用活动信息
	@Override
	public int updateTrial(DiyTrial diyTrial) {
		// 校验参数
		checkParams(diyTrial);
		if (diyTrial.getId() == null)
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + diyTrial.getId());
		int result = diyTrialBackMapper.updateDiyTrial(diyTrial);
		return result;
	}

	@Override
	public int updateTrialRule(Long id, String rule) {
		if(id==null){
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		DiyTrial diyTrial = new DiyTrial();
		diyTrial.setId(id);
		diyTrial.setRule(rule);
		return diyTrialBackMapper.updateDiyTrial(diyTrial);
	}

	@Override
	public int updateTrialData(Long id, List<DiyTrialData> diyTrialDataList) {
		if(id==null){
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
//		// 删除已有的推荐商品信息
		int result = diyTrialBackMapper.deleteDiyTrialData(null, id);
//		// 新增修修改后的推荐商品
		for (DiyTrialData diyTrialData : diyTrialDataList) {
			if (diyTrialData.getRelationId() != null) {
				diyTrialData.setTrialId(id);
				result = diyTrialBackMapper.insertDiyTrialData(diyTrialData);
			}
		}
		return result;
	}
	
	
//	修改试用活动排序
	@Override
	public int updateTrialSort(Long id, Integer sort) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		DiyTrial diyTrial = new DiyTrial();
		diyTrial.setId(id);
		diyTrial.setSort(sort);
		return diyTrialBackMapper.updateDiyTrial(diyTrial);
	}
	
//	启用/禁用试用活动
	@Override
	public int onAndOffTrial(Long id, Integer status) {
		if (id == null || status == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id + ",status:" + status);
		}
		DiyTrial diyTrial = new DiyTrial();
		diyTrial.setId(id);
		diyTrial.setStatus(status);
		return diyTrialBackMapper.updateDiyTrial(diyTrial);
	}

//	删除试用活动
	@Override
	public int deleteTrial(Long id) {
		if (id == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",id:" + id);
		}
		return diyTrialBackMapper.deleteDiyTrial(id);
	}

//	*********************有关试用活动推荐商品信息操作*****************************
	/**
	 * 新增试用活动的推荐商品信息
	 * @param diyTrial			试用活动
	 * @param diyTrialDataList	推荐商品
	 * @return
	 * @author lily
	 * @date  2017-05-05
	 */
	private int addTrialData(DiyTrial diyTrial,
			List<DiyTrialData> diyTrialDataList) {
		int result = 0;
		if (diyTrial.getId() != null) {
			for (DiyTrialData diyTrialData : diyTrialDataList) {
				diyTrialData.setTrialId(diyTrial.getId());
				if (diyTrialData.getRelationId()!= null ) {
					result = diyTrialBackMapper.insertDiyTrialData(diyTrialData);
				}
			}
		}
		return result;
	}
	

//	删除指定试用活动的推荐商品
	@Override
	public int deleteTrialDataByTrialId(Long trialId) {
		if (trialId == null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		}
		return diyTrialBackMapper.deleteDiyTrialData(null, trialId);
		
	}

	/**
	 * 校验参数信息
	 * 
	 * @Description: 校验参数信息
	 * @param: diyTrial 试用活动
	 * @return: 
	 * @author: lily
	 * @date: 2017年5月5日
	 */
	private void checkParams(DiyTrial diyTrial) {
		if (diyTrial == null)
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + ",diyTrial:null");
		if (diyTrial.getProductId()==null || diyTrial.getTrialNum() == null||StringUtil.isEmpty(diyTrial.getIsChargeDeposit())
				|| diyTrial.getStartTime() == null || diyTrial.getPreheatEndTime() == null || diyTrial.getApplyEndTime() == null
				|| diyTrial.getEndTime() == null||diyTrial.getStatus()==null) {
			throw new ServiceException(Message.SYSTEM_10104.getCode(), Message.SYSTEM_10104.getMsg() + 
					",productId:" + diyTrial.getProductId() + 
					",trialNum:" + diyTrial.getTrialNum() + 
					",isChargeDeposit:" +diyTrial.getIsChargeDeposit()+
					",startTime:"+ diyTrial.getStartTime() + 
					",preheatEndTime:"+ diyTrial.getPreheatEndTime() + 
					",applyEndTime:"+ diyTrial.getApplyEndTime() + 
					",endTime:" + diyTrial.getEndTime() + 
					",status:" + diyTrial.getStatus());
		}
//		判断支付押金的活动添加的是否为众创商品
		if("Y".equals(diyTrial.getIsChargeDeposit())){
			Map<String,Object> map=diyProductBackMapper.getProductById(diyTrial.getProductId());
			if(map==null||map.get("model")==null||!"ZC".equals(map.get("model").toString()))
				throw new ServiceException(Message.ADMIN_2012021.getCode());
		}
//		判断设置时间
		if (diyTrial.getStartTime().getTime() > diyTrial.getPreheatEndTime().getTime()) {
			throw new ServiceException(Message.ADMIN_2012002.getCode());
		}else if(diyTrial.getPreheatEndTime().after(diyTrial.getApplyEndTime())){
			throw new ServiceException(Message.ADMIN_2012003.getCode());
		}else if(diyTrial.getApplyEndTime().after(diyTrial.getEndTime())){
			throw new ServiceException(Message.ADMIN_2012004.getCode());
		}else if(diyTrial.getEndTime().before(new Date())){
			throw new ServiceException(Message.ADMIN_2012005.getCode());
		}
	}
}
