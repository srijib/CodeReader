package com.wkswind.money.drawer;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.wkswind.money.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/5/8.
 */
public class DrawerItemUtils {
    public static DrawerItem newDivider(){
        DrawerItem item = new DrawerItem();
        item.setType(DrawerItem.TYPE_DIVIDER);
        return item;
    }

    public static ArrayList<DrawerItem> initDrawerItems(Context context){
        Resources res = context.getResources();
        ArrayList<DrawerItem> result = new ArrayList<>();
        TypedArray labelArray = res.obtainTypedArray(R.array.drawer_labels);
        TypedArray iconArray = res.obtainTypedArray(R.array.drawer_icons);
        for(int i=0, j= labelArray.length();i<j;i++){
            int labelsId = labelArray.getResourceId(i, -1);
            int iconsId = iconArray.getResourceId(i,-1);
            TypedArray ita = res.obtainTypedArray(iconsId);
            String[] labels = res.getStringArray(labelsId);
            for(int m=0, n=labels.length;m<n;m++){
                DrawerItem item = new DrawerItem();
                item.setLabel(labels[m]);
                item.setIconId(ita.getResourceId(m,-1));
                item.setQueryType(item.getLabel());
                result.add(item);
            }
//            if(i<j){
                result.add(newDivider());
//            }
            ita.recycle();
        }
        labelArray.recycle();
        iconArray.recycle();
//        result.remove(result.size()-1);
        return result;
    }
//    public static ArrayList<DrawerItem> initDrawerItems(Context context){
//        ArrayList<DrawerItem> items = new ArrayList<>();
//        Resources res = context.getResources();
//        String[] arrays = res.getStringArray(R.array.label_items_money);
//        for(int i=0,j=arrays.length;i<j;i++){
//            DrawerItem item = new DrawerItem();
//            item.setLabel(arrays[i]);
//            items.add(item);
//        }
//        if(!items.isEmpty()){
//            items.add(DrawerItem.newDivider());
//        }
//        arrays = res.getStringArray(R.array.label_items_chart);
//        for(int i=0,j=arrays.length;i<j;i++){
//            DrawerItem item = new DrawerItem();
//            item.setLabel(arrays[i]);
//            items.add(item);
//        }
//        if(!items.isEmpty()){
//            items.add(DrawerItem.newDivider());
//        }
//        arrays = res.getStringArray(R.array.label_items_others);
//        for(int i=0,j=arrays.length;i<j;i++){
//            DrawerItem item = new DrawerItem();
//            item.setLabel(arrays[i]);
//            items.add(item);
//        }
//        return items;
//    }
}
