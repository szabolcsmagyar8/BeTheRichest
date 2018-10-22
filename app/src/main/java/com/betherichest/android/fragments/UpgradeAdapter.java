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

import com.betherichest.android.gameElements.Upgrade;
import com.betherichest.android.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class UpgradeAdapter extends BaseAdapter {
    private List<Upgrade> items;
    private TextView priceTextView;
    private ImageView imageView;
    private TextView effectTextView;
    private RelativeLayout relativeLayout;
    private Context context;
    private Upgrade upgrade;

    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    protected UpgradeAdapter(List<Upgrade> items, Context context) {
        this.items = items;
        this.context = context;
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

        final View finalListItemView = listItemView;
//        listItemView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                GradientDrawable shape = new GradientDrawable();
//                shape.setCornerRadius(20);
//                shape.setColor(App.getContext().getResources().getColor(R.color.upgradeHighlightColor));
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                         finalListItemView.setBackground(shape);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                    case MotionEvent.ACTION_CANCEL:
//                        finalListItemView.setBackgroundColor(Color.TRANSPARENT);
//                        break;
//                }
//                return false;
//            }
//        });

        priceTextView = listItemView.findViewById(R.id.price);
        imageView = listItemView.findViewById(R.id.investmentIcon);
        effectTextView = listItemView.findViewById(R.id.multiplier);
        relativeLayout = listItemView.findViewById(R.id.boosterItem);

        upgrade = items.get(position);
        Glide
                .with(parent.getContext())
                .load(upgrade.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);

        setLayoutParamsByScreenSize();
        setLabelTexts();
        setTextColorByAvailability();
        createColorfulBorder();
        convertThousandsToSIUnit();

        return listItemView;
    }

    private void setLayoutParamsByScreenSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(size.x / 5, size.x / 5);
        relativeLayout.setBackgroundColor(upgrade.getColor());
        relativeLayout.setLayoutParams(layoutParams);
    }

    private void setLabelTexts() {
        priceTextView.setText(nf.format(upgrade.getPrice()));

        effectTextView.setText("X" + String.valueOf(upgrade.getMultiplier()));
        effectTextView.setTextColor(upgrade.getColor());

        if (effectTextView.getText().length() > 3) {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        } else {
            effectTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 28);
        }
    }

    private void createColorfulBorder() {  // makes a dynamic border around the relative layout which contains the image and the effect text
        GradientDrawable gd = new GradientDrawable();

        gd.setStroke((int) getPixelFromDP(3), upgrade.getColor());      // different borderSize in pixels for different density displays
        gd.setCornerRadius(getPixelFromDP(12));
        relativeLayout.setBackground(gd);
    }

    private float getPixelFromDP(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private void setTextColorByAvailability() {
        if (upgrade.isBuyable()) {
            priceTextView.setTextColor(Color.parseColor("#90EE90"));
        } else {
            priceTextView.setTextColor(Color.parseColor("#FF0027"));
        }
    }

    private void convertThousandsToSIUnit() {
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