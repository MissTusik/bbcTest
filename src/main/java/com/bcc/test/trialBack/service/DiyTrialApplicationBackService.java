package com.bcc.test.trialBack.service;

import com.bcc.test.common.utils.Page;
import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.trial.service.com;
import com.bcc.test.trialBack.domain.DiyTrialApplicationSupper;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 试用活动申请用户业务逻辑
 * @author lily
 * @date 2017-05-09
 *
 */
public interface DiyTrialApplicationBackService {
	/**
	 * 分页查询指定试用活动的申请用户列表
	 * @param trialId   指定的试用活动id
	 * @param pageNum   当前页码
	 * @param pageSize  每页个数
	 * @return
	 * @author lily
	 * @date 2017-05-09
	 */
	public Page<Map> getTrialUserByPage(Long trialId, Integer pageNum, Integer pageSize);
	
	/**
	 * 设置申请用户中签状态
	 * @param diyTrialUserList
	 * @author lily
	 * @date 2017-05-10
	 */
	public int updateTrialUserBallot(List<DiyTrialApplication> diyTrialUserList);
	
	/**
	 * 删除用户申请
	 * @param id
	 * @author lily
	 * @date 2017-05-11
	 */
	public int deleteTrialUser(Long id);
	
	/**
	 * 分页查询指定试用活动的中签申请用户列表
	 * @param trialId   指定的试用活动id
	 * @param isBallot  中签标识
	 * @param pageNum   当前页码
	 * @param pageSize  每页个数
	 * @return
	 * @author lily
	 * @date 2017-05-09
	 */
	public JSONObject getTrialBallotUserByPage(Long trialId, String isBallot, Integer pageNum, Integer pageSize);

	/**
	 * 设置申请中签用户公示状态
	 * @param diyTrialUserList
	 * @author lily
	 * @date 2017-05-10
	 */
	public int updateTrialUserPublic(Long trialId, Integer validUserNum, List<DiyTrialApplication> diyTrialUserList);

	/**
	 * 获取指定的试用申请详细信息
	 * @param id
	 * @return
	 * @author lily
	 * @date 2017-05-05
	 */
	public DiyTrialApplicationSupper getTrialUserById(Long id);


	/**
	 * 设置试用申请付款时间
	 * @param id  试用活动主键标识
	 * @param payStartTime   付款开始时间
	 * @param payEndTime     付款结束时间
	 * @return int
	 * @author lily
	 * @date 2017-05-10
	 */
	public int updateTrialUserPayTime(Long id, String payStartTime, String payEndTime);
	/**
	 * 设置试用申请付款时间
	 * @param id  试用活动主键标识
	 * @param expressCompany   快递公司名称
	 * @param expressNo        快递公司单号
	 * @return int
	 * @author lily
	 * @date 2017-05-10
	 */
	public int updateExpressInfo(Long id, String expressCompany, String expressNo);
	/**
	 * 设置申请理由是否显示
	 * @param id               申请id
	 * @param isShowReason     是否显示申请理由
	 * @return
	 * @author lily
	 * @date 2017-05-15
	 */
	public int showAndHiddenReason(Long id, String isShowReason);
	
	/**
	 * 获取指定的id的用户列表
	 * @param idsList   用户id集合
	 * @return
	 * @author lily
	 * @date 2017-05-15
	 */
	public List<DiyTrialApplication> selectTrialUserByIds(List<Long> idsList);
	
	/**
	 * 功能：试用活动结束，未提交试用报告的，且已付定金的订单自动设置为超期未付尾款
	 */
	public void   autoDeduceDeposit();








}
