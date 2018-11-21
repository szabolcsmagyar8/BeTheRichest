package com.betherichest.android.fragments;

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

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.upgrade.GlobalIncrementUpgrade;
import com.betherichest.android.gameElements.upgrade.Upgrade;
import com.bumptech.glide.Glide;

import java.util.List;

public class UpgradeAdapter extends BaseAdapter {
    private List<Upgrade> items;
    private TextView priceTextView;
    private ImageView imageView;
    private TextView effectTextView;
    private RelativeLayout relativeLayout;
    private WindowManager wm;
    private Display display;
    private RelativeLayout.LayoutParams layoutParams;
    private Upgrade upgrade;

    protected UpgradeAdapter(List<Upgrade> items) {
        this.items = items;
        wm = (WindowManager) App.getContext().getSystemService(Context.WINDOW_SERVICE);

        display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        layoutParams = new RelativeLayout.LayoutParams(size.x / 5, size.x / 5);
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
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_upgrade, null);
        } else {
            listItemView = view;
        }

        priceTextView = listItemView.findViewById(R.id.price);
        imageView = listItemView.findViewById(R.id.investmentIcon);
        effectTextView = listItemView.findViewById(R.id.multiplier);
        relativeLayout = listItemView.findViewById(R.id.upgrade_item);

        upgrade = items.get(position);
        Glide
                .with(App.getContext())
                .load(upgrade.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        setLayoutParamsByScreenSize();
        setUITexts();
        setTextColorByAvailability();
        createColorfulBorder();

        return listItemView;
    }

    private void setLayoutParamsByScreenSize() {
        relativeLayout.setBackgroundColor(upgrade.getColor());
        relativeLayout.setLayoutParams(layoutParams);
    }

    private void setUITexts() {
        App.NF.setMaximumFractionDigits(1);
        priceTextView.setText(App.convertThousandsToSIUnit(upgrade.getPrice(), false));
        String prefix = upgrade instanceof GlobalIncrementUpgrade ? "+" : "X";

        effectTextView.setText(String.format("%s%s", prefix, upgrade.getMultiplier() % 1 == 0 ? String.valueOf((int) upgrade.getMultiplier()) : upgrade.getMultiplier()));
        effectTextView.setTextColor(upgrade.getColor());

        if (effectTextView.getText().length() > 4) {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        } else if (effectTextView.getText().length() > 3) {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 26);
        } else {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        }
    }

    private void createColorfulBorder() {       // makes a dynamic border around the relative layout which contains the image and the effect text
        GradientDrawable gd = new GradientDrawable();

        gd.setStroke((int) App.getPixelFromDP(3), upgrade.getColor());      // different borderSize in pixels for different density displays
        gd.setCornerRadius(App.getPixelFromDP(12));
        relativeLayout.setBackground(gd);
    }

    private void setTextColorByAvailability() {
        if (upgrade.isBuyable()) {
            priceTextView.setTextColor(Color.parseColor("#90EE90"));
        } else {
            priceTextView.setTextColor(Color.parseColor("#FF0027"));
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
