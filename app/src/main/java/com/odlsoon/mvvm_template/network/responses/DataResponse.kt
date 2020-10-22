package com.odlsoon.mvvm_template.network.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataResponse {
    @SerializedName("items") @Expose var items: List<Item>? = null
    @SerializedName("has_more") @Expose var has_more: Boolean = false
    @SerializedName("quota_max") @Expose var quota_max: Int = 0
    @SerializedName("quota_remaining") @Expose var quota_remaining: Int = 0

    class Item (
        @SerializedName("owner") @Expose var owner: Owner? = null,
        @SerializedName("is_accepted") @Expose var accepted: Boolean? = false,
        @SerializedName("score") @Expose var score: Int? = 0,
        @SerializedName("last_activity_date") @Expose var last_activity_date: Long? = 0,
        @SerializedName("creation_date") @Expose var creation_date: Long? = 0,
        @SerializedName("answer_id") @Expose var answer_id: Long? = 0,
        @SerializedName("question_id") @Expose var question_id: Long? = 0
    )

    class Owner (
        @SerializedName("user_id") @Expose var user_id: Long = 0,
        @SerializedName("reputation") @Expose var reputation: Int = 0,
        @SerializedName("user_type") @Expose var user_type: String? = null,
        @SerializedName("profile_image") @Expose var profile_image: String? = null,
        @SerializedName("display_name") @Expose var display_name: String? = null,
        @SerializedName("link") @Expose var link: String? = null
    )
}