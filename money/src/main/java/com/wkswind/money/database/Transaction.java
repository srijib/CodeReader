package com.wkswind.money.database;

import net.simonvt.schematic.annotation.DataType;

/**
 * 流水
 * Created by Administrator on 2015/5/6.
 */
public interface Transaction extends Base {
    @DataType(DataType.Type.TEXT)
    String account = "account";
    @DataType(DataType.Type.TEXT)
    String description = "description";
    @DataType(DataType.Type.REAL)
    String timeInMS = "time_in_ms";
    @DataType(DataType.Type.TEXT)
    String details = "details";
    @DataType(DataType.Type.INTEGER)
    String transactionType = "transaction_type";
    @DataType(DataType.Type.INTEGER)
    String transactionStatus = "transaction_status";
}
