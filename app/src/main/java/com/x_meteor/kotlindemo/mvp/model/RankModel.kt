package com.x_meteor.kotlindemo.mvp.model

import com.hazz.kotlinmvp.net.RetrofitManager
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/16 14:06
 * @company:
 * @email: lx802315@163.com
 */
class RankModel {
    /**
     * 获取排行榜数据
     * */
    fun requestRankList(apiUrl: String): Observable<HandpickBean.Issue> {
        return RetrofitManager.service.getIssueData(apiUrl)
            .compose(SchedulerUtils.ioToMain())
    }
}