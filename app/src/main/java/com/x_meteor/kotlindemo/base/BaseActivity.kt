package com.x_meteor.kotlindemo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.classic.common.MultipleStatusView
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.utils.LogUtils
import com.x_meteor.kotlindemo.utils.ToastUtils
import java.util.*

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/26 15:26
 * @company:
 * @email: lx802315@163.com
 */
abstract class BaseActivity : AppCompatActivity() {

    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    /**
     * 声明一个活动管理栈
     */
    private val activities = Stack<Activity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //添加活动到活动栈中
        activities.add(this)

        //打印当前类名
        val msg = this.localClassName
        LogUtils.print(msg)

        setContentView(layoutId())
        initView()
        initListener()
        initData()

        initMultipleStatusView()
    }

    /**
     * 初始化根布局文件
     */
    abstract fun layoutId(): Int

    /**
     * 初始化控件
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化接口
     */
    abstract fun initListener()

    /**
     * 开始请求网络数据
     */
    abstract fun getNetData()

    private fun initMultipleStatusView() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        getNetData()
    }

    /**
     * 退出所有活动并且退出当前应用
     */
    fun exitApplication() {
        for (activity in activities) {
            activity?.finish()
        }
        System.exit(0)
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * 退出所有活动并且退出所有活动
     */
    fun clearActicity() {
        for (activity in activities) {
            activity?.finish()
        }
    }

    /**
     * 打卡软键盘
     */
    fun openKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    /**
     * @description: 初始化权限管理
     * @date: 2018/9/29 16:41
     * @author: Meteor
     * @email: lx802315@163.com
     */
    protected fun initPermissions(activity: Activity, permissions: Array<String>, onPermissionListener: OnPermission) {
        XXPermissions.with(activity)
            //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
            .permission(permissions)
            //                .permission(Permission.Group.STORAGE, Permission.Group.CALENDAR) //不指定权限则自动获取清单中的危险权限
            .request(onPermissionListener)
    }

    /**
     * @name: 判断是否具有此权限
     * @description: 方法描述
     * @date: 2018/9/17 16:49
     * @company:
     * @author: Meteor
     */
    protected fun isHasPermission(context: Context, permissions: Array<String>): Boolean {
        return if (XXPermissions.isHasPermission(context, permissions)) {
//            ToastUtils.showToast(this, "已经获取到权限，不需要再次申请了")
            true
        } else {
//            ToastUtils.showToast(this, "还没有获取到权限或者部分权限未授予")
            false
        }
    }

    /**
     * @description: 跳转到权限设置页面
     * @date: 2018/9/29 16:42
     * @author: Meteor
     * @email: lx802315@163.com
     */
    protected fun gotoPermissionSettings(context: Context) {
        XXPermissions.gotoPermissionSettings(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.getRefWatcher(this)?.watch(this)
    }
}