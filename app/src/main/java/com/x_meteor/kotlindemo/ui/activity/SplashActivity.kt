package com.x_meteor.kotlindemo.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.base.BaseActivity
import com.x_meteor.kotlindemo.utils.AppUtils
import com.x_meteor.kotlindemo.utils.StatusBarUtils
import com.x_meteor.kotlindemo.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    /**
     * 权限申请列表
     */
    var permissions = arrayOf(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)

    private var textTypeface: Typeface? = null

    private var descTypeFace: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    private lateinit var onPermissionListener: OnPermission

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/Lobster-1.4.otf")
        descTypeFace = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initView() {

//        StatusBarUtils.transparencyBar(this)
//        StatusBarUtils.StatusBarLightMode(this)

        tv_app_name.typeface = textTypeface
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "v${AppUtils.getVerName(MyApplication.context)}"

        //渐变展示启动屏
        alphaAnimation = AlphaAnimation(0.3f, 1.0f)
        alphaAnimation?.duration = 2000
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(arg0: Animation) {
                redirectToMain()
            }

            override fun onAnimationRepeat(animation: Animation) {}

            override fun onAnimationStart(animation: Animation) {}

        })
    }

    override fun initData() {
        //获取读写权限
        if (!isHasPermission(this, permissions)) {
            initPermissions(this, permissions, onPermissionListener)
        } else {
            if (alphaAnimation != null) {
                iv_web_icon.startAnimation(alphaAnimation)
            }
        }
    }

    override fun initListener() {

        onPermissionListener = object : OnPermission {

            override fun hasPermission(granted: List<String>, isAll: Boolean) {
                if (isAll) {
                    if (alphaAnimation != null) {
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                    ToastUtils.showToast(this@SplashActivity, "获取权限成功！")
                } else {
                    ToastUtils.showToast(this@SplashActivity, "获取权限成功，部分权限未正常授予！")
                }
            }

            override fun noPermission(denied: List<String>, quick: Boolean) {
                if (quick) {
                    ToastUtils.showToast(this@SplashActivity, "被永久拒绝授权，请手动授予权限！")
                    //如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.gotoPermissionSettings(this@SplashActivity)
                } else {
                    ToastUtils.showToast(this@SplashActivity, "获取权限失败！")
                }
            }
        }
    }

    override fun getNetData() {

    }

    fun redirectToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
