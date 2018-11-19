package com.x_meteor.kotlindemo.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.x_meteor.kotlindemo.mvp.model.bean.TabInfoBean

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/14 15:33
 * @company:
 * @email: lx802315@163.com
 */
class HotTabContract {

    interface HotTabView : IBaseView {

        /**
         * 设置TabInfo
         * */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg: String, errorCode: Int)
    }

    interface HotTabPresenter:IPresenter<HotTabView>{
        /**
         * 获取TabInfo
         * */
        fun getTabInfo()
    }
}