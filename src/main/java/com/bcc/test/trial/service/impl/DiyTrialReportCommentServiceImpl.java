package com.bcc.test.trial.service.impl;

import com.haier.diy.common.utils.*;
import com.haier.diy.core.system.ServiceException;
import com.haier.diy.trial.domain.DiyTrialReport;
import com.haier.diy.trial.domain.DiyTrialReportComment;
import com.haier.diy.trial.domain.DiyTrialReportCommentCus;
import com.haier.diy.trial.mapper.DiyTrialReportCommentMapper;
import com.haier.diy.trial.mapper.DiyTrialReportMapper;
import com.haier.diy.trial.service.DiyTrialReportCommentService;
import com.haier.diy.user.api.MessageConsts;
import com.haier.diy.user.service.DiyMessageService;
import com.haier.diy.user.service.DiyReportService;
import io.terminus.pampas.common.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试用报告评论实现类
 * ClassName: DiyProductTopicServiceImpl 
 * @Description: 试用报告评论实现类
 * @author lily
 * @date 2017-05-26
 */
@Service
public class DiyTrialReportCommentServiceImpl implements DiyTrialReportCommentService {

	@Autowired
	private DiyTrialReportCommentMapper diyTrialReportCommentMapper;
	@Autowired
	private DiyTrialReportMapper diyTrialReportMapper;
	
	@Autowired
	private DiyReportService diyReportService;
	
	@Autowired
	private DiyMessageService diyMessageService;
	//首次获取子评论数量
	public final static Integer FIRSTNUM=3;
	
	/**
	 * 获取指定试用报告评论
	 */
	@Override
	public Page<Map> getTrialReportCommentByPage(Integer pageNum,Integer pageSize,
			Long reportId) {
		if (reportId==null) 
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		if (pageSize==null) pageSize=8;
		if (pageNum==null) pageNum=1;
		//获取评论总数
		int total = diyTrialReportCommentMapper.getTrialReportCommentTotal(reportId);
		if(total==0) return null;
		Page<Map> page=new Page<Map>(pageNum, pageSize, total);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("type", "parent");
		map.put("reportId", reportId);
		
		List<DiyTrialReportCommentCus> items = diyTrialReportCommentMapper.getTrialReportComment(map);
		if(items==null) return null;
		//循环获取顶级评论下的子级评论
		for (int i = 0; i < items.size(); i++) {
			//子评论首次展示数量
			pageSize=FIRSTNUM;
			List<DiyTrialReportCommentCus> item = getChildCommentByPage(items.get(i).getId(),1,pageSize,reportId).getItems();
			for(DiyTrialReportCommentCus cus:item){
				cus.setHeadImg(StringUtil.covertFullImg(cus.getHeadImg()));
			}
			//获取子级评论总数
			Map<String, Object> childMap = new HashMap<String, Object>();
			childMap.put("groupId", items.get(i).getId());
			childMap.put("reportId", reportId);
			int childTotal = diyTrialReportCommentMapper.getChildTrialReportCommentTotal(childMap);
			//计算子级话题剩余数
			int count = childTotal-(1*pageSize);
			items.get(i).setSurplusNum(count<0?0:count);
			items.get(i).setItem(BeanToMapUtil.convertList(item, false));
			items.get(i).setReplyCount(childTotal);
			items.get(i).setHeadImg(StringUtil.covertFullImg(items.get(i).getHeadImg()));
			
		}
		page.setItems(BeanToMapUtil.convertList(items, false));
		
		return page;
	}
	
	
	/**
	 * 分页获取指定评论下的子级评论(纯粹分页查询，不考虑初始页显示显示的情况)
	 */
	@Override
	public Page<DiyTrialReportCommentCus> getChildCommentByPage(Long groupId, Integer pageNum,
			Integer pageSize, Long reportId) {
		if (pageNum==null) pageNum=1;
		if (pageSize==null) pageSize=3;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId", groupId);
		map.put("reportId", reportId);
		int total = diyTrialReportCommentMapper.getChildTrialReportCommentTotal(map);
		Page<DiyTrialReportCommentCus> page=new Page<DiyTrialReportCommentCus>(pageNum, pageSize, total);
		map.put("type", "child");
		map.put("page", page);
		List<DiyTrialReportCommentCus> items = diyTrialReportCommentMapper.getTrialReportComment(map);
		for(DiyTrialReportCommentCus cus:items){
			cus.setHeadImg(StringUtil.covertFullImg(cus.getHeadImg()));
		}
		page.setItems(items);
		return page;
	}
	
