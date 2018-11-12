package com.x_meteor.kotlindemo.mvp.model

import com.hazz.kotlinmvp.net.RetrofitManager
import com.x_meteor.kotlindemo.mvp.model.bean.CategoryBean
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/8 17:21
 * @company:
 * @email: lx802315@163.com
 */
class RankModel {
    /**
     * 获取排行榜
     * */
    fun requestRankList(): Observable<ArrayList<CategoryBean>> {

        return RetrofitManager.service.getCategory()
            .compose(SchedulerUtils.ioToMain())
    }
}