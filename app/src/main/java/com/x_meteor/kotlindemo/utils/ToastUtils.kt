package com.x_meteor.kotlindemo.utils

import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.MyApplication.Companion.context
import com.x_meteor.kotlindemo.R

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/26 16:39
 * @company:
 * @email: lx802315@163.com
 */
class ToastUtils() {
    private var toast: Toast? = null
    private var toastView: LinearLayout? = null

    /**
     * 完全自定义布局Toast
     *
     * @param context
     * @param view
     */
    constructor(context: Context, view: View, duration: Int) : this() {
        toast = Toast(MyApplication.context)
        toast!!.view = view
        toast!!.duration = duration
    }

    init {

    }

    /**
     * 向Toast中添加自定义view
     *
     * @param view
     * @param postion
     * @return
     */
    fun addView(view: View, postion: Int): ToastUtils {
        toastView = toast!!.view as LinearLayout
        toastView!!.addView(view, postion)

        return this
    }

    fun setGravity(gravity: Int, xOffset: Int, yOffset: Int): ToastUtils {
        toast!!.setGravity(gravity, xOffset, yOffset)
        return this
    }

    /**
     * 设置Toast字体及背景颜色
     *
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    fun setToastColor(messageColor: Int, backgroundColor: Int): ToastUtils {
        val view = toast!!.view
        if (view != null) {
            val message = view.findViewById<View>(android.R.id.message) as TextView
            view.setBackgroundColor(backgroundColor)
            message.setBackgroundColor(backgroundColor)
            message.setTextColor(messageColor)
        }
        return this
    }

    /**
     * 设置Toast字体及背景
     *
     * @param messageColor
     * @param background
     * @return
     */
    fun setToastBackground(messageColor: Int, background: Int): ToastUtils {
        val view = toast!!.view
        if (view != null) {
            val message = view.findViewById<View>(android.R.id.message) as TextView
            view.setBackgroundResource(background)
            message.setBackgroundResource(background)
            message.setTextColor(messageColor)
        }
        return this
    }

    /**
     * 短时间显示Toast
     */
    fun Short(context: Context, message: CharSequence): ToastUtils {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        return this
    }

    /**
     * 短时间显示Toast
     */
    fun Short(context: Context, message: Int): ToastUtils {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_SHORT
        }
        return this
    }

    /**
     * 长时间显示Toast
     */
    fun Long(context: Context, message: CharSequence): ToastUtils {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_LONG
        }
        return this
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    fun Long(context: Context, message: Int): ToastUtils {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = Toast.LENGTH_LONG
        }
        return this
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    fun Indefinite(context: Context, message: CharSequence, duration: Int): ToastUtils {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, duration)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = duration
        }
        return this
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    fun Indefinite(context: Context, message: Int, duration: Int): ToastUtils {
        if (toast == null || toastView != null && toastView!!.childCount > 1) {
            toast = Toast.makeText(context, message, duration)
            toastView = null
        } else {
            toast!!.setText(message)
            toast!!.duration = duration
        }
        return this
    }

    /**
     * 显示Toast
     *
     * @return
     */
    fun show(): ToastUtils {
        toast!!.show()
        return this
    }

    /**
     * 获取Toast
     *
     * @return
     */
    fun getToast(): Toast? {
        return toast
    }

    companion object {

        fun showToast(context: Context, content: String) {
            if (Looper.myLooper() != null) {
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
            } else {
                Looper.prepare()
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
                Looper.loop()
            }


        }

        fun showToast(context: Context, content: String, length: Int) {
            if (Looper.myLooper() != null) {
                Toast.makeText(context, content, length).show()
            } else {
                Looper.prepare()
                Toast.makeText(context, content, length).show()
                Looper.loop()
            }

        }

        fun showCenterToast(context: Context, message: String) {
            val toastUtils = ToastUtils()
            toastUtils.Short(context, message)
                .setToastBackground(Color.WHITE, R.drawable.toast_radius)
                .setGravity(Gravity.CENTER, 0, 0).show()
        }
    }

}