package com.wkswind.password;

import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wkswind.password.base.ToolbarActivity;
import com.wkswind.password.custom.ColorPickerDialog;
import com.wkswind.password.custom.ColorPickerSwatch;
import com.wkswind.password.databases.Password;
import com.wkswind.password.databases.Password$Table;
import com.wkswind.password.utils.Utils;

/**
 * Created by Administrator on 2015/6/18.
 */
public class EditPasswordActivity extends ToolbarActivity implements ColorPickerSwatch.OnColorSelectedListener {
    private Password password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                showColorPicker();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showColorPicker() {
//        int[] mColorIds = getResources().getIntArray(R.array.default_color_choice_values);
        TypedArray ta = getResources().obtainTypedArray(R.array.default_color_choice_values);
        int[] colors = new int[ta.length()];
        for(int i=0,j=ta.length();i<j;i++){
            colors[i] = getResources().getColor(ta.getResourceId(i, R.color.colorPrimary));
        }
        ta.recycle();
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(R.string.action_color_selector,colors,getResources().getColor(R.color.colorPrimary), 4,ColorPickerDialog.SIZE_SMALL);
        dialog.setOnColorSelectedListener(this);
        dialog.show(getSupportFragmentManager(), getString(R.string.action_color_selector));
    }

    @Override
    public void onColorSelected(int color) {
        changeThemeColor(color);
    }
}
