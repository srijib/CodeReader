package com.wkswind.codereader.database;

import net.simonvt.schematic.annotation.DataType;

/**
 * 用于Drawer侧边栏的代码类型，包含名字和文件后缀名(用,分割)
 * Created by Administrator on 2015-11-5.
 */
public interface DocTypeColumn extends Base{
    /**
     * 类型名称
     */
    @DataType(DataType.Type.TEXT)
    String docType = "doc_type";
    /**
     * 对应的后缀名
     */
    @DataType(DataType.Type.TEXT)
    String fileExtension = "file_extensions";
}
