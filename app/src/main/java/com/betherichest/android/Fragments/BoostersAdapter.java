package com.betherichest.android.Fragments;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.Activities.MainActivity;
import com.betherichest.android.GameElements.Booster;
import com.betherichest.android.ListenerInterfaces.AdapterRefreshListener;
import com.betherichest.android.Mangers.Game;
import com.betherichest.android.R;

import java.util.List;

public class BoostersAdapter extends BaseAdapter {
    private View listItemView;
    private TextView nameTextView;
    private TextView priceTextView;
    private ImageView iconImageView;
    private List<Booster> items;

    public BoostersAdapter(List<Booster> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
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

        Game.getInstance().adapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                BoostersAdapter.super.notifyDataSetChanged();
            }
        };

        nameTextView = listItemView.findViewById(R.id.interval);
        priceTextView = listItemView.findViewById(R.id.price);
        iconImageView = listItemView.findViewById(R.id.booster_icon);

        nameTextView.setText(String.valueOf(items.get(i).getInterval()) + "h");
        priceTextView.setText(String.valueOf(items.get(i).getPrice() + "$"));
        iconImageView.setImageResource(items.get(i).getImageResource());

        return listItemView;
    }
}
