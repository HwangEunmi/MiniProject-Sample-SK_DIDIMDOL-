package com.example.miniprojectx;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miniprojectx.data.ContentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-08-12.
 */
public class ContentAdapter extends RecyclerView.Adapter<ContentViewHolder> {
   // 이미지의 어댑터

    List<ContentData> items = new ArrayList<>();

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<ContentData> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        holder.setContent(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
