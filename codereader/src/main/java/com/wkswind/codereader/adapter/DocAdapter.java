package com.wkswind.codereader.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.format.DateUtils;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.database.Result;
import com.wkswind.codereader.utils.IconUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 源码文件结果列表
 *
 * Created by Administrator on 2015-11-27.
 */
public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocViewHolder> {
    private OnRecyclerViewItemClickListener onItemClickListener;
    private Activity activity;
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("MM-dd", Locale.getDefault());
    private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("hh:mm", Locale.getDefault());
    private SortedList<Result> sortedList;
    private LayoutInflater inflater;

    public DocAdapter(Activity activity, List<Result> datas){
        super();
        this.activity = activity;

        inflater = LayoutInflater.from(activity);
        sortedList = new SortedList<>(Result.class, new SortedListAdapterCallback<Result>(this) {
            @Override
            public int compare(Result o1, Result o2) {
                return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
            }

            @Override
            public boolean areContentsTheSame(Result oldItem, Result newItem) {
                return oldItem.getAbsolutePath().equals(newItem.getAbsolutePath());
            }

            @Override
            public boolean areItemsTheSame(Result item1, Result item2) {
                return item1.getAbsolutePath().equals(item2.getAbsolutePath());
            }
        });
        if(datas != null) {
            sortedList.addAll(datas);
        }
    }

    @Override
    public DocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doc_list, parent, false);
        return new DocViewHolder(view, activity, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(DocViewHolder holder, int position) {
        Result result = sortedList.get(position);
        File item = new File(result.getAbsolutePath());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            holder.iconMime.setBackground(IconUtils.loadMimeIcon(activity, item));
        }else{
            holder.iconMime.setBackgroundDrawable(IconUtils.loadMimeIcon(activity, item));
        }
        holder.title.setText(item.getName());
        long lastModify = item.lastModified();
        holder.date.setText(DateUtils.isToday(lastModify) ?  FORMAT_TIME.format(new Date(item.lastModified())):FORMAT_DATE.format(new Date(item.lastModified())));
        if(item.isDirectory()){
            holder.size.setVisibility(View.GONE);
        }else{
            holder.size.setVisibility(View.VISIBLE);;
        }
        holder.size.setText(Formatter.formatFileSize(activity,item.length()));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void addItems(Result... items){
        if(sortedList != null && items != null){
            sortedList.addAll(items);
        }
    }

    public static final class DocViewHolder extends RecyclerView.ViewHolder {
        private Activity activity;
        ImageView iconMime;
        TextView title, date, size;
        private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
        public DocViewHolder(View itemView, Activity activity, OnRecyclerViewItemClickListener onItemClickListener) {
            super(itemView);
            this.activity = activity;
            iconMime = (ImageView) itemView.findViewById(R.id.icon_mime);
            title = (TextView) itemView
                    .findViewById(android.R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            size = (TextView) itemView.findViewById(R.id.size);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRecyclerViewItemClickListener != null){
                        onRecyclerViewItemClickListener.onItemClick(v,getAdapterPosition(),getLayoutPosition());
                    }
                }
            });
        }
    }

    public static interface OnRecyclerViewItemClickListener {
        /**
         *
         * @param view
         * @param adapterPosition adapter中的位置
         * @param layoutPosition layout中的位置
         */
        public void onItemClick(View view,int adapterPosition, int layoutPosition);
    }

}
