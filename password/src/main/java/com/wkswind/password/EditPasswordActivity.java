package com.wkswind.password;

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
import com.wkswind.password.databases.Password;
import com.wkswind.password.databases.Password$Table;
import com.wkswind.password.utils.Utils;

/**
 * Created by Administrator on 2015/6/18.
 */
public class EditPasswordActivity extends ToolbarActivity implements ThemePickerSwatch.OnThemeSelectedListener {
    private Password password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar_Brown);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        setupToolbar();
        enableClearIndicator();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            getToolbar().setElevation(0);
//        }
        String passwordId = getIntent().getStringExtra(Password$Table.ID);
        if(TextUtils.isEmpty(passwordId)){
            password = new Password();
        }else{
            password = new Select().from(Password.class).where(Condition.column(Password$Table.ID).is(passwordId)).querySingle();
        }

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
//                showColorPicker();
                showThemePicker();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        int color = getResources().getColor(Utils.AttributeParser.parseAttribute(this,theme,R.attr.colorPrimary));
        changeThemeColor(color);
    }
}
