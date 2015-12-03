package com.wkswind.codereader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2015-12-3.
 */
public abstract class BaseAdapter <VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public static interface OnRecyclerViewItemClickListener {
        /**
         *
         * @param view
         * @param adapterPosition adapter中的位置
         * @param layoutPosition layout中的位置
         */
        public void onItemClick(View view,int adapterPosition, int layoutPosition);
    }

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final int adapterPosition = holder.getAdapterPosition();
        final int layoutPosition = holder.getLayoutPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onRecyclerViewItemClickListener != null){
                    onRecyclerViewItemClickListener.onItemClick(v,adapterPosition,layoutPosition);
                }
            }
        });
    }

}
