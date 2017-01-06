package com.aaron.gank.ui.provider;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aaron.gank.R;
import com.aaron.gank.data.entity.CategoryTitle;
import com.aaron.library.adapter.VH;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Aaron on 2017/1/6.
 */

public class CategoryTitleProvider extends ItemViewProvider<CategoryTitle, VH> {


    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_daily_category, parent, false);
        return new VH(parent.getContext(), view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull CategoryTitle categoryTitle) {
        holder.setText(R.id.tv_category, categoryTitle.getTitle());
    }


}
