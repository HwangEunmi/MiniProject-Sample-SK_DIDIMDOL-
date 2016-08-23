package com.example.miniprojectx;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miniprojectx.data.ContentData;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-08-12.
 */
public class ContentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.text_content)
    TextView contentView;
    @BindView(R.id.image_picture)
    ImageView pictureView;

    public ContentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContent(ContentData content) {
        contentView.setText(content.getContent());
        Glide.with(pictureView.getContext())
                .load(content.getImageUrl())
                .into(pictureView);
        // TextView랑 ImageView에 나중에 값 넣어줄거니까
        // set해줌
    }

}
