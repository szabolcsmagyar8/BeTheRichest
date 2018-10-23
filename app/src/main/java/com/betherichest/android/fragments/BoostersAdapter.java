package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.R;
import com.betherichest.android.activities.MainActivity;
import com.betherichest.android.gameElements.Booster;
import com.betherichest.android.util.Inventory;

import java.util.List;

public class BoostersAdapter extends BaseAdapter {
    private View listItemView;
    private TextView nameTextView;
    private TextView priceTextView;
    private ImageView iconImageView;
    private List<Booster> items;
    Inventory inventory;

    public BoostersAdapter(List<Booster> items, Inventory inv) {
        this.items = items;
        this.inventory = inv;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Booster getItem(int i) {
        return items == null ? null : items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        listItemView = view;
        if (listItemView == null) {
            listItemView = View.inflate(MainActivity.getContext(), R.layout.listitem_booster, null);
        } else {
            listItemView = view;
        }

        nameTextView = listItemView.findViewById(R.id.interval);
        priceTextView = listItemView.findViewById(R.id.price);
        iconImageView = listItemView.findViewById(R.id.booster_icon);

        nameTextView.setText(String.valueOf(items.get(i).getInterval()) + "h");
        //priceTextView.setText(String.valueOf(items.get(i).getPrice() + "$"));
        priceTextView.setText(inventory.getSkuDetails(items.get(i).getSkuId()).getPrice());
        iconImageView.setImageResource(items.get(i).getImageResource());

        return listItemView;
    }
}
