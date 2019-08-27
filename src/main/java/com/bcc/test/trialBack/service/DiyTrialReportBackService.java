package com.bcc.test.trialBack.service;

import com.bcc.test.common.utils.Page;
import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trial.service.com;
import com.bcc.test.trialBack.domain.DiyTrialReportSupper;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface DiyTrialReportBackService {
	/**
	 * 分页查询所有试用报告公示报告列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public Page<Map> getPublicTrialReportByPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 修改试用报告排序
	 * @param id    		试用报告id
	 * @param sort          序号
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public int updateTrialReportSort(Long id, Integer sort);
	
	/**
	 * 推荐指定的试用报告
	 * @param id
	 * @param sort
	 * @author lily
	 * @date 2017-05-17
	 * @return
	 */
	public int recommendTrialReport(Long id, String isRecommend);
		
	/**
	 * 启用/禁用试用报告
	 * @param id  		试用报告主键标识
	 * @param isShow    显示状态（Y启用   N禁用）
	 * @return int
	 * @author lily
	 * @date 2017-05-17
	 */
	public int onAndOffTrialReport(Long id, String isShow);

	/**
	 * 删除试用报告
	 * @param id
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public int deleteTrialReport(Long id);
	
	/**
	 * 分页查询指定试用报告所有报告列表
	 * @param  trialId   试用报告id
	 * @param  pageNum
	 * @param  pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public JSONObject getTrialReportByPage(Long trialId, Integer pageNum, Integer pageSize);


	/**
	 * 获取指定的试用报告信息
	 * @param id
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public DiyTrialReportSupper getTrialReportById(Long id);


	/**
	 * 审核试用报告
	 * @param id    		试用报告id
	 * @param type          审批类型   1：合格  2：修改   3：不合格
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public DiyTrialReportSupper auditTrialReport(Long id, Integer type);

	/**
	 * 上传试用报告封面图
	 * @param id    		试用报告id
	 * @param cover         封面图
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	public int uploadTrialReportCover(Long id, String cover);

	/**
	 * 查询指定的多个试用报告列表
	 * @param idsList   试用报告id
	 * @return
	 * @author lily
	 * @date  2017-05-18
	 */
	public List<DiyTrialReport> selectTrialReportByIds(List<Long> idsList);

	/**
	 * 公示试用报告
	 * @param trialId
	 * @param diyTrialReportList
	 * @return
	 * @author lily
	 * @date  2017-05-18
	 */
	public int publicTrialReport(Long trialId,
                                 List<DiyTrialReport> diyTrialReportList);

	/**
	 * 指定试用报告添加购买链接
	 * @param id     试用报告id
	 * @param pcUrl	 pc端购买链接
	 * @param mUrl   移动端购买链接
	 * @return
	 * @author lily
	 * @date  2018-04-13日
	 */
	public int updateTrialReportBuyUrl(Long id, String pcUrl, String mUrl);
	
	




	





}
