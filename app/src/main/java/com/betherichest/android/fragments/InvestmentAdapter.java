package com.betherichest.android.fragments;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.gameElements.Investment;
import com.betherichest.android.mangers.Game;
import com.bumptech.glide.Glide;

import java.util.List;

public class InvestmentAdapter extends BaseAdapter {
    View listItemView;
    private List<Investment> items;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView dpsPerLevelTextView;
    private TextView levelTextView;
    private TextView totalDPSTextView;
    private TextView upsTextView;
    private ImageView investmentImageView;
    private ImageView dollarImageView;
    Investment investment;

    public InvestmentAdapter(List<Investment> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items == null ? 0 : items.size();
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
        listItemView = view;

        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.listitem_investment, null);
        } else {
            listItemView = view;
        }

        investment = items.get(position);
        initializeListUIElements();
        setUIElementValues();

        return listItemView;
    }

    private void initializeListUIElements() {
        nameTextView = listItemView.findViewById(R.id.name);
        priceTextView = listItemView.findViewById(R.id.price);
        dpsPerLevelTextView = listItemView.findViewById(R.id.dpsPerLevel);
        levelTextView = listItemView.findViewById(R.id.level);
        totalDPSTextView = listItemView.findViewById(R.id.total);
        upsTextView = listItemView.findViewById(R.id.ups);
        investmentImageView = listItemView.findViewById(R.id.investmentIcon);
        dollarImageView = listItemView.findViewById(R.id.dollarIcon);
    }

    private void setUIElementValues() {
        if (investment.isLocked()) {
            listItemView.setEnabled(false);
            nameTextView.setText(R.string.locked);
            priceTextView.setText("");
            levelTextView.setText("");
            dpsPerLevelTextView.setText("");
            totalDPSTextView.setText("");
            upsTextView.setText("");
            investmentImageView.setImageResource(R.drawable.questionmark);
            dollarImageView.setVisibility(View.INVISIBLE);
        } else {
            listItemView.setEnabled(true);
            nameTextView.setText(investment.getName());
            priceTextView.setText("Price: " + App.NF.format(investment.getPrice()));
            levelTextView.setText(String.valueOf(investment.getLevel()));
            dpsPerLevelTextView.setText("DPS: " + App.NF.format(investment.getMoneyPerSecPerLevel()));
            totalDPSTextView.setText("Total: " + App.NF.format((investment.getMoneyPerSec())) + " (" + String.format("%.2f", Game.getInstance().getDPSPercentage(investment)) + "%)");
            upsTextView.setText("UPs: " + investment.getPurchasedRelevantUpgrades().size() + "/" + investment.getRelevantUpgrades().size());
            Glide
                    .with(App.getContext())
                    .load(investment.getImageResource())
                    .asBitmap()
                    .dontAnimate()
                    .dontTransform()
                    .into(investmentImageView);
            dollarImageView.setVisibility(View.VISIBLE);
        }
        setTextColorByAvailability(investment);
    }

    private void setTextColorByAvailability(Investment investment) {
        if (investment.isLocked()) {
            nameTextView.setTextColor(App.getContext().getResources().getColor(R.color.darkerGray));//App.getContext().getResources().getColor(R.color.black));
        } else if (investment.isBuyable()) {
            nameTextView.setTextColor(Color.parseColor("#0c6f04"));
        } else {
            nameTextView.setTextColor(Color.parseColor("#760c07"));
        }
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
