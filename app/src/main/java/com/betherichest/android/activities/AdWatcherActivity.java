package com.betherichest.android.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.text.NumberFormat;
import java.util.Locale;

public class AdWatcherActivity extends AppCompatActivity implements RewardedVideoAdListener {
    private static final double AD_REWARD_MULTIPLIER = 300;
    private RewardedVideoAd mRewardedVideoAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
    Game game = Game.getInstance();
    double rewardMoney;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_watcher);

        MobileAds.initialize(this, AD_UNIT_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(AD_UNIT_ID,
                new AdRequest.Builder().build());
    }

    @Override
    public void onRewarded(RewardItem reward) {
        rewardMoney = game.getMoneyPerSec() * AD_REWARD_MULTIPLIER;
        game.earnMoney(rewardMoney);
    }

    private void showRewardDialog(double rewardMoney) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Video reward")
                .setMessage("You received " + String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format(rewardMoney)) + "$")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                })
                .show();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
        showRewardDialog(rewardMoney);
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        mRewardedVideoAd.show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoCompleted() {
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        Game.setTimerPaused(false);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        Game.setTimerPaused(true);
        super.onPause();
    }

    @Override
    protected void onStop() {
        DatabaseManager.instance.saveStateToDb();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        GUIManager.setActivityOpened(false);
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}
