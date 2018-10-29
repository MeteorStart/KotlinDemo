package com.x_meteor.kotlindemo.utils

import com.orhanobut.logger.Logger

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/26 15:00
 * @company:
 * @email: lx802315@163.com
 */
class LogUtils {
     companion object {
        var isDebug: Boolean = true
        fun print(msg: String) {
            if (isDebug) {
                if (GsonUtils.isGoodJson(msg)) {
                    Logger.json(msg)
                } else {
                    Logger.i(msg)
                }
            }
        }

        fun print(tag: String, msg: String) {
            if (isDebug) {
                if (GsonUtils.isGoodJson(msg)) {
                    Logger.t(tag)
                    Logger.json(msg)
                } else {
                    Logger.t(tag)
                    Logger.i(msg)
                }
            }
        }
    }
}