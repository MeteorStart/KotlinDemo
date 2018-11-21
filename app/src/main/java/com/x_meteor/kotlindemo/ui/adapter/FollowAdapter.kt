package com.x_meteor.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kotlinmvp.glide.GlideApp
import com.x_meteor.kotlindemo.Constants
import com.x_meteor.kotlindemo.R
import com.x_meteor.kotlindemo.mvp.model.bean.HandpickBean
import com.x_meteor.kotlindemo.ui.activity.VideoDetailActivity
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
                        goToVideoPlayer(mContext as Activity, view,
                            adapter.data.get(position) as HandpickBean.Issue.Item
                        )
                    }
        }

    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HandpickBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair
            )
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}
