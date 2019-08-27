package com.bcc.test.trialBack.client;

import io.terminus.pampas.client.Export;
import net.sf.json.JSONObject;

/**
 * 试用申请用户接口
 * ClassName:DiyTrialUserBackClient
 * @Description:试用申请用户业务接口
 * @author lily
 * @date 2017-05-09
 *
 */
public interface DiyTrialUserBackClient {
	/**
	 * 分页查询指定试用活动全部申请用户列表
	 * @param trialId  指定的试用活动id
	 * @param pageNum  当前页码
	 * @param pageSize 每个显示个数
	 * @return
	 * @author lily
	 * @date 2017-05-09
	 */
	@Export(paramNames={"trialId","pageNum", "pageSize"})
	public JSONObject getTrialUserByPage(Long trialId, Integer pageNum, Integer pageSize);

	/**
	 * 更新用户中签状态
	 * @param ids				选中申请用户id   格式：1,2,3
	 * @return
	 * @author lily
	 * @date 2017-05-10
	 */
	@Export(paramNames={"ids"})
	public JSONObject updateTrialUserBallot(String ids);
	/**
	 * 删除指定的申请记录
	 * @param id			试用申请id
	 * @return
	 * @author lily
	 * @date 2017-05-11
	 */
	@Export(paramNames={"id"})
	public JSONObject deleteTrialUser(Long id);

	/**
	 * 显示或隐藏指定的申请记录的申请理由
	 * @param id			试用申请id
	 * @return
	 * @author lily
	 * @date 2017-05-15
	 */
	@Export(paramNames={"id","isShowReason"})
	public  JSONObject   showAndhHiddenReason(Long id, String isShowReason);

	/**
	 * 分页查询指定试用活动全部中签用户列表
	 * @param trialId  指定的试用活动id
	 * @param isBallot 中签标识 Y
	 * @param pageNum  当前页码
	 * @param pageSize 每个显示个数
	 * @return
	 * @author lily
	 * @date 2017-05-09
	 */
	@Export(paramNames={"trialId","isBallot","pageNum", "pageSize"})
	public JSONObject getBallotTrialUserByPage(Long trialId, String isBallot, Integer pageNum, Integer pageSize);



	/**
	 * 更新用户公示状态
	 * @param trialId   试用活动id
	 * @param validUserNum  有效的用户数
	 * @param ids       公示的用户申请id   格式：1,2,3
	 * @return
	 * @author lily
	 * @date  2017-05-05
	 */
	@Export(paramNames={"trialId","validUserNum","ids"})
	public JSONObject  updateTrialUserPublic(Long trialId, Integer validUserNum, String ids);

	/**
	 * 修改试用申请用户付款时间
	 * @param productId			申请记录id
	 * @param payStartTime		付款开始时间
	 * @param payEndTime		付款结束时间
	 * @return
	 * @author lily
	 * @date 2017-05-10
	 */
	@Export(paramNames={"id","payStartTime", "payEndTime"})
	public JSONObject updateTrialUserPayTime(Long id, String payStartTime, String payEndTime);

	/**
	 * 获取指定的试用申请用户详情
	 * @param id   试用申请主键
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	@Export(paramNames={"id"})
	public JSONObject getTrialUserById(Long id);

	/**
	 * 修改试用申请的快递信息（快递公司和快递单号）
	 * @param id        		试用申请id
	 * @param expressCompany    快递公司名称
	 * @param expressNo         快递单号
	 * @return
	 * @author lily
	 * @date 2017-05-04
	 */
	@Export(paramNames={"id", "expressCompany", "expressNo"})
	public JSONObject updateExpressInfo(Long id, String expressCompany, String expressNo);
	

	


}
