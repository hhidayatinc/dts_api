package com.example.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.profile.adapter.ProfileAdapter;
import com.example.profile.model.Profile;
import com.example.profile.model.ResponseData;
import com.example.profile.service.ApiClient;
import com.example.profile.service.ApiProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ProfileAdapter contactAdapter;
    private RecyclerView rvShowProfile;
    private ProgressDialog progressDialog;
    private List<Profile> dataProfile = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openInput = new Intent(getApplicationContext(), InputActivity.class);
                openInput.putExtra("OPERATION", "insert");
                startActivity(openInput);
            }
        });
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Memuat data...");

        rvShowProfile = findViewById(R.id.rv_tampil);

        showProfileData();
    }

    private void showProfileData() {
        progressDialog.show();
        ApiProfile api = ApiClient.getRetrofitInstance().create(ApiProfile.class);
        Call<ResponseData> call = api.getData();
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                String value = response.body().getValue();

                if(value.equals("1")){
                    dataProfile = response.body().getResult();
                    ProfileAdapter profileAdapter = new ProfileAdapter(dataProfile, getApplicationContext());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    rvShowProfile.setLayoutManager(layoutManager);
                    rvShowProfile.setAdapter(profileAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }

   @ Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

//        final MenuItem searchData = menu.findItem(R.id.menu_item_search);
//        final SearchView searchView = (SearchView)searchData.getActionView();
//        searchView.setQueryHint("Searching Contact");
//        searchView.setIconified(true);
//        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProfileData();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.equals("")) {
            showProfileData();
        }
//        else {
//            searchProfile(s);
//        }
        return false;
    }

//    private void searchProfile(String s) {
//        ApiProfile api = ApiClient.getRetrofitInstance().create(ApiProfile.class);
//        Call<ResponseData> call = api.searchData(s);
//        call.enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                String value = response.body().getValue();
//
//                if(value.equals("1")) {
//                    dataProfile = response.body().getResult();
//                    contactAdapter = new ProfileAdapter(dataProfile, getApplicationContext());
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//                    rvShowProfile.setLayoutManager(layoutManager);
//                    rvShowProfile.setAdapter(contactAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//
//            }
//        });
//    }
}