package com.wkswind.codereader.database;

import net.simonvt.schematic.annotation.DataType;

/**
 * Created by Administrator on 2015/3/31.
 */
public interface HistorysColumn extends Base {
    @DataType(DataType.Type.INTEGER)
    String lastReadTime = "last_read_time";
    @DataType(DataType.Type.TEXT)
    String fileName = "file_name";
}
