package com.example.assignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.assignment.Database.CountryDatabase;
import com.example.assignment.Utils.MyAdapter;

public class MainActivity extends AppCompatActivity {


    private CountryDatabase countryDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryDatabase = Room.databaseBuilder(getApplicationContext(), CountryDatabase.class, "countryDB").allowMainThreadQueries().build();

        if (haveNetworkConnection()) {
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
            Log.d("DEBUG", "Fetching from web");
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
        }


        Log.d("DEBUG", "Size: " + countryDatabase.getCountryDAO().getAllCountry().size());


        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), countryDatabase);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(myAdapter);


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}