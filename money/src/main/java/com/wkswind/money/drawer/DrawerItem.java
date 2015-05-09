package com.wkswind.money.drawer;

/**
 * Created by Administrator on 2015/5/8.
 */
public class DrawerItem {
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_DIVIDER = 1;

    private String label;
    private String queryType;
    private int iconId ;

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    private int type = TYPE_ITEM;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSeparator(){
        return type == TYPE_DIVIDER;
    }
}
