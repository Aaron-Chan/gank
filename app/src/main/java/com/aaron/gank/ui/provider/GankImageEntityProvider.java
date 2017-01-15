package com.aaron.gank.ui.provider;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aaron.gank.App;
import com.aaron.gank.R;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.ui.activity.GankDetailActivity;
import com.aaron.gank.utils.GlideUtilsWrapper;
import com.aaron.gank.utils.ThemeUtils;
import com.aaron.library.activity.PicViewActivity;
import com.aaron.library.adapter.VH;
import com.aaron.library.utils.DateUtils;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by AaronChan on 2017/1/7.
 */

public class GankImageEntityProvider extends ItemViewProvider<GankEntity, VH> {

    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_gank_image, parent, false);
        return new VH(parent.getContext(), view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull final GankEntity gankImageEntity) {
        holder.setText(R.id.tv_title, gankImageEntity.getDesc());
        holder.setText(R.id.tv_sub_title,
                String.format(App.sContext.getString(R.string.gank_subtitle_format),
                        gankImageEntity.getWho(), DateUtils.getFriendlyTimeSpanByNow(gankImageEntity.getPublishedAt().getTime())
                ));
        final ImageView imageView = holder.getView(R.id.iv_pic);
        final String imageUrl = gankImageEntity.getImages().get(0);
        GlideUtilsWrapper.loadImage(imageView, imageUrl);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PicViewActivity.openNotZoomTheme(v.getContext(), imageUrl, gankImageEntity.getDesc(),
                        v, ThemeUtils.getCurrentTheme());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GankDetailActivity.open(view.getContext(), gankImageEntity.getUrl(),
                        gankImageEntity.getDesc(), GankDetailActivity.class);
            }
        });
    }
}
