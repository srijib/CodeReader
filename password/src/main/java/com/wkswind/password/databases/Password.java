package com.wkswind.password.databases;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import java.util.List;

/**
 * Created by Administrator on 2015/6/9.
 */
@Table(databaseName = AppDatabase.NAME)
public class Password extends BaseModel{
    @Column
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String name;
    @Column
    @ForeignKey(references = {@ForeignKeyReference(columnName = "type_id", columnType = Long.class, foreignColumnName = "id")}, saveForeignKeyModel = false)
    PasswordType passwordType;
    @Column
    @ForeignKey(
            references = {@ForeignKeyReference(columnName = "question_id",
                    columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    ForeignKeyContainer<SafetyQuestion> safetyQuestions;
    @Column
    int status;
    @Column
    String secureMobile;
    @Column
    String secureEmail;
    @Column
    int backgroundColor;
    List<SafetyQuestion> allSafetyQuestions;

    @OneToMany(methods = {OneToMany.Method.ALL})
    public List<SafetyQuestion> getAllSafetyQuestions(){
        if(allSafetyQuestions == null){
            allSafetyQuestions = new Select().from(SafetyQuestion.class).where(Condition.column(SafetyQuestion$Table.PASSWORDID).is(id)).queryList();
        }
        return allSafetyQuestions;
    }

}
