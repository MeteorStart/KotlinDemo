package com.x_meteor.kotlindemo.ui.adapter

import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.glide.GlideApp
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.durationFormat
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.view.recyclerview.MyViewHolder

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/7 15:19
 * @company:
 * @email: lx802315@163.com
 */
class FollowHorizontalAdapter(layoutResId: Int, data: MutableList<HandpickBean.Issue.Item>) :
    BaseQuickAdapter<HandpickBean.Issue.Item, MyViewHolder>(layoutResId, data) {

    override fun convert(helper: MyViewHolder, item: HandpickBean.Issue.Item) {
        val horizontalItemData = item.data

        helper.setImagePath(R.id.imvCoverFeed,
            object : MyViewHolder.HolderImageLoader(horizontalItemData?.cover?.feed!!) {
                override fun loadImage(iv: ImageView, path: String) {
                    // 加载封页图
                    GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.mipmap.placeholder_banner)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(helper.getView(R.id.imvCoverFeed))
                }
            })
        //横向 RecyclerView 封页图下面标题
        helper.setText(R.id.tvTitle, horizontalItemData?.title)

        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)

        //标签
        with(helper) {
            if (horizontalItemData.tags.size > 0) {
                setText(R.id.tvTag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            }else{
                setText(R.id.tvTag,"#$timeFormat")
            }
            addOnClickListener(R.id.imvCoverFeed)
        }
    }
}