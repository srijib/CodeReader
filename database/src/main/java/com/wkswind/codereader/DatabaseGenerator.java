package com.wkswind.codereader;

import org.apache.commons.io.FileUtils;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by Administrator on 2015-11-16.
 */
public class DatabaseGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(17,"com.wkswind.codereader.database");
        Entity docType = addDocType(schema);
        assert docType != null;
        Entity result = addResult(docType, schema);
        assert result != null;
        addHistory(schema, result);
        addStars(schema, result);
        String path = "database/output";
        File file = new File(path);
        if(file.exists()){
            FileUtils.cleanDirectory(file);
        }else{
            file.mkdir();
        }
//        FileUtils.cleanDirectory(file);
//        System.out.println(file.getAbsolutePath());
        new DaoGenerator().generateAll(schema, file.getAbsolutePath());
    }

    private static Entity addStars(Schema schema, Entity result){
        Entity stars = schema.addEntity("Star");
        stars.addBooleanProperty("star");
        stars.addDateProperty("lastAccessTime");
        Property property = stars.addLongProperty("resultId").getProperty();
        stars.addToOne(result,property);
        addCommonColumns(stars);
        return stars;
    }

    private static Entity addHistory(Schema schema, Entity result) {
        Entity history = schema.addEntity("History");
        history.addDateProperty("lastReadTime");
        Property property = history.addLongProperty("resultId").getProperty();
        history.addToOne(result,property);
        addCommonColumns(history);
        return history;
    }

    private static Entity addResult(Entity docType, Schema schema) {
        Entity result = schema.addEntity("Result");
        result.addStringProperty("absolutePath");
        result.addStringProperty("extension");
        Property docTypeProperty = result.addLongProperty("docTypeId").getProperty();
        result.addToOne(docType,docTypeProperty);
        addCommonColumns(result);
        return result;
    }

    private static Entity addDocType(Schema schema) {
        Entity docType = schema.addEntity("DocType");
        docType.addStringProperty("name");
        docType.addStringProperty("extensions");
        docType.addStringProperty("scanRoot");
        addCommonColumns(docType);
        return docType;
    }

    private static void addCommonColumns(Entity entity){
        entity.addIdProperty().autoincrement();
//        entity.addContentProvider();
        entity.addIntProperty("status");
        entity.addStringProperty("remark");
    }

}