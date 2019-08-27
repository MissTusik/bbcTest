package com.bcc.test.trial.service;

import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.trial.domain.DiyTrialReasonComment;
import com.bcc.test.common.utils.Page;

import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * 试用申请
 * @author lily
 * @date  2017-05-31
 */
public interface DiyTrialApplicationService {
	
	/**
	 * 分页获取指定用户的列表
	 * @param pageNum			当前页码
	 * @param pageSize			每页个数
	 * @param trialId			试用活动id
	 * @param isPublic			公示标识
	 * @return
	 * @author lily
	 * @date  2017-05-24
	 */
	public List<Map> getTrialUserByPage(Integer pageNum, Integer pageSize, Long trialId, String isPublic);

	/**
	 * 分页获取申请理由
	 * @param pageNum
	 * @param pageSize
	 * @param trialId		试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-31
	 */
	public Page<Map>   getTrialReasonByPage(Integer pageNum, Integer pageSize, Long trialId);

	/**
	 * 分页获取指定申请理由的评论
	 * @param pageNum
	 * @param pageSize
	 * @param trialApplicationId	试用申请id
	 * @return
	 * @author lily
	 * @date  2017-05-31
	 */
	public Page<Map> getTrialReasonCommentByPage(Integer pageNum,
                                                 Integer pageSize, Long trialApplicationId);

	/**
	 * 发表或回复申请理由评论
	 * @param trialApplicationId	试用申请id
	 * @param parentId				上层评论id
	 * @param content				评论内容
	 * @return
	 * @author lily
	 * @date  2017-05-31
	 */
	public DiyTrialReasonComment createTrialReasonComment(Long trialApplicationId, Long parentId,
														  String content);

	/**
	 * 发送短信验证码
	 * @param mobile	手机号
	 * @param trialId   试用活动id
	 * @author lily
	 * @date  2017-06-01
	 */
	public JSONObject sendVerificationCode(String mobile, Long trialId);

	/**
	 * 提交试用申请
	 * @param trialId
	 * @param name
	 * @param sex
	 * @param mobile
	 * @param verificationCode
	 * @param reason
	 * @return
	 * @author lily
	 * @date  2017-06-01
	 */
	public int createTrialApplication(Long trialId, String name, Integer sex,
                                      String mobile, String verificationCode, String reason);

	/**
	 * 分页获取我的试用申请
	 * @param pageNum		当前页码
	 * @param pageSize		每页显示个数
	 * @param type			查询类型        1:申请中    2申请成功      3申请失败
	 * @return
	 * @author lily
	 * @date  2017-06-05
	 */
	public Page<Map> getMyApplicationByPage(Integer pageNum, Integer pageSize,
                                            Integer type);

	/**
	 * 删除指定的试用申请
	 * @param id       试用申请id
	 * @return
	 * @author lily
	 * @date  2017-06-06
	 */
	public int deleteTrialApplication(Long id);

	/**
	 * 设置收货地址
	 * @param applicationId
	 * @param addressId
	 * @return
	 * @author lily
	 * @date  2017-06-06
	 */
	public int updatTrialApplicationAddress(Long applicationId, Long addressId);

	/**
	 * 获取指定id的试用申请
	 * @param trialApplicationId
	 * @return
	 * @author lily
	 * @date  2017-06-06
	 */
	public DiyTrialApplication getTrialApplicationById(Long trialApplicationId);

	/**
	 * 获取指定用户的试用申请
	 * @param userId
	 * @return
	 * @author lily
	 * @date  2017-10-10
	 */
	public List<DiyTrialApplication> getApplicationListByUserId(
            Long userId);
	
	/**
	 * 批量修改指定申请的userID
	 * @param list
	 * @param userId
	 * @return
	 * @author lily
	 * @date  2017-10-10
	 */
	public int updateApplicationUserId(List<Long> list, Long userId);
	


}
