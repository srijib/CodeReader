package com.wkswind.codereader.database;

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
}
