package com.betherichest.android;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Szabi on 2018.03.27..
 */

public class UpgradeAdapter extends BaseAdapter {
    private List<Upgrade> items;
    TextView priceTextView;
    ImageView imageView;
    TextView labelTextView;
    RelativeLayout relativeLayout;

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    public UpgradeAdapter(List<Upgrade> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public Upgrade getItem(int position) {
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
            listItemView = View.inflate(parent.getContext(), R.layout.upgrade_listitem, null);
        } else {
            listItemView = view;
        }

        priceTextView = listItemView.findViewById(R.id.price);
        imageView = listItemView.findViewById(R.id.upgradeIcon);
        labelTextView = listItemView.findViewById(R.id.multiplier);
        relativeLayout = listItemView.findViewById(R.id.layout);

        Upgrade upgrade = items.get(position);
        Glide
                .with(parent.getContext())
                .load(upgrade.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        priceTextView.setText(nf.format(upgrade.getPrice()));
        labelTextView.setTextColor(upgrade.getColor());
        if (labelTextView.getText().length() > 3) {
            labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,23);
        } else {
            labelTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,25);
        }
        setTextColorByAvailability(upgrade);
        convertThousandsToSIUnit(upgrade);

        return listItemView;
    }

    private void setTextColorByAvailability(Upgrade upgrade) {
        if (upgrade.isBuyable()) {
            priceTextView.setTextColor(Color.parseColor("#90EE90"));
        } else {
            priceTextView.setTextColor(Color.parseColor("#F2003C"));
        }
    }

    private void convertThousandsToSIUnit(Upgrade upgrade) {
        double price = upgrade.getPrice();
        if (price < 10000) {
            priceTextView.setText(nf.format(price));
        } else if (price >= 10000 && price < 1000000) {
            priceTextView.setText(nf.format(price / 1000d) + "K");
        } else if (price >= 1000000 && price < 1000000000) {
            priceTextView.setText(nf.format(price / 1000000d) + "M");
        } else if (price >= 1000000000) {
            priceTextView.setText(nf.format(price / 1000000000d) + "B");
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
