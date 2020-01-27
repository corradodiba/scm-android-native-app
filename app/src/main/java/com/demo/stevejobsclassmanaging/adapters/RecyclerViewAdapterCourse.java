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
import com.demo.stevejobsclassmanaging.model.Course;

import java.util.List;

public class RecyclerViewAdapterCourse extends RecyclerView.Adapter<RecyclerViewAdapterCourse.MyViewHolder> {

    private Context mContext ;
    private List<Course> mData ;
    RequestOptions option;


    public RecyclerViewAdapterCourse(Context mContext, List<Course> mData) {
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder( R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public RecyclerViewAdapterCourse.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.course_row_item,parent,false) ;
        final RecyclerViewAdapterCourse.MyViewHolder viewHolder = new RecyclerViewAdapterCourse.MyViewHolder(view) ;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapterCourse.MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getName());
        holder.status.setText(mData.get(position).getStatus());
        holder.year.setText(mData.get(position).getYear());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, status, year;
        LinearLayout view_container;

        public MyViewHolder(View itemView) {
            super(itemView);

            view_container = itemView.findViewById(R.id.container);
            name = itemView.findViewById(R.id.nameCourse);
            status = itemView.findViewById(R.id.statusCourse);
            year = itemView.findViewById(R.id.yearCourse);
        }
    }



}
