package com.wkswind.codereader.database;

import net.simonvt.schematic.annotation.DataType;

/**
 * 查询结果缓存至数据库，查询时优先搜索数据库内容
 * Created by Administrator on 2015-11-5.
 */
public interface QueryResultColumn extends Base {
    /**
     * 文件绝对路径
     */
    @DataType(DataType.Type.TEXT)
    String absolutePath = "absolute_path";
    /**
     * 文件名称
     */
    @DataType(DataType.Type.TEXT)
    String fileName = "file_name";
    /**
     * 文件后缀名
     */
    @DataType(DataType.Type.TEXT)
    String fileExtension = "file_extension";
    /**
     * 文件对应doc_type的id
     */
    @DataType(DataType.Type.INTEGER)
    String docTypeId = "doc_type_id";
}
