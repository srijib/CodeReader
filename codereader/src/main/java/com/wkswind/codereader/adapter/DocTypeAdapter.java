package com.wkswind.codereader.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wkswind.codereader.R;
import com.wkswind.codereader.database.DocType;

import java.util.List;

/**
 * Created by Administrator on 2015-12-3.
 */
public class DocTypeAdapter extends BaseAdapter<DocTypeAdapter.DocTypeViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private SortedList<DocType> sortedList;
    public DocTypeAdapter(@NonNull Activity activity,@NonNull List<DocType> docTypes){
        inflater = LayoutInflater.from(activity);
        context = activity.getApplication();
        sortedList = new SortedList<DocType>(DocType.class, new SortedListAdapterCallback<DocType>(this) {
            @Override
            public int compare(DocType o1, DocType o2) {
                return o1.getName().compareTo(o2.getName());
            }

            @Override
            public boolean areContentsTheSame(DocType oldItem, DocType newItem) {
                return oldItem.getName().equals(newItem.getName());
            }

            @Override
            public boolean areItemsTheSame(DocType item1, DocType item2) {
                return item1.getName().equals(item2.getName());
            }
        });
        sortedList.addAll(docTypes);
    }
    @Override
    public DocTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_root_list, parent, false);
        return new DocTypeViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void addDocType(DocType type){
        sortedList.add(type);
    }

    @Override
    public void onBindViewHolder(DocTypeViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        DocType item = sortedList.get(position);
        holder.name.setText(item.getName());
    }

    public void delete(int position) {
        sortedList.removeItemAt(position);
    }

    public static class DocTypeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public DocTypeViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
