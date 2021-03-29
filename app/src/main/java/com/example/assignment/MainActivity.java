package com.example.assignment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
import com.example.assignment.Database.MemberDatabase;
import com.example.assignment.Model.Member;
import com.example.assignment.Utils.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://api.spacexdata.com/v4/crew";
    private MemberDatabase memberDatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "Click on load button to refresh data.", Toast.LENGTH_SHORT).show();
        memberDatabase = Room.databaseBuilder(getApplicationContext(), MemberDatabase.class, "countryDB").allowMainThreadQueries().build();

        if (haveNetworkConnection()) {
            fetchDataFromWeb();
        }

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, memberDatabase);

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
            memberDatabase.getMemberDAO().deleteAllMember();
            MyAdapter myAdapter = new MyAdapter(MainActivity.this, memberDatabase);
            recyclerView.setAdapter(myAdapter);
        }else if(item.getItemId()==R.id.refresh_menu){
            reload();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchDataFromWeb() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {



                memberDatabase.getMemberDAO().deleteAllMember();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Member member = new Member(i, obj.getString("name"), obj.getString("agency"), obj.getString("wikipedia"), obj.getString("status"), obj.getString("image"));
                        memberDatabase.getMemberDAO().addMember(member);
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


    public void reload(){
        if (haveNetworkConnection()) {
            fetchDataFromWeb();
        }

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, memberDatabase);
        recyclerView.setAdapter(myAdapter);
    }
}