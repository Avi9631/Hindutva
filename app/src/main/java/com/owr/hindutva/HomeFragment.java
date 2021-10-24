package com.owr.hindutva;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView homeRecyclerView;
    private HomeAdapter homeAdapter;

    public static List<HomeModel> homeList = new ArrayList<>();

    private AdView mAdView;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        mAdView = view.findViewById(R.id.adView);
        loadAds();

        linearLayout= view.findViewById(R.id.lin);

        homeRecyclerView = view.findViewById(R.id.recyl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        homeRecyclerView.setLayoutManager(layoutManager);
        homeRecyclerView.setHasFixedSize(true);

        if (homeList.size() == 0){
            Toast.makeText(getContext(), "No Internet Connection!!", Toast.LENGTH_LONG).show();
        }


        homeAdapter = new HomeAdapter(getContext(), homeList);
        homeRecyclerView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                linearLayout.setEnabled(false);
                Intent i = new Intent(getContext(), SearchActivity.class);
                startActivity(i);
                linearLayout.setEnabled(true);
                return false;
            }
        });

        return view;
    }


    private void loadAds()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


}
