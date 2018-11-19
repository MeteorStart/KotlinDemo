package com.x_meteor.kotlindemo.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/16 14:02
 * @company:
 * @email: lx802315@163.com
 */
class RankContract {

    interface RankView : IBaseView {
        /**
         * 设置排行榜数据
         * */
        fun setRankList(itemList: ArrayList<HandpickBean.Issue.Item>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface RankPresenter : IPresenter<RankView> {
        /**
         * 获取排行榜数据
         * */
        fun requestRankList(apiUrl: String)
    }
}