package com.wkswind.codereader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wkswind.codereader.base.ToolbarActivity;
import com.wkswind.codereader.database.DocType;
import com.wkswind.codereader.database.DataUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 首页
 * Created by Administrator on 2015-11-17.
 */
public class HomeActivity extends ToolbarActivity implements Drawer.OnDrawerItemClickListener {
    private DrawerBuilder drawerBuilder;
    private Toolbar toolbar;
    private Observable<List<DocType>> observable;
    private Drawer drawer;
    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        ViewCompat.setTranslationZ(toolbar,getResources().getDimensionPixelOffset(R.dimen.headerbar_elevation));
        drawerBuilder = new DrawerBuilder(this).withDelayOnDrawerClose(-1);
        drawerBuilder.addDrawerItems(starredDrawerItem()).addDrawerItems(historyDrawerItem()).addDrawerItems(drawerDivider());
        drawerBuilder.withOnDrawerItemClickListener(this);
        RxToolbar.navigationClicks(toolbar).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if(drawer != null){
                    if(drawer.isDrawerOpen()){
                        drawer.closeDrawer();
                    }else{
                        drawer.openDrawer();
                    }
                }
            }
        });

        observable = DataUtils.getAllDocTypes(getApplication());

        subscription = observable.subscribe(new Subscriber<List<DocType>>() {
            @Override
            public void onCompleted() {
                drawerBuilder.addStickyDrawerItems(configureDocTypeDrawerItem(),feedbackDrawerItem(),settingsDrawerItem());
                drawer = drawerBuilder.build();
            }
            @Override
            public void onError(Throwable e) {}
            @Override
            public void onNext(List<DocType> docTypes) {
                if(docTypes != null && !docTypes.isEmpty()){
                    for(int i=0,j=docTypes.size();i<j;i++){
                        DocType type = docTypes.get(i);
                        PrimaryDrawerItem item =docTypeDrawerItem(type);
                        formatDrawerColor(item);
                        drawerBuilder.addDrawerItems(item);
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }

    private PrimaryDrawerItem docTypeDrawerItem(DocType type){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(type.getName()).withIcon(R.drawable.ic_description_24dp);
        return item;
    }

    /**
     * 收藏
     * @return
     */
    private PrimaryDrawerItem starredDrawerItem(){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(R.string.action_starred).withIcon(R.drawable.ic_star_24dp).withIdentifier(R.id.starred_drawer);
        formatDrawerColor(item);
        return item;
    }

    private PrimaryDrawerItem configureDocTypeDrawerItem(){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(R.string.action_configure_doctype).withIcon(R.drawable.ic_configure_24dp).withIdentifier(R.id.configure_drawer);
        formatDrawerColor(item);
        item.withSelectable(false);
        return item;
    }

    private PrimaryDrawerItem settingsDrawerItem(){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(R.string.action_settings).withIcon(R.drawable.ic_settings_24dp).withIdentifier(R.id.setting_drawer).withSelectable(false);
        formatDrawerColor(item);
        item.withSelectable(false);
        return item;
    }

    private PrimaryDrawerItem feedbackDrawerItem(){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(R.string.action_feedback).withIcon(R.drawable.ic_help_24dp).withIdentifier(R.id.feedback_drawer).withSelectable(false);
        formatDrawerColor(item);
        item.withSelectable(false);
        return item;
    }

    /**
     * 浏览历史
     * @return
     */
    private PrimaryDrawerItem historyDrawerItem(){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(R.string.action_history).withIcon(R.drawable.ic_history_24dp).withIdentifier(R.id.history_drawer);
        formatDrawerColor(item);
        return item;
    }


    private IDrawerItem drawerDivider(){
        return new DividerDrawerItem();
    }

    private void formatDrawerColor(PrimaryDrawerItem item){
        item.withSelectedColorRes(R.color.navdrawer_icon_tint_selected)
                .withIconColorRes(R.color.navdrawer_icon_tint);
        item.withSelectedTextColorRes(R.color.navdrawer_text_color_selected)
                .withTextColorRes(R.color.navdrawer_text_color);
        item.withSelectedColorRes(android.R.color.transparent);
//        item.withSelectedColorRes(android.R.color.white);
        item.withIconTintingEnabled(true);
//        item.with
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        switch (drawerItem.getIdentifier()){
            case R.id.action_starred:
                break;
            case R.id.history_drawer:
                break;
            case R.id.configure_drawer:
                startActivity(new Intent(this, DocTypeActivity.class));
                break;
            case R.id.feedback_drawer:
                break;
            case R.id.setting_drawer:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }
}
