package com.aaron.gank.ui.provider;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aaron.gank.App;
import com.aaron.gank.R;
import com.aaron.gank.constans.Constants;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.ui.activity.GankDetailActivity;
import com.aaron.library.adapter.VH;
import com.aaron.library.utils.SharePrefUtils;

import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Aaron on 2017/1/3.
 */

public class GankEntityProvider extends ItemViewProvider<GankEntity, VH> {

    @NonNull
    @Override
    protected VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.list_item_daily, parent, false);
        return new VH(parent.getContext(), view);
    }

    @Override
    protected void onBindViewHolder(@NonNull VH holder, @NonNull final GankEntity gankEntity) {

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(gankEntity.getDesc());
        SpannableString spannableString = new SpannableString("(by " + gankEntity.getWho() + ")");
        // 竟然用TypedArray解决不了。。
//        TypedArray array = App.sContext.obtainStyledAttributes(null, new int[]{android.R.attr.textColorPrimary});
//        try {
//
//            ForegroundColorSpan span = new ForegroundColorSpan(color);
//            spannableString.setSpan(span, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//            spannableStringBuilder.append(spannableString);
//        } finally {
//            array.recycle();
//        }
        boolean nightMode = SharePrefUtils.getBoolean(App.sContext, Constants.SP_KEY_NIGHT_MODE, false);
        int color = ContextCompat.getColor(App.sContext, nightMode ? R.color.secondary_text : R.color.night_secondary_text);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spannableString.setSpan(span, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);


        TextView title = holder.getView(R.id.tv_daily_item);
        title.setText(spannableStringBuilder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GankDetailActivity.open(view.getContext(), gankEntity.getUrl(), gankEntity.getDesc(),
                        GankDetailActivity.class);
            }
        });
    }

}
