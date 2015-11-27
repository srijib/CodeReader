package com.wkswind.codereader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wkswind.codereader.database.Result;

import java.util.List;

/**
 * Created by Administrator on 2015-11-27.
 */
public class DocAdapter extends RecyclerView.Adapter<DocAdapter.DocViewHolder> {
    private List<Result> datas;
    public DocAdapter(Context context,  List<Result> datas){
        super();
        this.datas = datas;

    }

    @Override
    public DocViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(DocViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static final class DocViewHolder extends RecyclerView.ViewHolder {
        public DocViewHolder(View itemView) {
            super(itemView);
        }
    }

}
