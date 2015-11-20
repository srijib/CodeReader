package com.wkswind.codereader;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.wkswind.codereader.base.CodeReaderApplication;
import com.wkswind.codereader.base.ToolbarActivity;
import com.wkswind.codereader.database.DocType;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Administrator on 2015-11-17.
 */
public class HomeActivity extends ToolbarActivity {
    private DrawerBuilder drawerBuilder;
    private Toolbar toolbar;
    private Observable<List<DocType>> observable;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = getToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        ViewCompat.setTranslationZ(toolbar,getResources().getDimensionPixelOffset(R.dimen.headerbar_elevation));
        drawerBuilder = new DrawerBuilder(this).withDelayOnDrawerClose(-1);
        drawerBuilder.addDrawerItems(starredDrawerItem()).addDrawerItems(historyDrawerItem()).addDrawerItems(drawerDivider());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer != null){
                    if(drawer.isDrawerOpen()){
                        drawer.closeDrawer();
                    }else{
                        drawer.openDrawer();
                    }
                }
            }
        });

        observable = Observable.create(new Observable.OnSubscribe<List<DocType>>() {
            @Override
            public void call(Subscriber<? super List<DocType>> subscriber) {
                try {
                    List<DocType> result = queryDocType();
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(result);
                    }
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                } catch (Throwable e) {
                    subscriber.onError(e);
                }
            }
        });
        observable.subscribe(new Action1<List<DocType>>() {
            @Override
            public void call(List<DocType> docTypes) {
                if(docTypes != null && !docTypes.isEmpty()){
                    for(int i=0,j=docTypes.size();i<j;i++){
                        DocType type = docTypes.get(i);
                        PrimaryDrawerItem item =docTypeDrawerItem(type);
                        formatDrawerColor(item);
                        drawerBuilder.addDrawerItems(item);
                    }
                }
                drawer = drawerBuilder.build();
            }
        });

//        Observable.
    }

    private PrimaryDrawerItem docTypeDrawerItem(DocType type){
        PrimaryDrawerItem item = new PrimaryDrawerItem();
        item.withName(type.getName()).withIcon(R.drawable.ic_description_24dp);
        return item;
    }

    private List<DocType> queryDocType(){
        List<DocType> result = CodeReaderApplication.getSession().getDocTypeDao().queryBuilder().list();
        return result;
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
}
