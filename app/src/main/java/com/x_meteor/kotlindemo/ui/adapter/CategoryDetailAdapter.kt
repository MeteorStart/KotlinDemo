package com.x_meteor.kotlindemo.ui.adapter

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.glide.GlideApp
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.mvp.model.bean.CategoryBean
import com.x_meteor.kotlindemo.view.recyclerview.MyViewHolder

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/11/8 17:32
 * @company:
 * @email: lx802315@163.com
 */
class CategoryDetailAdapter(layoutResId: Int, data: MutableList<CategoryBean>?) :
    BaseQuickAdapter<CategoryBean, MyViewHolder>(layoutResId, data) {

    private var textTypeface: Typeface?=null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun convert(helper: MyViewHolder?, item: CategoryBean?) {

        helper?.setText(R.id.tvCategoryName, "#${item?.name}")
        //设置方正兰亭细黑简体
        helper?.getView<TextView>(R.id.tvCategoryName)?.typeface = textTypeface

        helper?.setImagePath(R.id.ivCategory, object :MyViewHolder.HolderImageLoader(item?.bgPicture!!){

            override fun loadImage(iv: ImageView, path: String) {
                GlideApp.with(mContext)
                    .load(path)
                    .placeholder(R.color.color_darker_gray)
                    .transition(DrawableTransitionOptions().crossFade())
                    .thumbnail(0.5f)
                    .into(iv)
            }

        })
    }

}
