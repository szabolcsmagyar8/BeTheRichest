package com.betherichest.android.Fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.GameElements.Gambling;
import com.betherichest.android.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class GamblingAdapter extends BaseAdapter {
    private List<Gambling> items;

    private TextView nameTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView winAmountTextView;
    private TextView chanceTextView;
    private ImageView imageView;

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

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
            listItemView = View.inflate(parent.getContext(), R.layout.gambling_listitem, null);
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
        priceTextView.setText("Price: " + String.valueOf((int) gambling.getPrice()));
        winAmountTextView.setText("Win amount: " + nf.format(gambling.getMinWinAmount()) + " - " + nf.format(gambling.getMaxWinAmount()) + "$");
        chanceTextView.setText("Chance: " + String.valueOf((int) gambling.getChance()) + "%");
        imageView.setBackgroundResource(gambling.getImageResource());

        return listItemView;
    }
}