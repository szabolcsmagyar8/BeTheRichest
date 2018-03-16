package com.betherichest.android;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;

/**
 * Created by Szabi on 2018. 03. 14..
 */

public class InvestmentAdapter extends BaseAdapter{
    private List<Investment> items;
    TextView nameTextView;
    TextView priceTextView;
    TextView dpsPerRankTextView;
    TextView rankTextView;
    TextView totalDPSTextView;
    ImageView imageView;

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
    }

    public InvestmentAdapter(List<Investment> items) {
        this.items = items;
    }

    @Override
    public Object getItem(int position) {
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
            listItemView = View.inflate(parent.getContext(), R.layout.investment_listitem, null);
        } else {
            listItemView = view;
        }

        nameTextView = listItemView.findViewById(R.id.name);
        priceTextView = listItemView.findViewById(R.id.price);
        dpsPerRankTextView = listItemView.findViewById(R.id.dpsPerRank);
        rankTextView = listItemView.findViewById(R.id.rank);
        totalDPSTextView = listItemView.findViewById(R.id.total);
        imageView = listItemView.findViewById(R.id.upgradeIcon);

        Investment investment = items.get(position);
        Glide
                .with(parent.getContext())
                .load(investment.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(imageView);


        nameTextView.setText(investment.getName());

        if (investment.isBuyable()) {
            nameTextView.setTextColor(Color.parseColor("#0c6f04"));
        } else {
            nameTextView.setTextColor(Color.parseColor("#760c07"));
        }


        priceTextView.setText(nf.format(investment.getPrice()));
        rankTextView.setText(String.valueOf(investment.getRank()));
        dpsPerRankTextView.setText("DPS: " + String.valueOf(nf.format(investment.getMoneyPerSecPerRank())));
        totalDPSTextView.setText("Total: " + String.valueOf(nf.format((investment.getMoneyPerSec())))); //+ " (" + String.format("%.2f", investment.getDPSPercentage()) + "%)"));
        imageView.setBackgroundResource(investment.getImageResource());

        return listItemView;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
