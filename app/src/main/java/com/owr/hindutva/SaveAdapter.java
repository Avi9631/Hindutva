package com.owr.hindutva;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SaveAdapter extends RecyclerView.Adapter<SaveAdapter.MyViewHolder> {


    private Context mContext;
    private List<HomeModel> mData ;


    public SaveAdapter(Context mContext, List<HomeModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(mContext).inflate(R.layout.post_item_layout, parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String title = mData.get(position).getName();
        String pic = mData.get(position).getImage();

        holder.setData(title, pic, position);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView posttitle;
        ImageView postimage;
        Button deleteBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            posttitle = itemView.findViewById(R.id.title);
            postimage = itemView.findViewById(R.id.imageView);
            deleteBtn= itemView.findViewById(R.id.deleteBtn);
            deleteBtn.setVisibility(View.VISIBLE);

        }

        public void setData(String title, String pic, final int position) {

            posttitle.setText(title);
            Glide.with(mContext).load(pic).into(postimage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, SavedDetailActivity.class);
                    i.putExtra("position", position);
                    itemView.getContext().startActivity(i);
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mData.remove(position);
                    SavedFragment.adapter.notifyDataSetChanged();
                    if (mData.size() == 0){
                        SavedFragment.notEmpty.setVisibility(View.VISIBLE);
                    }else{
                        SavedFragment.notEmpty.setVisibility(View.GONE);

                    }
                }
            });

        }
    }

}
