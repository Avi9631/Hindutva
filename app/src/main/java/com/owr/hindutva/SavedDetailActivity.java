package com.owr.hindutva;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.owr.hindutva.SavedFragment.bookmarkList;

public class SavedDetailActivity extends AppCompatActivity {

    private TextView titleDetail;
    private TextView textDetail;
    private ImageView imageDetail;
    private FloatingActionButton floatingActionButton;

    private int pos;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAdView = findViewById(R.id.adView);
        loadAds();


        textDetail= findViewById(R.id.textDetail);
        titleDetail= findViewById(R.id.titleDetail);
        imageDetail = findViewById(R.id.imageDetail);
        floatingActionButton= findViewById(R.id.floatingActionButton);
        floatingActionButton.setVisibility(View.GONE);

        pos= getIntent().getIntExtra("position", -1);
        titleDetail.setText(bookmarkList.get(pos).getName());
//        id = homeList.get(pos).getId();
        Glide.with(SavedDetailActivity.this).load(bookmarkList.get(pos).getImage()).into(imageDetail);

        textDetail.setText(bookmarkList.get(pos).getText());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    private void loadAds()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


}
