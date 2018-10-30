package com.x_meteor.kotlindemo.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/29 17:28
 * @company:
 * @email: lx802315@163.com
 */
interface HandpickContract {
    interface HandpickView:IBaseView{
        /**
         * 设置第一次请求的数据
         */
        fun setHomeData(handpickBean: HandpickBean)

        /**
         * 设置加载更多的数据
         */
        fun setMoreData(itemList:ArrayList<HandpickBean.Issue.Item>)

        /**
         * 显示错误信息
         */
        fun showError(msg: String,errorCode:Int)
    }
}