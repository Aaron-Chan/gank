package com.aaron.gank.ui.provider;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaron.gank.R;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.utils.GlideUtilsWrapper;
import com.aaron.library.adapter.VH;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by AaronChan on 2017/1/8.
 */

public class GirlEntityProvider extends ItemViewProvider<GankEntity, VH> {
    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_gank_gril, parent, false);
        return new VH(parent.getContext(), view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull GankEntity gankEntity) {
        GlideUtilsWrapper.loadImage((ImageView) holder.getView(R.id.iv_girl), gankEntity.getUrl());
    }
}
