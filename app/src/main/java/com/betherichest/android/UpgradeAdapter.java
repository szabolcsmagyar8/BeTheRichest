package com.betherichest.android;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
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
    private TextView priceTextView;
    private ImageView imageView;
    private TextView effectTextView;
    private RelativeLayout relativeLayout;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    protected UpgradeAdapter(List<Upgrade> items) {
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

    public void setItems(List<Upgrade> items) {
        this.items = items;
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
        effectTextView = listItemView.findViewById(R.id.multiplier);
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

        effectTextView.setText("X" + String.valueOf(upgrade.getMultiplier()));
        effectTextView.setTextColor(Color.parseColor("#ffffff"));

        if (effectTextView.getText().length() > 3) {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        } else {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
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
