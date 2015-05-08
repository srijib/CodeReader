package com.wkswind.money.drawer;

import android.content.Context;
import android.content.res.Resources;

import com.wkswind.money.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/5/8.
 */
public class DrawerItemUtils {
    public static ArrayList<DrawerItem> initDrawerItems(Context context){
        ArrayList<DrawerItem> items = new ArrayList<>();
        Resources res = context.getResources();
        String[] arrays = res.getStringArray(R.array.label_items_money);
        for(int i=0,j=arrays.length;i<j;i++){
            DrawerItem item = new DrawerItem();
            item.setLabel(arrays[i]);
            items.add(item);
        }
        if(!items.isEmpty()){
            items.add(DrawerItem.newDivider());
        }
        arrays = res.getStringArray(R.array.label_items_chart);
        for(int i=0,j=arrays.length;i<j;i++){
            DrawerItem item = new DrawerItem();
            item.setLabel(arrays[i]);
            items.add(item);
        }
        if(!items.isEmpty()){
            items.add(DrawerItem.newDivider());
        }
        arrays = res.getStringArray(R.array.label_items_others);
        for(int i=0,j=arrays.length;i<j;i++){
            DrawerItem item = new DrawerItem();
            item.setLabel(arrays[i]);
            items.add(item);
        }
        return items;
    }
}
