package com.wkswind.password.databases.migrations;

import java.util.ArrayList;

/**
 * Created by 南风不竞 on 2015-06-22.
 */
public class PasswordTypeConsts {
    public static final ArrayList<String> NAMES;
    public static final ArrayList<String> REMARKS;

    static {
        NAMES = new ArrayList<>();
        REMARKS = new ArrayList<>();
        NAMES.add("全部");
        NAMES.add("网站");
        NAMES.add("银行");
        NAMES.add("游戏");
        NAMES.add("学习");
        NAMES.add("其它");

        REMARKS.add("");
        REMARKS.add("");
        REMARKS.add("");
        REMARKS.add("");
        REMARKS.add("");
        REMARKS.add("");
    }
}
