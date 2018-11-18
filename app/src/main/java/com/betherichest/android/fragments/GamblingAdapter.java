package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.Gambling;
import com.bumptech.glide.Glide;

import java.util.List;

public class GamblingAdapter extends BaseAdapter {
    private List<Gambling> items;

    private TextView nameTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView winAmountTextView;
    private TextView chanceTextView;
    private ImageView imageView;

    public GamblingAdapter(List<Gambling> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Gambling getItem(int position) {
        return items == null ? null : items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View listItemView = view;

        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_gambling, null);
        } else {
            listItemView = view;
        }

        nameTextView = listItemView.findViewById(R.id.name);
        priceTextView = listItemView.findViewById(R.id.price);
        winAmountTextView = listItemView.findViewById(R.id.winAmount);
        chanceTextView = listItemView.findViewById(R.id.chance);
        imageView = listItemView.findViewById(R.id.gamblingIcon);

        Gambling gambling = getItem(position);
        Glide
                .with(parent.getContext())
                .load(gambling.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        nameTextView.setText(gambling.getName());
        priceTextView.setText(String.format("Price: %s", String.valueOf(App.NF.format(gambling.getPrice()))));
        winAmountTextView.setText(String.format("Win amount: %s - %s$",
                (gambling.getMinWinAmount() >= 1000000 ? App.convertThousandsToSIUnit(gambling.getMinWinAmount(), false) : App.NF.format(gambling.getMinWinAmount())),
                (gambling.getMaxWinAmount() >= 1000000 ? App.convertThousandsToSIUnit(gambling.getMaxWinAmount(), false) : App.NF.format(gambling.getMaxWinAmount()))));
        chanceTextView.setText(String.format("Chance: %s%%", String.valueOf(gambling.getChance())));
        imageView.setBackgroundResource(gambling.getImageResource());

        return listItemView;
    }
}