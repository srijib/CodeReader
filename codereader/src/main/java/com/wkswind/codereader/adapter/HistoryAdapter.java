package com.wkswind.codereader.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.database.History;
import com.wkswind.codereader.utils.Constants;
import com.wkswind.codereader.utils.IconUtils;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015-12-7.
 */
public class HistoryAdapter extends BaseAdapter<HistoryAdapter.HistoryViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private SortedList<History> sortedList;
    public HistoryAdapter(Activity activity, List<History> datas){
        context = activity.getApplication();
        inflater = LayoutInflater.from(activity);
        sortedList = new SortedList<History>(History.class, new SortedListAdapterCallback<History>(this) {
            @Override
            public int compare(History o1, History o2) {
                return (int) (o1.getLastReadTime().getTime() - o2.getLastReadTime().getTime());
            }

            @Override
            public boolean areContentsTheSame(History oldItem, History newItem) {
                return oldItem.getResultId().equals(newItem.getResult());
            }

            @Override
            public boolean areItemsTheSame(History item1, History item2) {
                return item1.getResultId().equals(item2.getResultId());
            }
        });
        sortedList.addAll(datas);
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_doc_list, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        History data = sortedList.get(position);
        holder.size.setVisibility(View.GONE);
        holder.title.setText(FilenameUtils.getName(data.getResult().getAbsolutePath()));
        Date lastModify = data.getLastReadTime();
        holder.date.setText(DateUtils.isToday(lastModify.getTime()) ?  Constants.FORMAT_TIME.format(lastModify):Constants.FORMAT_DATE.format(lastModify));
        File item = new File(data.getResult().getAbsolutePath());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            holder.iconMime.setBackground(IconUtils.loadMimeIcon(context, item));
        }else{
            holder.iconMime.setBackgroundDrawable(IconUtils.loadMimeIcon(context, item));
        }
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, size;
        ImageView iconMime;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            iconMime = (ImageView) itemView.findViewById(R.id.icon_mime);
            title = (TextView) itemView.findViewById(android.R.id.title);
            date = (TextView) itemView.findViewById(R.id.date);
            size = (TextView) itemView.findViewById(R.id.size);
        }
    }
}
