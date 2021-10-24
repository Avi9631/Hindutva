package com.owr.hindutva;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.owr.hindutva.DetailActivity.FILE_NAME;
import static com.owr.hindutva.DetailActivity.KEZ_NAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedFragment extends Fragment {


    public SavedFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public static List<HomeModel> bookmarkList;
    public static SaveAdapter adapter;

    public static TextView notEmpty;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        mAdView = view.findViewById(R.id.adView);
        loadAds();

        recyclerView= view.findViewById(R.id.saverec);


        preferences= getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson= new Gson();

        getBookmarks();

        notEmpty= view.findViewById(R.id.notsaved);

        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter= new SaveAdapter(getContext(),bookmarkList);
        recyclerView.setAdapter(adapter);
        if(adapter.getItemCount() == 0){
            notEmpty.setVisibility(View.VISIBLE);
        }else{
            notEmpty.setVisibility(View.GONE);
        }

        return view;
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


    private void storeBookmarks()
    {
        String json = gson.toJson(bookmarkList);
        editor.putString(KEZ_NAME, json);
        editor.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        storeBookmarks();
    }

    private void loadAds()
    {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

}
