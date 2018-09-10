package com.betherichest.android.Fragments;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.GameElements.Upgrade;
import com.betherichest.android.R;
import com.bumptech.glide.Glide;

/**
 * Created by Szabi on 2018. 03. 14..
 */

public class InvestmentAdapter extends BaseAdapter {
    View listItemView;
    private List<Investment> items;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView dpsPerRankTextView;
    private TextView rankTextView;
    private TextView totalDPSTextView;
    private ImageView upgradeImageView;
    private ImageView purchasedUpgradeImageView;
    private RelativeLayout upgradeIconContainer;

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        listItemView = view;

        if (listItemView == null) {
            listItemView = View.inflate(parent.getContext(), R.layout.investment_listitem, null);
        } else {
            listItemView = view;
        }

        initializeListUIElements(listItemView);

        Investment investment = items.get(position);
        Glide
                .with(parent.getContext())
                .load(investment.getImageResource())
                .asBitmap()
                .dontAnimate()
                .dontTransform()
                .into(upgradeImageView);

        setUIElementValues(investment);

        return listItemView;
    }

    private void initializeListUIElements(View listItemView) {
        nameTextView = listItemView.findViewById(R.id.name);
        priceTextView = listItemView.findViewById(R.id.price);
        dpsPerRankTextView = listItemView.findViewById(R.id.dpsPerRank);
        rankTextView = listItemView.findViewById(R.id.rank);
        totalDPSTextView = listItemView.findViewById(R.id.total);
        upgradeImageView = listItemView.findViewById(R.id.upgradeIcon);
        //purchasedUpgradeImageView = listItemView.findViewById(R.id.purchasedUpgrade);
        upgradeIconContainer = listItemView.findViewById(R.id.upgradeIconContainer);
    }

    private void showPurchasedUpgrades(Investment investment) {
//        List<Upgrade> relevantUpgrades = investment.getRelevantUpgrades();
//        for (int i = 0; i < relevantUpgrades.size(); i++) {
//            purchasedUpgradeImageView.setImageResource(investment.getImageResource());
//            purchasedUpgradeImageView.setId(i);
//
//            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) purchasedUpgradeImageView.getLayoutParams();
//            if (i != 0) {
//                lp.addRule(RelativeLayout.LEFT_OF, i - 1);
//            }
//            purchasedUpgradeImageView.setLayoutParams(lp);
////            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
////            lp.setMarginEnd(10);
//            //  lp.addRule(RelativeLayout.LEFT_OF, imageView.getId());
//            //imageView.setLayoutParams(lp);
//            //upgradeIconContainer.addView(imageView);
//            //  }
//            upgradeIconContainer.addView(purchasedUpgradeImageView);

        //  }

//        ImageView image;
//        List<Upgrade> relevantUpgrades = investment.getRelevantUpgrades();
//        for (int i = 0; i < relevantUpgrades.size(); i++) {
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT,
//                    RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            image = new ImageView(listItemView.getContext());
//            image.setLayoutParams(params);
//
//            params.height = convertPxToDp(20);
//            params.width = convertPxToDp(20);
//            params.rightMargin = convertPxToDp(5);
//
//            image.setImageResource(relevantUpgrades.get(i).getImageResource());
//            upgradeIconContainer.addView(image);
//        }
    }

    private void setUIElementValues(Investment investment) {
        nameTextView.setText(investment.getName());
        priceTextView.setText(nf.format(investment.getPrice()));
        rankTextView.setText(String.valueOf(investment.getRank()));
        dpsPerRankTextView.setText("DPS: " + String.valueOf(nf.format(investment.getMoneyPerSecPerRank())));
        totalDPSTextView.setText("Total: " + String.valueOf(nf.format((investment.getMoneyPerSec())))); //+ " (" + String.format("%.2f", investment.getDPSPercentage()) + "%)"));
        upgradeImageView.setImageResource(investment.getImageResource());
//        purchasedUpgradeImageView.setImageResource(investment.getImageResource());
        setTextColorByAvailability(investment);
        showPurchasedUpgrades(investment);
    }

    private void setTextColorByAvailability(Investment investment) {
        if (investment.isBuyable()) {
            nameTextView.setTextColor(Color.parseColor("#0c6f04"));
        } else {
            nameTextView.setTextColor(Color.parseColor("#760c07"));
        }
    }

    private int convertPxToDp(int px) {
        float density = listItemView.getContext().getResources().getDisplayMetrics().density;
        return (int) (px * density);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}