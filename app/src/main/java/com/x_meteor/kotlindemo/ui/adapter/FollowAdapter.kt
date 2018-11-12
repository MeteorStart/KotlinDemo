package com.x_meteor.kotlindemo.ui.adapter

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.glide.GlideApp
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.utils.ToastUtils
import com.x_meteor.kotlindemo.view.recyclerview.MyViewHolder

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/2 16:43
 * @company:
 * @email: lx802315@163.com
 */
class FollowAdapter(layoutResId: Int, data: MutableList<HandpickBean.Issue.Item>) :
    BaseQuickAdapter<HandpickBean.Issue.Item, MyViewHolder>(layoutResId, data) {

    override fun convert(helper: MyViewHolder, item: HandpickBean.Issue.Item) {
        val headerData = item.data?.header

        helper.let {
            helper.setImagePath(R.id.imvAvatar, object : MyViewHolder.HolderImageLoader(headerData?.icon!!) {
                override fun loadImage(iv: ImageView, path: String) {
                    GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.mipmap.default_avatar).circleCrop()
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(helper.getView(R.id.imvAvatar))
                }
            })

            helper.setText(R.id.tvTitle, headerData.title)
            helper.setText(R.id.tvDesc, headerData.description)

            val recycle = helper.getView<RecyclerView>(R.id.followRecyclerView)

            recycle.layoutManager = LinearLayoutManager(mContext as Activity, LinearLayoutManager.HORIZONTAL, false)
            recycle.adapter = FollowHorizontalAdapter(R.layout.item_follow_horizontal, item.data.itemList)

            (recycle.adapter as FollowHorizontalAdapter).onItemChildClickListener =
                    BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
                        ToastUtils.showToast("点击了")
                    }
        }

    }

}
