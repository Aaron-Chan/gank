package com.aaron.gank.ui.provider;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaron.gank.R;
import com.aaron.gank.data.entity.DailyHeader;
import com.aaron.gank.utils.GlideUtilsWrapper;
import com.aaron.library.adapter.VH;
import com.aaron.library.utils.DateUtils;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Aaron on 2017/1/6.
 */

public class DailyHeaderProvider extends ItemViewProvider<DailyHeader, VH> {


    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_daily_header, parent, false);
        return new VH(parent.getContext(), view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull DailyHeader dailyHeader) {
        holder.setText(R.id.tv_date, DateUtils.formatDate(dailyHeader.getDate()));
        GlideUtilsWrapper.loadImage((ImageView) holder.getView(R.id.iv_girl), dailyHeader.getImgUrl());
    }


}
