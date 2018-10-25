package com.betherichest.android.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.betherichest.android.R;
import com.betherichest.android.database.DatabaseManager;
import com.betherichest.android.fragments.BoostersAdapter;
import com.betherichest.android.gameElements.Booster;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.util.IabHelper;
import com.betherichest.android.util.IabResult;
import com.betherichest.android.util.Inventory;
import com.betherichest.android.util.Purchase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BoostersActivity extends AppCompatActivity {
    private IabHelper mHelper;
    private static final String TAG = "com.betherichest.android.inappbilling";
    private String selectedSKU = "";
    private BoostersAdapter adapter;
    List<Booster> boosters = new ArrayList<>();
    private Game game = Game.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boosters);

        boosters = game.getBoosters();
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setAdapter(null);
    }

    private List<String> getAllSkus() {
        List<String> allSkus = new ArrayList<>();
        for (Booster booster : Game.getInstance().getBoosters()) {
            allSkus.add(booster.getSkuId());
        }
        return allSkus;
    }

    @Override
    protected void onStart() {
        super.onStart();
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnxBVvsqKfpGkYNjxSiBxIY2DfD2j13vpIkIAMLJz/r/I/WfT6nUkGoFpMFWK/EfIW0x9csn" +
                "vfqCJxJJvCDXpcDeYj/Uvl+vvAbireR3Rv1l50vOS5fngqmzVc9Y3xgTHzjByc3s+8wcquneUFOOagnKA2Rrgx21Vy//og58O7GEdDcq3RYqZ63wBaucn/JyJ7YMf/1aYB8c1yhvj6p" +
                "q1n3hnsG4TE69KETwQPsujchGZCyKfGiX+5r1HiLexAvOuscVz0eR3mWUjNadCSztbd7INgmu+JITdVo1wrBJIhj2RQrlqmNH3zYlseXQCyw3lqLjverroyjA8y0J+sOSwmQIDAQAB";

        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.enableDebugLogging(true, "TAG");

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @SuppressLint("LongLogTag")
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                    return;
                }

                Log.d(TAG, "In-app Billing is set up OK");
                try {
                    mHelper.queryInventoryAsync(true, getAllSkus(), null, mReceivedInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Game.setTimerPaused(false);
    }

    @Override
    protected void onPause() {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                GUIManager.setActivityOpened(false);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                GUIManager.showToast(R.string.purchase_cancelled);
            } else if (purchase.getSku().equals(selectedSKU)) {
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);

                    double moneyReceived = game.getMoneyPerSec() * Game.SEC_TO_HOUR_MULTIPLIER * game.getBoosterBySkuId(selectedSKU).getInterval();
                    game.earnMoney(moneyReceived);
                    showAlertDialog(moneyReceived);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            if (result.isFailure()) {
                return;
            }

            setAdapter(inventory);

            Purchase purchase = inventory.getPurchase(selectedSKU);
            if (purchase != null) {
                try {
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            if (result.isSuccess()) {
                GUIManager.showToast(R.string.purchase_successful);
            } else {
                GUIManager.showToast(R.string.purchase_failed);
            }
        }
    };

    private void setAdapter(Inventory inventory) {
        adapter = new BoostersAdapter(boosters, inventory);
        GridView listView = findViewById(R.id.timeWarpGridview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Booster booster = adapter.getItem(i);
                    selectedSKU = booster.getSkuId();
                    mHelper.launchPurchaseFlow(BoostersActivity.this, selectedSKU, 101, mPurchaseFinishedListener, "mypurchasetoken");
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlertDialog(double moneyReceived) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Thanks for purchase!")
                .setMessage("You received " + String.valueOf(NumberFormat.getNumberInstance(Locale.FRANCE).format(moneyReceived)) + "$")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes button clicked, do something
                    }
                })
                .show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) {
            try {
                mHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
        }
        mHelper = null;
    }
}