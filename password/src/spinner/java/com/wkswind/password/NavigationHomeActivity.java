package com.wkswind.password;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.wkswind.password.adapters.PasswordTypeAdapter;
import com.wkswind.password.base.ToolbarActivity;
import com.wkswind.password.databases.AppDatabase;
import com.wkswind.password.databases.PasswordType;
import com.wkswind.password.databases.PasswordType$Table;
import com.wkswind.password.utils.ItemClickSupport;

import java.util.List;

/**
 * Created by Administrator on 2015/6/23.
 */
public class NavigationHomeActivity extends ToolbarActivity implements SearchView.OnQueryTextListener, View.OnClickListener, ItemClickSupport.OnItemClickListener, AdapterView.OnItemSelectedListener {
    public static final int DURATION = 400;
    private static final int REQUEST_ADD = 0x1;
    private static final float SCALE_DOWN = 0.8f;
    private FloatingActionButton fab;
    private RecyclerView content;

    private PasswordTypeAdapter adapter;

    private static final String SELECTED_ID = NavigationHomeActivity.class.getName()+".SELECTED_ID";
    private int mSelectPosition = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_navigation_view);
        if(savedInstanceState != null && savedInstanceState.containsKey(SELECTED_ID)){
            mSelectPosition = savedInstanceState.getInt(SELECTED_ID);
        }
        setupFab();
        setupRecyclerView();
        setupToolbar();
        setUpSpinner();
    }

    private void setupFab() {
        fab = (FloatingActionButton) findViewById(R.id.action_add);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (MotionEventCompat.getActionMasked(event)) {
                    case MotionEvent.ACTION_DOWN:
                        scaleFab(true);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        scaleFab(false);
                        break;
                }
                return false;
            }
        });
        fab.setOnClickListener(this);
        animateFabIn(100);
    }

    private void setupRecyclerView() {
        content = (RecyclerView) findViewById(R.id.password_content);
        content.setItemAnimator(new DefaultItemAnimator());
        int columns = getResources().getInteger(R.integer.main_grid_columns);
        RecyclerView.LayoutManager manager = columns == 1 ? new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) : new GridLayoutManager(this,columns);
        content.setLayoutManager(manager);
        ItemClickSupport.addTo(content).setOnItemClickListener(this);
    }

    private void setUpSpinner() {
        Toolbar toolbar = getToolbar();
        View spinnerContainer = LayoutInflater.from(this).inflate(R.layout.actionbar_spinner,
                toolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.addView(spinnerContainer, lp);

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.actionbar_spinner);
        List<PasswordType> types = new Select().from(PasswordType.class).where(Condition.column(PasswordType$Table.STATUS).eq(AppDatabase.STATUS_NORMAL)).queryList();
        spinner.setAdapter(new PasswordTypeAdapter(this,types,false));
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(mSelectPosition,false);
    }

    private void scaleFab(boolean scaleDown){
        ViewPropertyAnimator animator = fab.animate().setDuration(DURATION/2).setInterpolator(new AccelerateDecelerateInterpolator());
        if(scaleDown){
            animator.scaleX(SCALE_DOWN).scaleY(SCALE_DOWN);
        }else{
            animator.scaleX(1f).scaleY(1f);
        }
        animator.start();
    }

    private void animateFabIn(long delay) {
        fab.animate().setStartDelay(delay).scaleX(1).scaleY(1).setDuration(DURATION).setInterpolator(new FastOutSlowInInterpolator());
    }

    private ViewPropertyAnimator animateFabOut(long delay){
        return fab.animate().setStartDelay(delay).scaleX(0).scaleY(0).setDuration(DURATION).setInterpolator(new FastOutSlowInInterpolator());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_view:
                startActivity(new Intent(this,ViewPasswordActivity.class));
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SELECTED_ID, mSelectPosition);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_add:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                    animateFabOut(0).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            startActivityForResult(new Intent(NavigationHomeActivity.this, EditPasswordActivity.class),REQUEST_ADD);
                        }
                    });
                }else{
                    animateFabOut(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            startActivityForResult(new Intent(NavigationHomeActivity.this, EditPasswordActivity.class), REQUEST_ADD);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onItemClick(RecyclerView parent, View view, int position, long id) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        animateFabIn(0);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_ADD:
                    addItemToContent(data);
                    break;
            }
        }
    }

    private void addItemToContent(Intent data) {
//        content.getAdapter().notifyItemInserted(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
