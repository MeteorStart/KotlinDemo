package com.x_meteor.kotlindemo.ui.adapter

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.glide.GlideApp
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.durationFormat
import com.x_meteor.kotlindemo.mvp.model.bean.CategoryBean
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.view.recyclerview.MyViewHolder

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/16 14:15
 * @company:
 * @email: lx802315@163.com
 */
class RankAdapter(layoutResId: Int, data: MutableList<HandpickBean.Issue.Item>?) :
    BaseQuickAdapter<HandpickBean.Issue.Item, MyViewHolder>(layoutResId, data) {

    private var textTypeface: Typeface? = null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun convert(helper: MyViewHolder?, item: HandpickBean.Issue.Item?) {

        var itemData = item?.data

        helper?.let {
            helper.setText(R.id.tvTitle, itemData?.title)
            //设置方正兰亭细黑简体
            helper.getView<TextView>(R.id.tvTag)?.typeface = textTypeface

            helper.setImagePath(R.id.ivImage, object : MyViewHolder.HolderImageLoader(itemData?.cover?.feed!!) {

                override fun loadImage(iv: ImageView, path: String) {
                    GlideApp.with(mContext)
                        .load(path)
                        .placeholder(R.color.color_darker_gray)
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv)
                }

            })

            // 格式化时间
            val timeFormat = durationFormat(itemData?.duration)

            helper.setText(R.id.tvTag, "#${itemData?.category}/$timeFormat")
        }

    }


}