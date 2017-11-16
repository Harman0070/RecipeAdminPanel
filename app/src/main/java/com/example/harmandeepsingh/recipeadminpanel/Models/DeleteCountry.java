package com.example.harmandeepsingh.recipeadminpanel.Models;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Handler;

import com.example.harmandeepsingh.recipeadminpanel.Adapters.deleteCountryAdapter;
import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiClient;
import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiService;
import com.example.harmandeepsingh.recipeadminpanel.R;
import com.example.harmandeepsingh.recipeadminpanel.checkInternetState;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCountry extends AppCompatActivity {
    ProgressDialog pDialog;
    List<CountryName> countryList;
    RecyclerView recyclerView;
    deleteCountryAdapter adaptercountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_country);
        setTitle("Delete Country");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
        doTheAutoRefresh();
    }
    private void doTheAutoRefresh() {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Write code for your refresh logic
                initialize();
                countryList.clear();
                doTheAutoRefresh();
            }
        }, 15000);
    }
    void initialize(){
        countryList=new ArrayList<>();
        adaptercountry=new deleteCountryAdapter(this,countryList);
        recyclerView=(RecyclerView)findViewById(R.id.addrecipe2_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DeleteCountry.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptercountry);
        preparecountry();
    }
    public void preparecountry(){
        pDialog=new ProgressDialog(DeleteCountry.this);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(DeleteCountry.this).isOnline()) {
            hidepDialog();
            Toast.makeText(DeleteCountry.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();
        }else {
            ApiService service = ApiClient.getClient().create(ApiService.class);
            Call<CountryrecipeModel> userCall = service.getcountryiid("");
            userCall.enqueue(new Callback<CountryrecipeModel>() {
                @Override
                public void onResponse(Call<CountryrecipeModel> call, Response<CountryrecipeModel> response) {
                    hidepDialog();
                    Log.d("spinnerresponse"," "+response.body().getSuccess());
                    if (response.isSuccessful()){
                        if (response.body().getSuccess()) {
                            for (int i = 0; i < response.body().getCountryName().size(); i++) {
                                CountryName country = new CountryName();
                                country.setCountryName(response.body().getCountryName().get(i).getCountryName());
                                country.setCountryType(response.body().getCountryName().get(i).getCountryType());
                                Log.d("spinnercountry", " " + response.body().getCountryName().get(i).getCountryName());
                                countryList.add(country);
                                adaptercountry.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(DeleteCountry.this, "not success", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<CountryrecipeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
        adaptercountry.notifyDataSetChanged();
    }

    //to show progress dialog
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    //to hide progress
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle action bar item clicks here. The action bar will
// automatically handle clicks on the Home/Up button, so long
// as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
// finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
