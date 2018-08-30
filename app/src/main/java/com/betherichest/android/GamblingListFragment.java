package com.betherichest.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.betherichest.android.GameElements.Gambling;

import java.util.List;

public class GamblingListFragment extends Fragment {
    View rootView;
    Toast noMoneyToast = null;
    Game game = Game.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gambling_listview, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Gambling> items = game.getGamblings();

        final GamblingAdapter adapter = new GamblingAdapter(items);
        final ListView listView = rootView.findViewById(R.id.gambling_listview);
        listView.setAdapter(adapter);

        game.adapterRefreshListener = new AdapterRefreshListener() {
            @Override
            public void refreshAdapter() {
                adapter.notifyDataSetChanged();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Gambling selectedGambling = adapter.getItem(position);
                if (selectedGambling.isBuyable()) {
                    game.buyGambling(selectedGambling);
                    adapter.notifyDataSetChanged();
                } else {
                    if (noMoneyToast != null) {
                        noMoneyToast.cancel();
                    }
                    noMoneyToast =
                            Toast.makeText(
                                    getContext(),
                                    R.string.not_enough_money,
                                    Toast.LENGTH_SHORT
                            );
                    noMoneyToast.show();
                }

            }
        });
    }

}