	/**
	 * 创建试用报告评论并获取当前评论
	 */
	@Override
	public DiyTrialReportCommentCus createTrialReportCommentAndGet(DiyTrialReportComment diyTrialReportComment){
		//校验当前用户是否被举报
		diyReportService.getCheckUserFunResult(IntegerUtil.SIX, UserUtil.getUserId());
//		diyReportService.getCheckUserFunResult(IntegerUtil.SIX, 11783l);
		
		if (diyTrialReportComment==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		if (diyTrialReportComment.getReportId()==null || StringUtil.isEmpty(diyTrialReportComment.getContent()))
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		
		if(diyTrialReportComment.getGroupId() == null) 
			diyTrialReportComment.setGroupId(new Long(0));
		if(diyTrialReportComment.getToUserId() == null) 
			diyTrialReportComment.setToUserId(new Long(0));
		if(diyTrialReportComment.getParentId() == null||diyTrialReportComment.getParentId()==0){
			diyTrialReportComment.setParentId(new Long(0));
		}else{
			DiyTrialReportComment comment=diyTrialReportCommentMapper.getTrialReportCommentById(diyTrialReportComment.getParentId());
			if(comment==null)
				throw new ServiceException(Message.SYSTEM_10105.getCode());
			diyTrialReportComment.setToUserId(comment.getUserId());
			diyTrialReportComment.setParentId(comment.getId());
			diyTrialReportComment.setGroupId(comment.getGroupId()==0?comment.getId():comment.getGroupId());
		}
		
		DiyTrialReport  diyTrialReport=diyTrialReportMapper.selectTrialReportById(diyTrialReportComment.getReportId());
		if(diyTrialReport==null)
			throw new ServiceException(Message.PUBLIC_1012004.getCode(),Message.PUBLIC_1012004.getMsg()+"reportId:"+diyTrialReportComment.getReportId());
		Boolean result=diyTrialReportCommentMapper.createTrialReportComment(diyTrialReportComment);
		DiyTrialReportCommentCus diyTrialReportCommentCus=null;
		if (result) {
//			更新评论数
			int commentNum=diyTrialReport.getCommentNum()==null?0:diyTrialReport.getCommentNum();
			diyTrialReport.setCommentNum(commentNum+1);
			diyTrialReportMapper.updateTrialReport(diyTrialReport);
			if(diyTrialReportComment.getToUserId() !=null 
					&& diyTrialReportComment.getToUserId() != (long)IntegerUtil.ZERO 
					&& !diyTrialReportComment.getUserId().equals(diyTrialReportComment.getToUserId())){
				diyMessageService.addMessage(6,"您的试用报告评论被回复了","",StringUtil.URL_TRIAL_REPORT_FIX+diyTrialReportComment.getReportId(),
						diyTrialReportComment.getContent(),diyTrialReportComment.getUserId(),diyTrialReportComment.getToUserId(),0L,
						diyTrialReportComment.getReportId(),MessageConsts.MSG_TRIAL);
			}
			diyTrialReportCommentCus = diyTrialReportCommentMapper.getTrialReportCommentCusById(diyTrialReportComment.getId());
		}
		return diyTrialReportCommentCus;
	}

//	获取指定用户的试用报告列表
	@Override
	public List<DiyTrialReportComment> getReportCommentListByUserId(Long userId) {
		if(userId == null){
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		}
		List<DiyTrialReportComment> list=diyTrialReportCommentMapper.getReportCommentListByUserId(userId);
		return list;
	}

//	批量修改指定试用报告评论的userId
	@Override
	public int updateReportCommentUserId(List<Long> list, Long userId) {
		if (list == null || list.size() <= 0 ) 
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		if (userId == null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("userId", userId);
		Integer result = diyTrialReportCommentMapper.updateReportCommentUserId(map);
		return result;
	}

}
