package com.bcc.test.trial.client;

import io.terminus.pampas.client.Export;
import net.sf.json.JSONObject;

/**
 * 试用活动申请Client
 * @author lily
 * @date  2017-06-01
 */
public interface DiyTrialApplicationClient {
	/**
	 * 分页获取指定条件的申购用户
	 * @param pageNum		当前页码
	 * @param pageSize		每页显示个数
	 * @param trialId		试用活动id
	 * @param isPublic		用户是否公示   Y:是  N：否
	 * @return
	 * @author lily
	 * @date  2017-05-27
	 */
	@Export(paramNames={"pageNum","pageSize","trialId","isPublic"})
	public JSONObject  getTrialUserByPage(Integer pageNum, Integer pageSize, Long trialId, String isPublic);

	/**
	 * 分页查看申请理由
	 * @param pageNum    当前页码
	 * @param pageSize   每页显示个数
	 * @param trialId	 试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-31
	 */
	@Export(paramNames={"pageNum","pageSize","trialId"})
	public JSONObject   getTrialReasonByPage(Integer pageNum, Integer pageSize, Long trialId);

	/**
	 * 分页查看指定申请理由的评论
	 * @param pageNum    			 当前页码
	 * @param pageSize   			 每页显示个数
	 * @param trialApplicationId	 试用申请id
	 * @return
	 * @author lily
	 * @date   2017-05-31
	 */
	@Export(paramNames={"pageNum","pageSize","trialApplicationId"})
	public JSONObject   getTrialReasonCommentByPage(Integer pageNum, Integer pageSize, Long trialApplicationId);

	/**
	 * 发表/回复申请理由评论
	 * @param trialApplicationId
	 * @param parentId
	 * @param content
	 * @return
	 * @author lily
	 * @date   2017-05-31
	 */
	@Export(paramNames={"trialApplicationId","parentId","content"})
	public JSONObject   createTrialReasonComment(Long trialApplicationId, Long parentId, String content);

	/**
	 * 发送验证码短信
	 * @param mobile		手机号
	 * @param trialId		试用活动
	 * @return
	 * @author lily
	 * @date  2017-06-01
	 */
	@Export(paramNames={"mobile","trialId"})
	public  JSONObject  sendVerificationCode(String mobile, Long trialId);


	/**
	 * 提交申请
	 * @param trialId				试用活动id
	 * @param name					姓名
	 * @param sex					性别 1男  2 女 0保密
	 * @param mobile				手机号
	 * @param varificationCode		验证码
	 * @param reason				申请理由
	 * @return
	 * @author lily
	 * @date  2017-06-01
	 */
	@Export(paramNames={"trialId","name","sex","mobile","verificationCode","reason"})
	public JSONObject    createTrialApplication(Long trialId, String name, Integer sex, String mobile, String verificationCode, String reason);

	/**
	 * 按类别分页获取我的收藏
	 * @param pageNum     当前页码
	 * @param pageSize    每页显示个数大小
	 * @param type		  查询类别    1：申请中      2：申请成功   3：申请失败
	 * @return
	 * @author lily
	 * @date  2017-06-05
	 */
	@Export(paramNames={"pageNum","pageSize","type"})
	public JSONObject    getMyApplicationByPage(Integer pageNum, Integer pageSize, Integer type);

	/**
	 * 删除指定的试用申请
	 * @param id      试用申请id
	 * @return
	 * @author lily
	 * @date  2017-06-06
	 */
	@Export(paramNames={"id"})
	public JSONObject    deleteTrialApplication(Long id);

	/**
	 * 设置收货地址
	 * @param applicationId			试用申请id
	 * @param addressId				地址id
	 * @return
	 * @author lily
	 * @date   2017-06-12
	 */
	@Export(paramNames={"applicationId","addressId"})
	public JSONObject    updatTrialApplicationAddress(Long applicationId, Long addressId);
	
}
