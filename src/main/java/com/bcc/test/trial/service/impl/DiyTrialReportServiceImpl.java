package com.bcc.test.trial.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.bcc.test.common.utils.*;
import com.bcc.test.core.system.ServiceException;
import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trial.domain.DiyTrialReportCus;
import com.bcc.test.trial.domain.DiyTrialReportLike;
import com.bcc.test.trial.mapper.DiyTrialMapper;
import com.bcc.test.trial.mapper.DiyTrialReportMapper;
import com.bcc.test.trial.service.DiyTrialReportService;
import io.terminus.pampas.common.UserUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 试用报告Service实现类
 * @author lily
 * @date 2017-05-24
 */
@Service
public class DiyTrialReportServiceImpl implements DiyTrialReportService{
	@Autowired
	private DiyTrialReportMapper  diyTrialReportMapper;
	@Autowired
	private DiyTrialMapper  diyTrialMapper;

//	分页多条件查询试用报告列表
	@Override
	public Page<Map> getTrialReportByPage(Integer pageNum, Integer pageSize,Long trialId,
			String isRecommend) {
		if(pageSize == null || pageSize <= 0)
			pageSize=12;
		int total=diyTrialReportMapper.getTrialReportCount(trialId,isRecommend);
		Page<Map> page=new Page<Map>(pageNum, pageSize,total);
		List<Map<String,Object>> list=diyTrialReportMapper.getTrialReportByPage(page.getStartItem(),page.getPageSize(),trialId,isRecommend);
		if(list!=null&&list.size()>0){
//			判断各试用报告当前所处节点
			for(Map<String,Object> map:list){
				if(map.get("cover")!=null){
					map.put("cover",StringUtil.covertFullImg(map.get("cover").toString()));
				}
				if(map.get("headImg")!=null){
					map.put("headImg",StringUtil.covertFullImg(map.get("headImg").toString()));
				}
				if(map.get("content")!=null){
					String content=map.get("content").toString();
					List<String> imgItems=getImgs(content);
					if(imgItems!=null&&imgItems.size()!=0){
						map.put("imgItems", imgItems);
					}
				}
			}
		}else{
			list=new ArrayList<Map<String,Object>>();
		}
		page.setItems(BeanToMapUtil.convertList(list, false));
		return page;
	}

//	获取指定的试用报告详情
	@Override
	public DiyTrialReportCus getTrialReportById(Long reportId) {
		if(reportId==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		Long userId=UserUtil.getUserId();
		DiyTrialReportCus   report=diyTrialReportMapper.getTrialReportById(reportId, userId);
		if(report!=null){
//			判断申购人数是否大于13  大于+基数 
			if(report.getTrialUserNum()!=null&&report.getTrialUserNum().intValue()>13){
				report.setTrialUserNum((report.getBase()==null?0:report.getBase().intValue())+report.getTrialUserNum().intValue());
			}
			
			report.setCover(StringUtil.covertFullImg(report.getCover()));			//报告封面图
			report.setPcCover(StringUtil.covertFullImg(report.getPcCover()));		//pc版产品图
			report.setmCover(StringUtil.covertFullImg(report.getmCover()));			//m版产品图
			report.setHeadImg(StringUtil.covertFullImg(report.getHeadImg()));		//头像
//			判断该报告是不是启用
			if("N".equals(report.getIsShow())){//-------------未启用
//				判断是不是自己的
				if(userId==null||userId.longValue()!=report.getUserId().longValue()){//------------不是自己的
					
					throw new ServiceException(Message.PUBLIC_1012004.getCode());
				}
			}
		}else{
			throw new ServiceException(Message.PUBLIC_1012004.getCode());
		}
		return report;
	}
//	获取指定的试用报告基本信息
	@Override
	public DiyTrialReport selectTrialReportById(Long reportId) {
		if(reportId==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		DiyTrialReport   report=diyTrialReportMapper.selectTrialReportById(reportId);
		if(report!=null){
			report.setCover(StringUtil.covertFullImg(report.getCover()));			//报告封面图
		}else{
			throw new ServiceException(Message.PUBLIC_1012004.getCode());
		}
		return report;
	}

//	新增试用报告
	@Override
	public int addTrialReport(String report) {
		if(StringUtil.isEmpty(report))
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		if(userId==null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		JSONObject reportJson= JSONObject.fromObject(report);
		if(reportJson.get("trialId")==null||reportJson.get("trialApplicationId")==null||reportJson.get("cover")==null
				||reportJson.get("summary")==null||reportJson.get("content")==null||reportJson.get("comprehensiveScore")==null||
				reportJson.get("functionScore")==null||reportJson.get("appearanceScore")==null||reportJson.get("performanceScore")==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		DiyTrialReport  diyTrialReport=diyTrialReportMapper.getTrialReportByApplicationId(reportJson.getLong("trialApplicationId"));
		if(diyTrialReport!=null){
			throw new ServiceException(Message.PUBLIC_1012011.getCode());
		}else{
			  diyTrialReport=new DiyTrialReport();
		}
		diyTrialReport.setTrialId(reportJson.getLong("trialId"));
		diyTrialReport.setTrialApplicationId(reportJson.getLong("trialApplicationId"));
		diyTrialReport.setUserId(userId);
		diyTrialReport.setCover(reportJson.getString("cover"));
		diyTrialReport.setSummary(StringUtil.formartHtmlStr(reportJson.getString("summary")));
		diyTrialReport.setContent(reportJson.getString("content"));
		diyTrialReport.setComprehensiveScore(reportJson.getInt("comprehensiveScore"));
		diyTrialReport.setFunctionScore(reportJson.getInt("functionScore"));
		diyTrialReport.setAppearanceScore(reportJson.getInt("appearanceScore"));
		diyTrialReport.setPerformanceScore(reportJson.getInt("performanceScore"));
		diyTrialReport.setStatus(0);
		int result=diyTrialReportMapper.insertTrialReport(diyTrialReport);
		return result;
	}

//	修改试用报告
	@Override
	public int updateTrialReport(String report) {
		if(StringUtil.isEmpty(report))
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		if(userId==null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		JSONObject reportJson= JSONObject.fromObject(report);
		if(reportJson.get("id")==null||reportJson.get("cover")==null||reportJson.get("summary")==null
				||reportJson.get("content")==null||reportJson.get("comprehensiveScore")==null||reportJson.get("functionScore")==null
				||reportJson.get("appearanceScore")==null||reportJson.get("performanceScore")==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		
		DiyTrialReport  diyTrialReport=diyTrialReportMapper.selectTrialReportById(reportJson.getLong("id"));
		if(diyTrialReport==null)
			throw new ServiceException(Message.PUBLIC_1012004.getCode());
		
		if(diyTrialReport.getStatus()!=0&&diyTrialReport.getStatus()!=2)
			throw new ServiceException(Message.PUBLIC_1012003.getCode());
		
		if(!diyTrialReport.getUserId().equals(userId))
			throw new ServiceException(Message.PUBLIC_1012010.getCode());
		
		diyTrialReport.setCover(reportJson.getString("cover"));
		diyTrialReport.setSummary(StringUtil.formartHtmlStr(reportJson.getString("summary")));
		diyTrialReport.setContent(reportJson.getString("content"));
		diyTrialReport.setComprehensiveScore(reportJson.getInt("comprehensiveScore"));
		diyTrialReport.setFunctionScore(reportJson.getInt("functionScore"));
		diyTrialReport.setAppearanceScore(reportJson.getInt("appearanceScore"));
		diyTrialReport.setPerformanceScore(reportJson.getInt("performanceScore"));
		diyTrialReport.setStatus(0);
		diyTrialReport.setCreateTime(new Date());
		int result=diyTrialReportMapper.updateTrialReport(diyTrialReport);
		return result;
	}
	
//	点赞或者点踩
	@Override
	public int addLike(Long reportId,Integer type) {
		if(reportId==null||type==null)
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		DiyTrialReport  trialReport=diyTrialReportMapper.selectTrialReportById(reportId);
		if(trialReport==null)
			throw new ServiceException(Message.PUBLIC_1012004.getCode());
//		判断用户是否已经点过踩或点过赞
		Long userId=UserUtil.getUserId();
//		Long userId=11783l;
		DiyTrialReportLike  like=null;
		if(type==1){
			like=diyTrialReportMapper.selectReportLikeByUserId(reportId, userId, IntegerUtil.TWO);
			if(like!=null){
				throw new ServiceException(Message.PC_1011005.getCode());
			}else{
				like=diyTrialReportMapper.selectReportLikeByUserId(reportId, userId, IntegerUtil.ONE);
				if(like!=null)
					throw new ServiceException(Message.PC_1006003.getCode());
			}
		}else if(type==2){
			like=diyTrialReportMapper.selectReportLikeByUserId(reportId, userId, IntegerUtil.ONE);
			if(like!=null){
				throw new ServiceException(Message.PC_1011004.getCode());
			}else{
				like=diyTrialReportMapper.selectReportLikeByUserId(reportId, userId, IntegerUtil.TWO);
				if(like!=null)
					throw new ServiceException(Message.PC_1011003.getCode());
			}
		}
		int result = 0;
		DiyTrialReportLike diyTrialReportLike=new DiyTrialReportLike();
		diyTrialReportLike.setReportId(reportId);
		diyTrialReportLike.setType(type);
		diyTrialReportLike.setUserId(userId);
		result = diyTrialReportMapper.insertLike(diyTrialReportLike);
		if(result > 0){	//更新点赞/点踩数量
			DiyTrialReport diyTrialReport = new DiyTrialReport();
			diyTrialReport.setId(diyTrialReportLike.getReportId());
			if(type==1){
				diyTrialReport.setLikeNum(IntegerUtil.ONE);
			}else{
				diyTrialReport.setDislikeNum(IntegerUtil.ONE);
			}
			result = diyTrialReportMapper.updateTrialReportNum(diyTrialReport);
		}
		return result;
	}
	
//	提取试用报告内容中的图片
	private static List<String>  getImgs( String content){
//		Pattern ATTR_PATTERN = Pattern.compile("<img[^<>]*?\\ssrc=['\"]?(.*?)['\"]?\\s.*?>",Pattern.CASE_INSENSITIVE);
		Pattern ATTR_PATTERN = Pattern.compile("<img\\s+(?:[^>]*)src\\s*=\\s*['\"]([^>]+)['\"]",   Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
		List<String> imgList=new ArrayList<String>();
		if(StringUtils.isNotEmpty(content)){
		  Matcher matcher = ATTR_PATTERN.matcher(content);
		   while (matcher.find()) {
			   if(imgList.size()<2){
				   imgList.add(StringUtil.covertFullImg(matcher.group(1)));
			   }else{
				   break;
			   }
		   }
		}
		return imgList;
	}

//	获取指定用户的试用报告列表
	@Override
	public List<DiyTrialReport> getReportListByUserId(Long userId) {
		if(userId == null){
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		}
		List<DiyTrialReport> list=diyTrialReportMapper.getReportListByUserId(userId);
		return list;
	}

//	 批量修改指定试用报告的userId
	@Override
	public int updateReportUserId(List<Long> list, Long userId) {
		if (list == null || list.size() <= 0 ) 
			throw new ServiceException(Message.SYSTEM_10104.getCode());
		if (userId == null)
			throw new ServiceException(Message.SYSTEM_10102.getCode());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("userId", userId);
		Integer result = diyTrialReportMapper.updateReportUserId(map);
		return result;
	}


}
