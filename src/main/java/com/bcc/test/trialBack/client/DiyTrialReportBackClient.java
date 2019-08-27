package com.bcc.test.trialBack.client;

import io.terminus.pampas.client.Export;
import net.sf.json.JSONObject;

/**
 * 试用报告接口
 * ClassName:DiyTrialReportBackClient
 * @Description:试用报告业务接口
 * @author lily
 * @date 2017-05-17
 *
 */
public interface DiyTrialReportBackClient {
	/**
	 * 分页查询所有公示试用报告列表
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"pageNum", "pageSize"})
	public JSONObject getPublicTrialReportByPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 修改试用报告排序
	 * @param id
	 * @param sort
	 * @return
	 * @author lily
	 * @date  2017-05-17
	 */
	@Export(paramNames={"id","sort"})
	public JSONObject  updateTrialReportSort(Long id, Integer sort);

	/**
	 * 启用/禁用试用报告
	 * @param id        试用报告id
	 * @param isShow    状态（Y：启用   N：禁用）
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"id", "isShow"})
	public JSONObject onAndOffTrialReport(Long id, String isShow);

	/**
	 * 推荐/不推荐试用报告
	 * @param id        试用报告id
	 * @param isRecommend    状态（Y：推荐   N：不推荐）
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"id", "isRecommend"})
	public JSONObject recommendTrialReport(Long id, String isRecommend);

	/**
	 * 删除试用报告
	 * @param id   试用报告id
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	@Export(paramNames={"id"})
	public JSONObject deleteTrialReport(Long id);

	/**
	 * 分页查询指定试用活动的所有试用报告列表
	 * @param trialId  试用报告id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"trialId", "pageNum", "pageSize"})
	public JSONObject getTrialReportByPage(Long trialId, Integer pageNum, Integer pageSize);


	/**
	 * 获取指定的试用报告信息
	 * @param id   试用报告主键
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"id"})
	public JSONObject getTrialReportById(Long id);

	/**
	 * 上传试用报告封面图
	 * @param id        试用报告id
	 * @param cover
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"id", "cover"})
	public JSONObject uploadTrialReportCover(Long id, String cover);

	/**
	 * 审批试用报告
	 * @param id	 试用报告id
	 * @param type   审批类型  1：合格   2：允许修改   3：不合格
	 * @return
	 * @author lily
	 * @date 2017-05-17
	 */
	@Export(paramNames={"id", "type"})
	public JSONObject auditTrialReport(Long id, Integer type);

	/**
	 * 公示试用报告
	 * @param trialId			试用报告id
	 * @param ids				被公示的报告id    多个以,进行拼接  例如1,2,3
	 * @return
	 */
	@Export(paramNames={"trialId","ids"})
	public JSONObject publicTrialReport(Long trialId, String ids);

	/**
	 * 试用报告添加购买链接
	 * @param id		试用报告id
	 * @param pcUrl		pc端购买链接
	 * @param mUrl		移动端购买链接
	 * @return
	 * @author lily
	 * @date   2018-04-13
	 */
	@Export(paramNames={"id","pcUrl","mUrl"})
	public JSONObject  updateTrialReportBuyUrl(Long id, String pcUrl, String mUrl);
	

	

	


}
