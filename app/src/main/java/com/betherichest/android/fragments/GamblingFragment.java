package com.betherichest.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.betherichest.android.App;
import com.betherichest.android.R;
import com.betherichest.android.connection.ActionType;
import com.betherichest.android.connection.RequestParam;
import com.betherichest.android.gameElements.Gambling;
import com.betherichest.android.mangers.GUIManager;
import com.betherichest.android.mangers.Game;
import com.betherichest.android.mangers.SoundManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GamblingFragment extends Fragment {
    private View rootView;
    private TextView wonMoneyText;
    private ImageView rotatingImage;
    private ImageView closeImageView;
    private GamblingAdapter adapter;
    private ListView listView;
    private Game game = Game.getInstance();

    private Animation growAndRotate, grow, fade;

    private int index = -1;

    private Runnable gamblingPriceWatch = new Runnable() {
        @Override
        public void run() {
            double act = game.getCurrentMoney();
            List<Double> gamblingPrices = new ArrayList<>();
            for (Gambling gambling : game.getGamblings()) {
                gamblingPrices.add(gambling.getPrice());
            }
            gamblingPrices.add(act);
            Collections.sort(gamblingPrices, new Comparator<Double>() {
                @Override
                public int compare(Double d1, Double d2) {
                    return Double.compare(d1, d2);

                }
            });
            setIndex(gamblingPrices.lastIndexOf(act));

            game.handler.postDelayed(gamblingPriceWatch, 100);
        }
    };

    public void setIndex(int index) {
        if (this.index != index) {
            this.index = index;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        growAndRotate = AnimationUtils.loadAnimation(getContext(), R.anim.grow_and_rotate);
        grow = AnimationUtils.loadAnimation(getContext(), R.anim.grow_gambling_wintext);
        fade = AnimationUtils.loadAnimation(getContext(), R.anim.fade);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_gambling, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Gambling> items = game.getGamblings();

        adapter = new GamblingAdapter(items);
        listView = rootView.findViewById(R.id.gambling_listview);
        listView.setAdapter(adapter);

        wonMoneyText = rootView.findViewById(R.id.wonMoneyText);
        rotatingImage = rootView.findViewById(R.id.rotatingImage);
        closeImageView = rootView.findViewById(R.id.closeIcon);

        setListeners();
        game.handler.postDelayed(gamblingPriceWatch, 100);
    }

    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!Game.isGamblingAnimationRunning()) {
                    Gambling selectedGambling = adapter.getItem(position);
                    if (selectedGambling.isBuyable()) {
                        game.buyGambling(selectedGambling);

                        rotatingImage.setBackgroundResource(selectedGambling.getImageResource());
                        rotatingImage.startAnimation(growAndRotate);
                        setAnimationListeners(position);

                    } else {
                        GUIManager.showToast(R.string.not_enough_money);
                    }
                } else {
                    return;
                }
            }
        });
    }

    private void setAnimationListeners(final int position) {
        final Gambling selectedGambling = adapter.getItem(position);
        growAndRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                rotatingImage.setVisibility(View.VISIBLE);
                closeImageView.setImageResource(R.drawable.close_gray_flat);
                Game.setGamblingAnimationRunning(true);
                SoundManager.playSound(SoundManager.soundGambling);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final double wonMoney = calculateWonMoney(selectedGambling);
                String text;
                if (wonMoney == 0) {
                    text = getString(R.string.gambling_no_win);
                    wonMoneyText.setTextColor(getResources().getColor(R.color.red));
                    Game.statisticsManager.gamblingLose();
                } else {
                    text = "You won " + App.NF.format(wonMoney) + "$";
                    game.earnMoney(wonMoney);
                    wonMoneyText.setTextColor(getResources().getColor(R.color.orange));
                    Game.statisticsManager.gamblingWin(wonMoney);
                }
                wonMoneyText.setVisibility(View.VISIBLE);
                wonMoneyText.setText(text);
                wonMoneyText.startAnimation(grow);

                List<RequestParam> params = new LinkedList<>();
                params.add(new RequestParam("gamblingId", String.valueOf(selectedGambling.getId())));
                params.add(new RequestParam("gamblingWon", String.valueOf(wonMoney > 0)));
                App.createConnection("/muser/log-gambling", params, ActionType.LOG);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        grow.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rotatingImage.startAnimation(fade);
                wonMoneyText.startAnimation(fade);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rotatingImage.setVisibility(View.GONE);
                wonMoneyText.setVisibility(View.GONE);
                closeImageView.setImageResource(R.drawable.close_flat);
                Game.setGamblingAnimationRunning(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private double calculateWonMoney(Gambling gambling) {
        Random rnd = new Random();
        if (rnd.nextInt(10000) < gambling.getChance() * 100) {
            double minValue = gambling.getMinWinAmount();
            double maxValue = gambling.getMaxWinAmount();
            return Math.floor(minValue + (maxValue - minValue) * rnd.nextDouble());
        } else {
            return 0;
        }
    }

}
