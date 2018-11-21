package com.x_meteor.kotlindemo.mvp.contract

import com.hazz.kotlinmvp.base.IBaseView
import com.hazz.kotlinmvp.base.IPresenter
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * @author: X_Meteor
 * @description: 视频详情契约类
 * @version: V_1.0.0
 * @date: 2018/11/19 14:16
 * @company:
 * @email: lx802315@163.com
 */
class VideoDetailContract {

    interface View : IBaseView {
        //这里添加视图层方法
        /**
         * 设置视频播放源
         */
        fun setVideo(url: String)

        /**
         * 设置视频信息
         */
        fun setVideoInfo(itemInfo: HandpickBean.Issue.Item)

        /**
         * 设置背景
         */
        fun setBackground(url: String)

        /**
         * 设置最新相关视频
         */
        fun setRecentRelatedVideo(itemList: ArrayList<HandpickBean.Issue.Item>)

        /**
         * 设置错误信息
         */
        fun setErrorMsg(errorMsg: String)
    }

    interface Presenter : IPresenter<View> {
        //这里添加逻辑层方法
        /**
         * 加载视频信息
         */
        fun loadVideoInfo(itemInfo: HandpickBean.Issue.Item)

        /**
         * 请求相关的视频数据
         */
        fun requestRelatedVideo(id: Long)
    }
}