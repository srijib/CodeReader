package com.wkswind.password;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wkswind.password.base.ToolbarActivity;
import com.wkswind.password.custom.themepicker.ThemePickerDialog;
import com.wkswind.password.custom.themepicker.ThemePickerSwatch;
import com.wkswind.password.databases.AppDatabase;
import com.wkswind.password.databases.Password;
import com.wkswind.password.databases.Password$Table;
import com.wkswind.password.utils.NativeUtils;
import com.wkswind.password.utils.Utils;

/**
 * Created by Administrator on 2015/6/18.
 */
public class EditPasswordActivity extends ToolbarActivity implements ThemePickerSwatch.OnThemeSelectedListener {
    boolean edit = true;
    private Password password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String passwordId = getIntent().getStringExtra(Password$Table.ID);
        if(TextUtils.isEmpty(passwordId)){
            edit = false;
            password = new Password();
        }else{
            password = new Select().from(Password.class).where(Condition.column(Password$Table.ID).is(passwordId)).querySingle();
            if(password != null){
                int themeResId = password.getThemeResId();
                if(themeResId > 0){
                    setTheme(themeResId);
                }
            }
        }
        setTheme(R.style.AppTheme_NoActionBar_Gray);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        setupToolbar();
        enableClearIndicator();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            getToolbar().setElevation(0);
//        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_color_selector:
                showThemePicker();
                break;
            case R.id.action_done:
                savePassword();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePassword() {
        password.save();
        Intent data = new Intent();
        data.putExtra(Password$Table.ID, password.getId());
        setResult(Activity.RESULT_OK, new Intent());
    }

    private void showThemePicker(){
        TypedArray ta = getResources().obtainTypedArray(R.array.default_theme_chooser);
        int[] themes = new int[ta.length()];
        for(int i=0,j=ta.length();i<j;i++){
            themes[i] = ta.getResourceId(i, R.style.AppTheme_NoActionBar);
        }
        ta.recycle();
        ThemePickerDialog dialog = ThemePickerDialog.newInstance(R.string.action_color_selector,themes,R.style.AppTheme_NoActionBar_Red, 4,ThemePickerDialog.SIZE_SMALL);
        dialog.setOnColorSelectedListener(this);
        dialog.show(getSupportFragmentManager(), getString(R.string.action_color_selector));
    }

    @Override
    public void onThemeSelected(int theme) {
        changeThemeColor(theme);
        password.setThemeResId(theme);
        password.setName("DEMO");
        password.setStatus(AppDatabase.STATUS_NORMAL);
        password.setRemark("DEMO");
        password.setPassword(NativeUtils.encrypt("demopassword"));
        password.setSecureEmail("wkswind@gmail.com");
        password.setSecureMobile("18565670861");
    }
}
