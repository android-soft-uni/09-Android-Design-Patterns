package com.cocacola.besanta.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public final class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryHolder> {

    private List<Story> data = new ArrayList<>();

    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_story, parent, false);
        StoryHolder vh = new StoryHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(StoryHolder holder, int position) {
        Story story = data.get(position);
        holder.setupViews(story);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void replace(List<Story> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class StoryHolder extends RecyclerView.ViewHolder {

        View view;
        public StoryHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            setupListener(itemView);
        }

        private void setupListener(View itemView) {
            itemView.findViewById(R.id.btn_overflow).setOnClickListener(view -> {
                PopupMenu popup = new PopupMenu(itemView.getContext(), view);
                MenuInflater menuInflater = popup.getMenuInflater();
                menuInflater.inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.item_report) {
                        return true;
                    }
                    return false;
                });
                popup.show();
            });
        }

        private void setupViews(Story story) {
            Glide.with(view.getContext())
                    .load(story.userImageUrl).into((ImageView) view.findViewById(R.id.img_avatar));
            Glide.with(view.getContext())
                    .load(story.imageUrl).into((ImageView) view.findViewById(R.id.img_feed_center));

            ((TextView) view.findViewById(R.id.txt_full_name)).setText(story.getUserFullName());
            ((TextView) view.findViewById(R.id.txt_story)).setText(story.text);
            setDateText((TextView) view.findViewById(R.id.txt_story_date), story.timestamp);
//            ((TextView) view.findViewById(R.id.txt_full_name)).
        }

        public void setDateText(TextView view, Date date) {
            String result;
            SimpleDateFormat dateFormat;
            if (isToday(date)) {
                dateFormat = new SimpleDateFormat("HH:mm aa");
                result = view.getContext().getString(R.string.today_date, dateFormat.format(date));
            } else {
                dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                result = dateFormat.format(date);
            }
            view.setText(result.toUpperCase());
        }

        private boolean isToday(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            boolean sameYear = calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR);
            boolean sameMonth = calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH);
            boolean sameDay = calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH);
            return (sameDay && sameMonth && sameYear);
        }
    }
}
