package com.aaron.gank.ui.provider;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.gank.R;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.ui.activity.GankDetailActivity;
import com.aaron.library.adapter.VH;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by AaronChan on 2017/1/7.
 */

public class GankTextEntityProvider extends ItemViewProvider<GankEntity, VH> {

    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_gank_text, parent, false);
        return new VH(parent.getContext(), view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull final GankEntity gankEntity) {
        holder.setText(R.id.tv_title, gankEntity.getDesc());
        holder.setText(R.id.tv_sub_title, gankEntity.getWho());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GankDetailActivity.open(view.getContext(), gankEntity.getUrl(),
                        gankEntity.getDesc(),GankDetailActivity.class);
            }
        });
    }
}
