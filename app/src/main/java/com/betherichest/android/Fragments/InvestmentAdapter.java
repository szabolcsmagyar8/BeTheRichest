package com.betherichest.android.Fragments;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.GameElements.Investment;
import com.betherichest.android.Mangers.Game;
import com.betherichest.android.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class InvestmentAdapter extends BaseAdapter {
    View listItemView;
    private List<Investment> items;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView dpsPerLevelTextView;
    private TextView levelTextView;
    private TextView totalDPSTextView;
    private ImageView investmentImageView;
    private ImageView purchasedUpgradeImageView;
    private RelativeLayout upgradeIconContainer;
    Investment investment;

    NumberFormat nf = NumberFormat.getNumberInstance(Locale.FRANCE);

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
            listItemView = View.inflate(parent.getContext(), R.layout.investment_listitem, null);
        } else {
            listItemView = view;
        }

        investment = items.get(position);
        initializeListUIElements();

        setUIElementValues(investment);

        return listItemView;
    }

    private void initializeListUIElements() {
        nameTextView = listItemView.findViewById(R.id.name);
        priceTextView = listItemView.findViewById(R.id.price);
        dpsPerLevelTextView = listItemView.findViewById(R.id.dpsPerLevel);
        levelTextView = listItemView.findViewById(R.id.level);
        totalDPSTextView = listItemView.findViewById(R.id.total);
        investmentImageView = listItemView.findViewById(R.id.investmentIcon);
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
        if (investment.isLocked()) {
            listItemView.setEnabled(false);
            nameTextView.setText(R.string.locked);
            priceTextView.setText("");
            levelTextView.setText("");
            dpsPerLevelTextView.setText("");
            totalDPSTextView.setText("");
            investmentImageView.setImageResource(R.drawable.questionmark);
        } else {
            listItemView.setEnabled(true);
            nameTextView.setText(investment.getName());
            priceTextView.setText("Price: " + nf.format(investment.getPrice()) + " $");
            levelTextView.setText(String.valueOf(investment.getLevel()));
            dpsPerLevelTextView.setText("DPS: " + String.valueOf(nf.format(investment.getMoneyPerSecPerLevel())));
            totalDPSTextView.setText("Total: " + String.valueOf(nf.format((investment.getMoneyPerSec())) + " (" + String.format("%.2f", Game.getInstance().getDPSPercentage(investment)) + "%)"));
            Glide
                    .with(App.getContext())
                    .load(investment.getImageResource())
                    .asBitmap()
                    .dontAnimate()
                    .dontTransform()
                    .into(investmentImageView);
            investmentImageView.setImageResource(investment.getImageResource());
            //      purchasedUpgradeImageView.setImageResource(investment.getImageResource());
            //      showPurchasedUpgrades(investment);
        }
        setTextColorByAvailability(investment);
    }

    private void setTextColorByAvailability(Investment investment) {
        if (investment.isLocked()){
            nameTextView.setTextColor(App.getContext().getResources().getColor(android.R.color.secondary_text_light));//App.getContext().getResources().getColor(R.color.black));
        }
        else if (investment.isBuyable()) {
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
