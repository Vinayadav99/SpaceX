package com.example.assignment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.assignment.Database.CountryDatabase;
import com.example.assignment.Model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyWorker extends Worker {

    public static final String URL = "https://restcountries.eu/rest/v2/region/asia";
    Context context;
    private CountryDatabase countryDatabase;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
        countryDatabase = Room.databaseBuilder(getApplicationContext(),CountryDatabase.class,"countryDB").allowMainThreadQueries().build();

    }

    @NonNull
    @Override
    public Result doWork() {
        fetchDataFromWeb();
        return Result.success();
    }

    private void fetchDataFromWeb() {

        RequestQueue queue = Volley.newRequestQueue(context);

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
