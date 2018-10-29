package com.x_meteor.kotlindemo.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/26 15:11
 * @company:
 * @email: lx802315@163.com
 */
class GsonUtils {

    init {
        createGson()
    }
    companion object {

        private var mGsonUtils: GsonUtils? = null
        private var gson: Gson? = null

        fun getInstance(): Gson? {
            synchronized(GsonUtils::class.java) {
                if (gson == null) {
                    mGsonUtils = GsonUtils()
                }
            }
            return gson
        }

        fun createGson(): Gson {
            gson = GsonBuilder()
                .create()
            return gson!!
        }

        fun isGoodJson(msg: String): Boolean {
            return msg.startsWith("{") && msg.endsWith("}")
                    && msg.contains(":") && msg.contains(",")
                    || msg.startsWith("[") && msg.endsWith("]")
                    && msg.contains(":") && msg.contains(",")
        }
    }

}