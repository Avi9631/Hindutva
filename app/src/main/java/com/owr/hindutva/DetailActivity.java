package com.owr.hindutva;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.owr.hindutva.HomeFragment.homeList;

public class DetailActivity extends AppCompatActivity {

    private TextView titleDetail;
    private TextView textDetail;
    private ImageView imageDetail;
    private FloatingActionButton floatingActionButton;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private List<HomeModel> bookmarkList;
    private int matchedQuestionPosition;

    public static  final String FILE_NAME = "QUIZZER";
    public static  final String KEZ_NAME = "QUESTIONS";

    private int pos;

    private InterstitialAd interstitialAd;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAdView = findViewById(R.id.adView);
        loadAds();

        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_id_hindutava));
        interstitialAd.loadAd(new AdRequest.Builder().build());

        textDetail= findViewById(R.id.textDetail);
        titleDetail= findViewById(R.id.titleDetail);
        imageDetail = findViewById(R.id.imageDetail);
        floatingActionButton= findViewById(R.id.floatingActionButton);

        pos= getIntent().getIntExtra("position", -1);
        titleDetail.setText(homeList.get(pos).getName());
        Glide.with(DetailActivity.this).load(homeList.get(pos).getImage()).into(imageDetail);
        textDetail.setText(homeList.get(pos).getText());

        preferences= getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson= new Gson();

        getBookmarks();
        if (modelMatch())
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                floatingActionButton.setImageDrawable(getDrawable(R.drawable.tick));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                floatingActionButton.setImageDrawable(getDrawable(R.drawable.bookmark));
            }
        }


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (interstitialAd.isLoaded()){
                    interstitialAd.show();
                }
                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        if (modelMatch())
                        {
                            bookmarkList.remove(matchedQuestionPosition);
                            Toast.makeText(DetailActivity.this, "Saved for offline reading.", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                floatingActionButton.setImageDrawable(getDrawable(R.drawable.bookmark));
                            }
                        }
                        else
                        {
                            bookmarkList.add(homeList.get(pos));
                            Toast.makeText(DetailActivity.this, "Removed from offline reading.", Toast.LENGTH_SHORT).show();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                floatingActionButton.setImageDrawable(getDrawable(R.drawable.tick));
                            }
                        }
                    }
                });

                if (modelMatch())
                {
                    bookmarkList.remove(matchedQuestionPosition);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        floatingActionButton.setImageDrawable(getDrawable(R.drawable.bookmark));
                    }
                }
                else
                {
                    bookmarkList.add(homeList.get(pos));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        floatingActionButton.setImageDrawable(getDrawable(R.drawable.tick));
                    }
                }
            }
        });

    }

    private void getBookmarks(){

        String json= preferences.getString(KEZ_NAME, "");

        Type type = new TypeToken<List<HomeModel>>(){}.getType();

        bookmarkList = gson.fromJson(json, type);
        if (bookmarkList == null)
        {
            bookmarkList= new ArrayList<>();
        }
    }

    private boolean modelMatch(){
        boolean matched = false;
        int i=0;
        for (HomeModel model : bookmarkList)
        {
            if (model.getName().equals(HomeFragment.homeList.get(pos).getName())
                    && model.getId().equals(HomeFragment.homeList.get(pos).getId())
                    && model.getImage().equals(HomeFragment.homeList.get(pos).getImage())
                    && model.getText().equals(HomeFragment.homeList.get(pos).getText()))
            {
                matched= true;
                matchedQuestionPosition= i;
            }
            i++;
        }
        return  matched;
    }

    private void storeBookmarks()
    {
        String json = gson.toJson(bookmarkList);
        editor.putString(KEZ_NAME, json);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
        ads();
    }


    private void ads(){
        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                finish();
            }
        });
    }

    private void loadAds()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

}
