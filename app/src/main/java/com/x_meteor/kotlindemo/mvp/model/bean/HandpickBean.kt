package com.x_meteor.kotlindemo.mvp.model.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import java.io.Serializable

/**
 * @author: X_Meteor
 * @description: 类描述
 * @version: V_1.0.0
 * @date: 2018/10/29 17:30
 * @company:
 * @email: lx802315@163.com
 */
data class HandpickBean(
    val issueList: ArrayList<Issue>,
    val bannerList: ArrayList<Issue>,
    val hander: Issue,
    val nextPageUrl: String,
    val nextPublishTime: Long,
    val newestIssueType: String,
    val dialog: Any
) {

//    "issueList": [],
//    "nextPageUrl": "http://baobab.kaiyanapp.com/api/v2/feed?date=1503104400000&num=1",
//    "nextPublishTime": 1503277200000,
//    "newestIssueType": "morning",
//    "dialog": null

    data class Issue(
        val releaseTime: Long,
        val type: String,
        val date: Long,
        val total: Int,
        val publishTime: Long,
        val itemList: ArrayList<Item>,
        var count: Int,
        val nextPageUrl: String
    ) {

        data class Item(var type: String, val data: Data?, val tag: String) : Serializable, MultiItemEntity {

            companion object {
                const val ITEM_TYPE_BANNER = 1    //Banner 类型
                const val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
                const val ITEM_TYPE_CONTENT = 3    //item
            }

            override fun getItemType(): Int {
                return when {
                    data?.dataType.equals("banner") -> ITEM_TYPE_BANNER
                    data?.dataType.equals("textHeader") -> ITEM_TYPE_TEXT_HEADER
                    else ->
                        ITEM_TYPE_CONTENT
                }
            }

            data class Data(
                val dataType: String,
                val text: String,
                val videoTitle: String,
                val id: Long,
                val title: String,
                val slogan: String?,
                val description: String,
                val actionUrl: String,
                val provider: Provider,
                val category: String,
                val parentReply: ParentReply,
                val author: Author,
                val cover: Cover,
                val likeCount: Int,
                val playUrl: String,
                val thumbPlayUrl: String,
                val duration: Long,
                val message: String,
                val createTime: Long,
                val webUrl: WebUrl,
                val library: String,
                val user: User,
                val playInfo: ArrayList<PlayInfo>?,
                val consumption: Consumption,
                val campaign: Any,
                val waterMarks: Any,
                val adTrack: Any,
                val tags: ArrayList<Tag>,
                val type: String,
                val titlePgc: Any,
                val descriptionPgc: Any,
                val remark: String,
                val idx: Int,
                val shareAdTrack: Any,
                val favoriteAdTrack: Any,
                val webAdTrack: Any,
                val date: Long,
                val promotion: Any,
                val label: Any,
                val labelList: Any,
                val descriptionEditor: String,
                val collected: Boolean,
                val played: Boolean,
                val subtitles: Any,
                val lastViewTime: Any,
                val playlists: Any,
                val header: Header,
                val itemList: ArrayList<HandpickBean.Issue.Item>
            ) : Serializable {
                data class Tag(val id: Int, val name: String, val actionUrl: String, val adTrack: Any) : Serializable

                data class Author(val icon: String, val name: String, val description: String) : Serializable

                data class Provider(val name: String, val alias: String, val icon: String) : Serializable

                data class Cover(
                    val feed: String, val detail: String,
                    val blurred: String, val sharing: String, val homepage: String
                ) : Serializable

                data class WebUrl(val raw: String, val forWeibo: String) : Serializable

                data class PlayInfo(val name: String, val url: String, val type: String, val urlList: ArrayList<Url>) :
                    Serializable

                data class Consumption(val collectionCount: Int, val shareCount: Int, val replyCount: Int) :
                    Serializable

                data class User(
                    val uid: Long,
                    val nickname: String,
                    val avatar: String,
                    val userType: String,
                    val ifPgc: Boolean
                ) :
                    Serializable

                data class ParentReply(val user: User, val message: String) : Serializable

                data class Url(val size: Long) : Serializable

                data class Header(
                    val id: Int,
                    val icon: String,
                    val iconType: String,
                    val description: String,
                    val title: String,
                    val font: String,
                    val cover: String,
                    val label: Label,
                    val actionUrl: String,
                    val subtitle: String,
                    val labelList: ArrayList<Label>
                ) :
                    Serializable {
                    data class Label(val text: String, val card: String, val detial: Any, val actionUrl: Any)
                }

            }
        }


    }


}

