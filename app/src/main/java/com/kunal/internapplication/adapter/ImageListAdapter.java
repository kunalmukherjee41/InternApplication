package com.kunal.internapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunal.internapplication.R;

import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ListViewAdapter> {

    List<Bitmap> bitmaps;
    Context mContext;

    public ImageListAdapter(List<Bitmap> bitmaps, Context mContext) {
        this.bitmaps = bitmaps;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ListViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list, parent, false);
        return new ListViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewAdapter holder, int position) {
        holder.image.setImageBitmap(bitmaps.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void addImages(List<Bitmap> bitmaps1) {
        bitmaps.clear();
        bitmaps.addAll(bitmaps1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    public static class ListViewAdapter extends RecyclerView.ViewHolder {

        ImageView image;

        public ListViewAdapter(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
