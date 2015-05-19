package com.wkswind.money.database.schema;

import net.simonvt.schematic.annotation.DataType;

/**
 * 流水类型
 * Created by Administrator on 2015/5/6.
 */
public interface TransactionTypeColumns extends BaseColumns {
    @DataType(DataType.Type.INTEGER)
    String type = "transaction_type";//分类
    @DataType(DataType.Type.TEXT)
    String name = "transaction_name";//名称
    @DataType(DataType.Type.INTEGER)
    String periodicity = "transaction_periodicity";//周期

    public static final int INCOMING = 0; //收入，需要手动录入
    public static final int EXPENSE = 1; //支出，需要手动录入
    public static final int INCOMING_PERIODICITY = 2;//周期性收入，自动添加
    public static final int EXPANSE_PERIODICITY = 3;//周期性支出，自动添加

    public static final int DAILY = 0; //每天
    public static final int WEEKLY = 1; //每周
    public static final int MONTHLY = 2; //每月
    public static final int YEARLY = 3;//每年
}
