package com.cocacola.besanta.common;

import android.annotation.SuppressLint;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cocacola.besanta.R;
import com.cocacola.besanta.common.views.SquaredImageView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class BindingAdapters {
    private BindingAdapters() {
    }

    @BindingAdapter("showView")
    public static void showView(View view, boolean show) {
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("hideView")
    public static void hideView(View view, boolean hide) {
        showView(view, !hide);
    }

    @BindingAdapter("imageUrl")
    public static void setImage(ImageView image, final String url) {
        // Load image
        Glide.with(image.getContext())
                .load(url)
                .into(image);
    }

    @BindingAdapter("circleImageUrl")
    public static void setCircleImage(ImageView image, final String url) {
        Glide.with(image.getContext()).load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(image);
    }

    @BindingAdapter("imageUrl")
    public static void setImage(SquaredImageView image, final String url) {
        // Load image
        Glide.with(image.getContext())
                .load(url)
                .into(image);
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("dateText")
    public static void setDateText(TextView view, Date date) {
        String result;
        SimpleDateFormat dateFormat;
        DateTime dateTime = new DateTime(date);
        if (isToday(dateTime)) {
            dateFormat = new SimpleDateFormat("HH:mm aa");
            result = view.getContext().getString(R.string.today_date, dateFormat.format(date));
        } else {
            dateFormat = new SimpleDateFormat("MMM dd, yyyy");
            result = dateFormat.format(date);
        }
        view.setText(result.toUpperCase());
    }

    public static boolean isToday(DateTime time) {
        return LocalDate.now().compareTo(new LocalDate(time)) == 0;
    }
    @BindingAdapter("priceText")
    public static void setPriceText(TextView view, int price) {
        view.setText(view.getContext().getString(R.string.price_format, price));
    }
}
