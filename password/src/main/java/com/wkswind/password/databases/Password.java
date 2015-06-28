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
    String password;
    @Column
    int status;
    @Column
    String secureMobile;
    @Column
    String secureEmail;
    @Column
    int themeResId;
    @Column
    String remark;
    @Column
    String safetyQuestion;
    @Column
    String safetyAnswer;

    public String getSafetyQuestion() {
        return safetyQuestion;
    }

    public void setSafetyQuestion(String safetyQuestion) {
        this.safetyQuestion = safetyQuestion;
    }

    public String getSafetyAnswer() {
        return safetyAnswer;
    }

    public void setSafetyAnswer(String safetyAnswer) {
        this.safetyAnswer = safetyAnswer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PasswordType getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(PasswordType passwordType) {
        this.passwordType = passwordType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSecureMobile() {
        return secureMobile;
    }

    public void setSecureMobile(String secureMobile) {
        this.secureMobile = secureMobile;
    }

    public String getSecureEmail() {
        return secureEmail;
    }

    public void setSecureEmail(String secureEmail) {
        this.secureEmail = secureEmail;
    }

    public int getThemeResId() {
        return themeResId;
    }

    public void setThemeResId(int themeResId) {
        this.themeResId = themeResId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
