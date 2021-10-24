package com.owr.hindutva;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import static com.owr.hindutva.HomeFragment.homeList;

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private ImageView searchBtn;
    private RecyclerView searchRec;

    private HomeAdapter homeAdapter;

    private List<HomeModel> homeModelList= new ArrayList<>();
    int k=0;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAdView = findViewById(R.id.adView);
        loadAds();

        searchBtn= findViewById(R.id.searchBtn);
        searchText= findViewById(R.id.searchText);
        searchRec= findViewById(R.id.searchRec);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        searchRec.setLayoutManager(layoutManager);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (k==0) {
                    search(searchText.getText().toString());
                }else {
                    homeModelList.clear();
                    homeAdapter.notifyDataSetChanged();
                    search(searchText.getText().toString());
                }
            }
        });
    }

    private void search(String data) {
        if (data.equals("")){
        homeModelList.clear();
        homeAdapter.notifyDataSetChanged();
        }else {
            for (int i = 0; i < homeList.size(); i++) {
                if (homeList.get(i).getName().toLowerCase().contains(data.toLowerCase().trim())) {
                    homeModelList.add(new HomeModel(homeList.get(i).getImage(), homeList.get(i).getName(), homeList.get(i).getId(), homeList.get(i).getText()));
                    addToRec(i);
                }
            }
        }

    }

    private void addToRec(int i) {

        k=1;
        homeAdapter = new HomeAdapter(SearchActivity.this, homeModelList);
        searchRec.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }

    private void loadAds()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

}
