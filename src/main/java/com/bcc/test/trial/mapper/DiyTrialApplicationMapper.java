package com.bcc.test.trial.mapper;

import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.trial.domain.DiyTrialApplicationCus;
import com.bcc.test.trial.domain.DiyTrialReasonComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DiyTrialApplicationMapper 
 * @Description: 试用申请记录
 * @author lily
 * @date 2017-05-23
 */
public interface DiyTrialApplicationMapper {
	
	/**
	 * @Description: 分页查询试用申请用户列表
	 * @param page
	 * @return   
	 * @return List<DiyTrialApplicationCus>  
	 * @throws
	 * @author lily
	 * @date 2017-5-23
	 */
	@SuppressWarnings("rawtypes")
	public List<DiyTrialApplicationCus> getTrialUserByPage(
            @Param("trialId") Long trialId,
            @Param("isPublic") String isPublic,
            @Param("start") Integer start,
            @Param("limit") Integer limit);

	/**
	 * 获取指定试用活动的申请理由总数
	 * @param trialId
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public  int  getTrialReasonCount(Long trialId);

	/**
	 * 分页获取指定试用活动的申请理由
	 * @param trialId	 	试用活动id
	 * @param startItem		查询起始位置
	 * @param pageSize		每个显示个数
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public List<Map<String, Object>> getTrialReasonByPage(@Param("trialId") Long trialId,
                                                          @Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);

	/**
	 * 获取指定申请理由的评论总数
	 * @param trialApplicationId   试用申请id
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public int getTrialReasonCommentCount(Long trialApplicationId);

	/**
	 * 分页获取指定申请理由的评论
	 * @param trialApplicationId	 	试用申请id
	 * @param startItem					查询起始位置
	 * @param pageSize					每个显示个数
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public List<Map<String, Object>> getTrialReasonCommentByPage(@Param("trialApplicationId") Long trialApplicationId,
                                                                 @Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);

	/**
	 * 获取指定id的申请理由评论
	 * @param id    申请理由评论id
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public DiyTrialReasonComment getTrialReasonCommentById(Long id);

	/**
	 * 获取指定id的试用申请
	 * @param id    	申请id
	 * @param trialId   试用活动id
	 * @param mobile    手机号
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public DiyTrialApplication selectTrialApplicationByCondition(Map<String, Object> map);

	/**
	 * 发表/回复申请理由评论
	 * @param diyTrialReasonComment
	 * @return
	 * @author lily
	 * @date 2017-05-31
	 */
	public int insertTrialReasonComment(DiyTrialReasonComment diyTrialReasonComment);

	/**
	 * 保存申请记录
	 * @param application
	 * @return
	 * @author lily
	 * @date 2017-06-01
	 */
	public int insertTrialApplication(DiyTrialApplication application);

	/**
	 * 更新申请记录
	 * @param application
	 * @return
	 * @author lily
	 * @date 2017-06-01
	 */
	public int updateTrialApplication(DiyTrialApplication application);

	/**
	 * 获取不同类型的指定用户试用申请的个数
	 * @param userId    当前用户id
	 * @param type		类型   1：申请中     2：申请成功   3：申请失败
	 * @return
	 * @author lily
	 * @date 2017-06-05
	 */
	public int getMyApplicationCount(@Param("userId") Long userId, @Param("type") Integer type);

	/**
	 * 分页获取不同类型的指定用户的试用申请列表
	 * @param userId		当前用户id
	 * @param type			查询类型   1：申请中     2：申请成功   3：申请失败
	 * @param startItem		起止位置
	 * @param pageSize      每页个数
	 * @return
	 * @author lily
	 * @date 2017-06-05
	 */
	public List<DiyTrialApplicationCus> getMyApplicationByPage(@Param("userId") Long userId, @Param("type") Integer type,
                                                               @Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);
	
	/**
	 * 获取指定用户的试用申请记录
	 * @param userId
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public List<DiyTrialApplication> getApplicationListByUserId(Long userId);

	/**
	 * 批量修改指定申请的userId
	 * @param map
	 * @return
	 * @author lily
	 * @date 2018-10-10
	 */
	public Integer updateApplicationUserId(Map<String, Object> map);
	
}
