package com.kunal.internapplication.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunal.internapplication.R;
import com.kunal.internapplication.model.Item;

import java.util.ArrayList;
import java.util.List;

public class TitleDialogAdapter extends RecyclerView.Adapter<TitleDialogAdapter.DialogViewHolder> {

    List<Item> items;
    Context mContext;
    ImageListAdapter adapter;
    OnButtonClickedListener listener;

    public TitleDialogAdapter(List<Item> titles, Context mContext, OnButtonClickedListener listener) {
        items = new ArrayList<>(titles);
        this.mContext = mContext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogViewHolder holder, int position) {
        holder.textView.setText(items.get(position).getTitle());
        holder.imageButton.setOnClickListener(
                v -> listener.onButtonClick(position, items)
        );
        if (items.get(position).getBitmaps() != null) {
//            holder.recyclerView.setHasFixedSize(true);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true));
            holder.recyclerView.setItemViewCacheSize(items.get(position).getBitmaps().size());
            adapter = new ImageListAdapter(items.get(position).getBitmaps(), mContext);
            adapter.setHasStableIds(true);
            adapter.notifyDataSetChanged();
            holder.recyclerView.setAdapter(adapter);
        }
    }

    public void addItems(List<Item> item) {
        items.clear();
        items.addAll(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class DialogViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        Button imageButton;
        RecyclerView recyclerView;

        public DialogViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.title1);
            imageButton = itemView.findViewById(R.id.imageButton);
            recyclerView = itemView.findViewById(R.id.images);
        }
    }

    public interface OnButtonClickedListener{
        void onButtonClick(int position, List<Item> items);
    }
}
