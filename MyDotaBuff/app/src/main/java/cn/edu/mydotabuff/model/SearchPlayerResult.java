package cn.edu.mydotabuff.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sadhu on 2017/6/27.
 * 描述: 搜索玩家结果
 */
public class SearchPlayerResult {
    /**
     * account_id : 0 玩家id
     * avatarfull : string 头像
     * personaname : string 昵称
     * similarity : 0 相似度
     */
    @SerializedName("account_id")
    public long accountId;
    public String avatarfull;
    @SerializedName("personaname")
    public String personaName;
    public float similarity;
}
