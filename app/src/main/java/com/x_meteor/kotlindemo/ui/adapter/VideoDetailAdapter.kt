package com.x_meteor.kotlindemo.ui.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.hazz.kotlinmvp.glide.GlideApp
import com.hazz.kotlinmvp.glide.GlideRoundTransform
import com.hazz.kotlinmvp.view.recyclerview.MultipleType
import com.hazz.kotlinmvp.view.recyclerview.ViewHolder
import com.hazz.kotlinmvp.view.recyclerview.adapter.CommonAdapter
import com.x_meteor.kotlindemo.MyApplication
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.durationFormat
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean

/**
 * Created by xuhao on 2017/11/25.
 * desc: 视频详情
 */

class VideoDetailAdapter(mContext: Context, data: ArrayList<HandpickBean.Issue.Item>) :
        CommonAdapter<HandpickBean.Issue.Item>(mContext, data, object : MultipleType<HandpickBean.Issue.Item> {
            override fun getLayoutId(item: HandpickBean.Issue.Item, position: Int): Int {
                return when {
                    position == 0 ->
                        R.layout.item_video_detail_info

                    data[position].type == "textCard" ->
                        R.layout.item_video_text_card

                    data[position].type == "videoSmallCard" ->
                        R.layout.item_video_small_card
                    else ->
                        throw IllegalAccessException("Api 解析出错了，出现其他类型") as Throwable
                }
            }
        }) {

    private var textTypeface:Typeface?=null

    init {
        textTypeface = Typeface.createFromAsset(MyApplication.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    /**
     * 添加视频的详细信息
     */
    fun addData(item: HandpickBean.Issue.Item) {
        mData.clear()
        notifyDataSetChanged()
        mData.add(item)
        notifyItemInserted(0)

    }

    /**
     * 添加相关推荐等数据 Item
     */
    fun addData(item: ArrayList<HandpickBean.Issue.Item>) {
        mData.addAll(item)
        notifyItemRangeInserted(1, item.size)

    }

    /**
     * Kotlin的函数可以作为参数，写callback的时候，可以不用interface了
     */

    private var mOnItemClickRelatedVideo: ((item: HandpickBean.Issue.Item) -> Unit)? = null


    fun setOnItemDetailClick(mItemRelatedVideo: (item: HandpickBean.Issue.Item) -> Unit) {
        this.mOnItemClickRelatedVideo = mItemRelatedVideo
    }


    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: HandpickBean.Issue.Item, position: Int) {
        when {
            position == 0 -> setVideoDetailInfo(data, holder)

            data.type == "textCard" -> {
                holder.setText(R.id.tv_text_card, data.data?.text!!)
                //设置方正兰亭细黑简体
                holder.getView<TextView>(R.id.tv_text_card).typeface =textTypeface

            }
            data.type == "videoSmallCard" -> {
                with(holder) {
                    setText(R.id.tv_title, data.data?.title!!)
                    setText(R.id.tv_tag, "#${data.data.category} / ${durationFormat(data.data.duration)}")
                    setImagePath(R.id.iv_video_small_card, object : ViewHolder.HolderImageLoader(data.data.cover.detail) {
                            override fun loadImage(iv: ImageView, path: String) {
                                GlideApp.with(mContext)
                                        .load(path)
                                        .optionalTransform(GlideRoundTransform())
                                        .placeholder(R.mipmap.placeholder_banner)
                                        .into(iv)
                            }
                        })
                }
                // 判断onItemClickRelatedVideo 并使用
                holder.itemView.setOnClickListener { mOnItemClickRelatedVideo?.invoke(data) }

            }
            else -> throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    /**
     * 设置视频详情数据
     */
    private fun setVideoDetailInfo(data: HandpickBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.setText(R.id.tv_title, it) }
        //视频简介
        data.data?.description?.let { holder.setText(R.id.expandable_text, it) }
        //标签
        holder.setText(R.id.tv_tag, "#${data.data?.category} / ${durationFormat(data.data?.duration)}")
        //喜欢
        holder.setText(R.id.tv_action_favorites, data.data?.consumption?.collectionCount.toString())
        //分享
        holder.setText(R.id.tv_action_share, data.data?.consumption?.shareCount.toString())
        //评论
        holder.setText(R.id.tv_action_reply, data.data?.consumption?.replyCount.toString())

        if (data.data?.author != null) {
            with(holder) {
                setText(R.id.tv_author_name, data.data.author.name)
                setText(R.id.tv_author_desc, data.data.author.description)
                setImagePath(R.id.iv_avatar, object : ViewHolder.HolderImageLoader(data.data.author.icon) {
                    override fun loadImage(iv: ImageView, path: String) {
                        //加载头像
                        GlideApp.with(mContext)
                                .load(path)
                                .placeholder(R.mipmap.default_avatar).circleCrop()
                                .into(iv)
                    }
                })
            }
        } else {
            holder.setViewVisibility(R.id.layout_author_view, View.GONE)
        }

        with(holder) {
            getView<TextView>(R.id.tv_action_favorites).setOnClickListener {
                Toast.makeText(MyApplication.context, "喜欢", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_share).setOnClickListener {
                Toast.makeText(MyApplication.context, "分享", Toast.LENGTH_SHORT).show()
            }
            getView<TextView>(R.id.tv_action_reply).setOnClickListener {
                Toast.makeText(MyApplication.context, "评论", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
