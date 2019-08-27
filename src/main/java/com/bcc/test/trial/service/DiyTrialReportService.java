package com.bcc.test.trial.service;

import com.bcc.test.trial.domain.DiyTrialReport;
import com.bcc.test.trial.domain.DiyTrialReportCus;
import com.bcc.test.common.utils.Page;

import java.util.List;
import java.util.Map;

/**
 * 试用报告
 * @author lily
 * @date 2017-05-24
 */
public interface DiyTrailReportService {
    /**
     * 分页查询试用活动列表
     * @param pageNum     	当前页码
     * @param pageSize		每页显示个数
     * @param trialId		试用活动id
     * @param isRecommend	推荐标识  Y/N
     * @return
     * @author lily
     * @date 2017-05-23
     */
    public Page<Map> getTrialReportByPage(Integer pageNum, Integer pageSize, Long trialId, String isRecommend);

    /**
     * 获取指定的试用报告详情
     * @param reportId
     * @return
     * @author lily
     * @date 2017-05-25
     */
    public DiyTrialReportCus getTrialReportById(Long reportId);

    /**
     * 试用报告点赞/点踩
     * @Description:
     * @param: @param reportId 点赞/点踩试用报告id
     * @param: @param type     操作类型  1：点赞   2：点踩
     * @param: @return
     * @return: int
     * @throws
     * @author: lily
     * @date: 2017-05-26
     */
    public int addLike(Long reportId, Integer type);

    /**
     * 新增试用报告
     * @param report
     * @return
     * @author: lily
     * @date: 2017-06-07
     */
    public int addTrialReport(String report);

    /**
     * 获取指定的试用报告基本信息
     * @param reportId
     * @return
     * @author: lily
     * @date: 2017-06-07
     */
    public DiyTrialReport selectTrialReportById(Long reportId);

    /**
     * 修改试用报告
     * @param report
     * @return
     * @author: lily
     * @date: 2017-06-07
     */
    public int updateTrialReport(String report);

    /**
     * 获取指定用户的试用报告列表
     * @param userId  用户标识
     * @return
     * @author lily
     * @date 2018-10-10
     */
    public List<DiyTrialReport> getReportListByUserId(Long userId);

    /**
     * 批量更新指定试用报告的userId
     * @param list   指定商品id集合
     * @param userId  用户标识
     * @return
     * @author lily
     * @date 2018-10-10
     */
    public int updateReportUserId(List<Long> list, Long userId);


}

