package com.bcc.test.trialBack.mapper;

import com.bcc.test.trial.domain.DiyTrialApplication;
import com.bcc.test.trial.domain.DiyTrialApplicationCus;
import com.bcc.test.trialBack.domain.DiyTrialApplicationSupper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName: DiyTrialUserBackMapper 
 * @Description: 试用申请用户信息管理
 * @author lily
 * @date 2017-05-09
 */
public interface DiyTrialApplicationBackMapper {

	/**
	 * @Description: 条件查询申请用户总行数
	 * @param  trialId     试用活动id
	 * @param  isBallot    是否中签 Y/N
	 * @return int  
	 * @throws
	 * @author lily
	 * @date 2017-5-9
	 */
	public int getTrialUserCount(@Param("trialId") Long trialId, @Param("isBallot") String isBallot);

	/**
	 * @Description: 分页查询指定试用活动的所有申请用户列表
	 * @param  trialId     试用活动id
	 * @param  startItem   开始位置
	 * @param  pageSize    每页个数
	 * @return List<Map<String,Object>>
	 * @throws
	 * @author lily
	 * @date 2017-5-9
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String,Object>> getTrialUserByPage(@Param("trialId") Long trialId, @Param("startItem") Integer startItem,
                                                       @Param("pageSize") Integer pageSize);

	/**
	 * @Description: 分页查询指定试用活动的中签用户列表
	 * @param  trialId     试用活动id
	 * @param  isBallot    中签标识
	 * @param  startItem   开始位置
	 * @param  pageSize    每页个数
	 * @return List<DiyTrialUserSupper>
	 * @throws
	 * @author lily
	 * @date 2017-5-9
	 */
	@SuppressWarnings("rawtypes")
	public List<DiyTrialApplicationSupper> getTrialBallotUserByPage(@Param("trialId") Long trialId, @Param("isBallot") String isBallot,
																	@Param("startItem") Integer startItem, @Param("pageSize") Integer pageSize);

	/**
	 * 获取指定的试用申请详细信息
	 * @param id  试用申请主键
	 * @return
	 * @author lily
	 * @date 2015-05-05
	 */
	public DiyTrialApplicationSupper selectDiyTrialUserById(Long id);

	/**
	 * 获取指定的试用申请信息
	 * @param id  试用申请主键
	 * @return
	 * @author lily
	 * @date 2015-05-05
	 */
	public DiyTrialApplication getDiyTrialUserById(Long id);


	/**
	 * 修改试用活动申请信息
	 * @param diyTrialUser
	 * @return int  数据库受影响行数
	 * @author lily
	 * @date  2017-05-10
	 */
	public int updateDiyTrialUser(DiyTrialApplication diyTrialUser);

	/**
	 * 删除试用活动信息
	 * @param id   试用活动id
	 * @return
	 * @author lily
	 * @date  2017-05-04
	 */
	public int deleteDiyTrial(Long id);

	/**
	 * 删除试用申请数据
	 * @param id		试用申请主键id
	 * @return
	 * @author lily
	 * @date 2015-05-11
	 */
	public int deleteDiyTrialUser(@Param("id") Long id);

	/**
	 * 查询指定试用活动的已公示用户列表
	 * @param trialId   试用活动id
	 * @return
	 * @author lily
	 * @date 2015-05-11
	 */
	public List<DiyTrialApplicationSupper> selectPublicTrialUser(Long trialId);

	/**
	 * 获取指定的id的用户申请列表
	 * @param idsList   申请id集合
	 * @return
	 * @author lily
	 * @date 2017-05-15
	 */
	public List<DiyTrialApplication> selectTrialUserByIds(@Param("idsList") List<Long> idsList);
	
	
	/**
	 * 获取所有无试用报告的已支付押金的申请
	 * @return
	 * @author lily
	 * @date 2017-06-19
	 */
	public List<DiyTrialApplicationCus> getNoReportDepositApplication();



}
