package com.x_meteor.kotlindemo.mvp.model

import com.hazz.kotlinmvp.net.RetrofitManager
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * @author: X_Meteor
 * @description: 首页精选 model
 * @version: V_1.0.0
 * @date: 2018/10/30 10:25
 * @company:
 * @email: lx802315@163.com
 */
class HandpickMode {

    /**
     * 获取首页精选数据
     */
    fun requestHomeData(num: Int): Observable<HandpickBean> {
        return RetrofitManager.service.getFirstHomeData(num)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData(url: String): Observable<HandpickBean> {
        return RetrofitManager.service.getMoreHomeData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}