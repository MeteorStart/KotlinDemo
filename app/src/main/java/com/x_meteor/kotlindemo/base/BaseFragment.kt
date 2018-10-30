package com.x_meteor.kotlindemo.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.classic.common.MultipleStatusView
import com.hjq.permissions.OnPermission
import com.hjq.permissions.XXPermissions
import com.x_meteor.kotlindemo.MyApplication

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/29 14:23
 * @company:
 * @email: lx802315@163.com
 */
abstract class BaseFragment : Fragment(){
    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false
    /**
     * 数据是否加载过了
     */
    private var hasLoadData = false
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(),null)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        lazyLoadDataIfPrepared()
        //多种状态切换的view 重试点击事件
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }


    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getLayoutId():Int

    /**
     * 初始化 ViewI
     */
    abstract fun initView()

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    override fun onDestroy() {
        super.onDestroy()
        activity?.let { MyApplication.getRefWatcher(it)?.watch(activity) }
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
}