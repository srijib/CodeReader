package com.wkswind.money.database;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by Administrator on 2015/3/31.
 */
public interface Base {
    @AutoIncrement
    @PrimaryKey
    @DataType(DataType.Type.INTEGER)
    String ID = "_id";
    @DataType(DataType.Type.INTEGER)
    String dataStatus = "data_status";
    @DataType(DataType.Type.INTEGER)
    String status = "status";//
    public static final int DATA_STATUS_DEFAULT = 0;//正常数据
    public static final int DATA_STATUS_DIRTY = 1;//被更改过的数据

    public static final int STATUS_NORMAL = 0;//状态正常
    public static final int STATUS_DELETED = 65536;//被删除的记录
}
