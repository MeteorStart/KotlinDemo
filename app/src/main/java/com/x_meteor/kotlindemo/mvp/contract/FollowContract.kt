package com.x_meteor.kotlindemo.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/1 16:16
 * @company:
 * @email: lx802315@163.com
 */
interface FollowContract {

    interface FollowView : IBaseView {

        /**
         * @description: 设置关注信息
         * @date: 2018/11/1 16:19
         * @author: Meteor
         * @email: lx802315@163.com
         */
        fun setFollowInfo(issue: HandpickBean.Issue)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface FollowPresenter : IPresenter<FollowView> {

        /**
         * @description: 获取数据
         * @date: 2018/11/1 16:19
         * @author: Meteor
         * @email: lx802315@163.com
         */
        fun requestFollowList()

        /**
         * @description: 加载更多
         * @date: 2018/11/1 16:19
         * @author: Meteor
         * @email: lx802315@163.com
         */
        fun loadMoreData()
    }

}