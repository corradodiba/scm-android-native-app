package com.demo.stevejobsclassmanaging.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.request.RequestOptions;
import com.demo.stevejobsclassmanaging.R;
import com.demo.stevejobsclassmanaging.model.Subject;


import java.util.List;

public class RecyclerViewAdapterSubject extends RecyclerView.Adapter<RecyclerViewAdapterSubject.MyViewHolder> {

    private Context mContext ;
    private List<Subject> mData ;
    RequestOptions option;


    public RecyclerViewAdapterSubject(Context mContext, List<Subject> mData) {
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder( R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public RecyclerViewAdapterSubject.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.subjects_row_item,parent,false) ;
        final RecyclerViewAdapterSubject.MyViewHolder viewHolder = new RecyclerViewAdapterSubject.MyViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterSubject.MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getName());
        holder.hour.setText(mData.get(position).getHours());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name ;
        TextView hour ;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            name = itemView.findViewById(R.id.nameSubjects);
            hour = itemView.findViewById(R.id.hourSubjects);
        }
    }



}
