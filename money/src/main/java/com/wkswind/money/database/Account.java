package com.wkswind.money.database;

import net.simonvt.schematic.annotation.DataType;

/**
 * 账号
 * Created by Administrator on 2015/5/7.
 */
public interface Account extends Base {
    @DataType(DataType.Type.TEXT)
    String name = "name";
    @DataType(DataType.Type.TEXT)
    String avatarUrl = "avatar_url";
    @DataType(DataType.Type.TEXT)
    String coverUrl = "cover_url";
    @DataType(DataType.Type.INTEGER)
    String accountType = "account_type";

    public static final int TYPE_QQ = 0; //QQ
    public static final int TYPE_WECHAT = 1;//微信
    public static final int TYPE_SINA_WEIBO = 2; //新浪微博


}
