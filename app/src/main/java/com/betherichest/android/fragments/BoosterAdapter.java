package com.betherichest.android.fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.Booster;
import com.betherichest.android.util.Inventory;

import java.util.List;

public class BoosterAdapter extends BaseAdapter {
    private View listItemView;
    private TextView nameTextView;
    private TextView rewardTextView;
    private TextView priceTextView;
    private ImageView iconImageView;
    private List<Booster> items;
    Inventory inventory;

    public BoosterAdapter(List<Booster> items, Inventory inv) {
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
            listItemView = View.inflate(App.getContext(), R.layout.listitem_booster, null);
        } else {
            listItemView = view;
        }

        nameTextView = listItemView.findViewById(R.id.interval);
        rewardTextView = listItemView.findViewById(R.id.reward);
        priceTextView = listItemView.findViewById(R.id.investment_price);
        iconImageView = listItemView.findViewById(R.id.booster_icon);

        nameTextView.setText(items.get(i).getTitle());
        rewardTextView.setText("Reward: " + App.convertThousandsToSIUnit(items.get(i).getActualReward(), false) );
        if (inventory != null) {
            priceTextView.setText(inventory.getSkuDetails(items.get(i).getSkuId()).getPrice());
        } else {
            priceTextView.setText(items.get(i).getPriceText());
        }
        iconImageView.setImageResource(items.get(i).getImageResource());

        return listItemView;
    }
}
