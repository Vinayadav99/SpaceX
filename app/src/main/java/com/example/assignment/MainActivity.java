package com.example.assignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment.Database.CountryDatabase;
import com.example.assignment.Model.Country;
import com.example.assignment.Utils.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://restcountries.eu/rest/v2/region/asia";
    private CountryDatabase countryDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countryDatabase = Room.databaseBuilder(getApplicationContext(), CountryDatabase.class, "countryDB").allowMainThreadQueries().build();

        if (haveNetworkConnection()) {
            fetchDataFromWeb();
//            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
            Log.d("DEBUG", "Fetching from web");
//            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.delete_menu){
            countryDatabase.getCountryDAO().deleteAllCountry();
            MyAdapter myAdapter = new MyAdapter(getApplicationContext(),countryDatabase);
            recyclerView.setAdapter(myAdapter);
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchDataFromWeb() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                countryDatabase.getCountryDAO().deleteAllCountry();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Country country = new Country(jsonObject.getString("name"),jsonObject.getString("capital"),jsonObject.getString("region"),jsonObject.getString("subregion"),jsonObject.getLong("population"));
                        JSONArray borderArray = jsonObject.getJSONArray("borders");
                        List<String> borderList = new ArrayList<>();
                        for(int j=0;j<borderArray.length();j++){
                            borderList.add(borderArray.get(j).toString());
                        }
                        country.setBorders(borderList);

                        List<String> languageList = new ArrayList<>();

                        JSONArray languages = jsonObject.getJSONArray("languages");
                        for (int j=0;j<languages.length();j++){
                            JSONObject lang = languages.getJSONObject(j);
                            languageList.add(lang.getString("name"));
                        }

                        country.setLanguages(languageList);

                        country.setImage(jsonObject.getString("flag"));

                        countryDatabase.getCountryDAO().addCountry(country);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error",error.getMessage());
            }
        });
        queue.add(arrayRequest);
    }

}