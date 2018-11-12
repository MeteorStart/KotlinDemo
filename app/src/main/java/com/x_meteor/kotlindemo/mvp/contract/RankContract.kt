package com.x_meteor.kotlindemo.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.x_meteor.kotlindemo.mvp.model.bean.CategoryBean
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/8 17:17
 * @company:
 * @email: lx802315@163.com
 */
interface RankContract {

    interface RankView : IBaseView {

        /**
         * 设置排行榜的数据
         * */

        fun setRankList(itemList:ArrayList<CategoryBean>)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface RankPresenter : IPresenter<RankView> {
        /**
         * 获取 TabInfo
         * */
        fun requestRankList()
    }
}