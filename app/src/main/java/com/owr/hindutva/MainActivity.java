package com.owr.hindutva;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import static com.owr.hindutva.HomeFragment.homeList;

public class MainActivity extends AppCompatActivity {

    private Dialog loadingDialog;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(MainActivity.this);

        loadingDialog = new Dialog(MainActivity.this);
        loadingDialog.setContentView(R.layout.loading);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.circle));
        }
        loadingDialog.getWindow().setLayout(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        loadingDialog.show();

        if (getConnectivityStatusString(MainActivity.this) == "No internet is available" ||
                getConnectivityStatusString(MainActivity.this) == null){
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
            loadingDialog.dismiss();

        }else {
            myRef.child("Home").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        homeList.add(new HomeModel(String.valueOf(snapshot.child("image").getValue()),
                                String.valueOf(snapshot.child("name").getValue()),
                                String.valueOf(snapshot.getKey()),
                                String.valueOf(snapshot.child("aarti").getValue())));

                    }
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                    loadingDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    loadingDialog.dismiss();

                }
            });
        }

//        homeList.add(new HomeModel(R.drawable.background, "Laxmi Mata Aarti", Aarti.laxmiAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Kunjbihari aarti",Aarti.kunjBihari));
//        homeList.add(new HomeModel(R.drawable.background, "Ganesh ji ki Aarti", Aarti.ganeshAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Hanuman Chalisa", Aarti.hanumanChalisa));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Balaji Aarti", Aarti.balajiAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Ram Chandra Ji Aarti", Aarti.ramchandrajiAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Raghuvar Ram Chandra Aarti", Aarti.raghuvarRamAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Gayatri Mantra", Aarti.gayatriAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Ambe Gauri Aarti", Aarti.ambegauriAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Khatu Shyam Ji Ki Aarti", Aarti.khatushyamAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Sai Baba Aarti", Aarti.saiBabaAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Saraswati Mata Aarti", Aarti.saraswatiMataAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Satnarayan Ji Ki Aarti", Aarti.satnarayanAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Hanuman Ji Ki Aarti", Aarti.hanumanAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shani Dev Aarti", Aarti.shanidevAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Krishna Aarti", Aarti.krishnaAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Tulsi Mata Aarti", Aarti.tulsiMataAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Surya Dev Aarti", Aarti.suryadevAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Vaishno Mata Aarti", Aarti.vaishnodeviAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shitla Mata Aarti", Aarti.shitlaMataAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Chitragupta Ji ki Aarti", Aarti.chitraguptAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Shyam Ji Ki Aarti", Aarti.shyamAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Shree Janaki Natth Ji Ki Aarti", Aarti.janakinaathAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Brihaspati Ji Ki Aarti", Aarti.brihaspatidevjiAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Santoshi Mata Aarti", Aarti.santoshiMataAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Baba GorakhNaath Ji Ki Aarti", Aarti.babagorakhnaathAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Ekadashi Mata Aarti", Aarti.ekadashiMataAarti));
//        homeList.add(new HomeModel(R.drawable.background, "Ganga Mata Ji Ki Aarti", Aarti.gangamataAarti));

    }

    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            return status;
        }
        return status;
    }

}
