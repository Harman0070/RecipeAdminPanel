package com.example.harmandeepsingh.recipeadminpanel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.harmandeepsingh.recipeadminpanel.Adapters.CustomSpinnerAdapter;
import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiClient;
import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiService;
import com.example.harmandeepsingh.recipeadminpanel.Models.AddCountrysearch2Model;
import com.example.harmandeepsingh.recipeadminpanel.Models.CountryName;
import com.example.harmandeepsingh.recipeadminpanel.Models.CountryrecipeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategory extends AppCompatActivity {
    Spinner spinnerCustomSearch3;
    EditText category_search3;
    List<CountryName> countryList;
    CustomSpinnerAdapter dataAdapter1;
    ProgressDialog pDialog;
    Button saveBtn;
    String countryid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        setTitle("Add Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerCustomSearch3 =(Spinner)findViewById(R.id.spinnerCustomSearch3);
        category_search3=(EditText)findViewById(R.id.category_search3);
        countryList=new ArrayList<>();
        dataAdapter1=new CustomSpinnerAdapter(AddCategory.this,countryList);
        preparecountry();

        spinnerCustomSearch3.setAdapter(dataAdapter1);

        spinnerCustomSearch3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();
                countryid=countryList.get(position).getCountryType();
              //  Log.d("mycon",""+countryid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveBtn=(Button)findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validate(category_search3.getText().toString())) {
                    Log.d("IDsearch3",""+countryid);
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCategory.this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Are you Sure?");
                    // builder.setIcon(R.drawable.ic_launcher);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            addtodatabase(category_search3.getText().toString(),countryid);
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            }
        });
    }

    public void preparecountry(){
        pDialog=new ProgressDialog(AddCategory.this);
        pDialog.setMessage("fetching data...");
        pDialog.setCancelable(false);
        //con_id ="1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(AddCategory.this).isOnline()) {
            hidepDialog();
            Toast.makeText(AddCategory.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

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
                                dataAdapter1.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(AddCategory.this, "not success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CountryrecipeModel> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
        dataAdapter1.notifyDataSetChanged();
    }

    private boolean validate(String categorysearch3){

        if (categorysearch3.equals("") ){
            category_search3.setError("Data is null or short");
            category_search3.setFocusable(true);
            Toast.makeText(AddCategory.this, "Category Not Selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void addtodatabase(String category_name,String countryid){

        //     categoryList.clear();
        pDialog=new ProgressDialog(AddCategory.this);
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(false);
        //con_id ="1";
        showpDialog();
        //check internet state
        if (!checkInternetState.getInstance(AddCategory.this).isOnline()) {
            hidepDialog();
            Toast.makeText(AddCategory.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            ApiService service = ApiClient.getClient().create(ApiService.class);
            Call<AddCountrysearch2Model> userCall = service.getcountryid(category_name,countryid);
            userCall.enqueue(new Callback<AddCountrysearch2Model>() {
                @Override
                public void onResponse(Call<AddCountrysearch2Model> call, Response<AddCountrysearch2Model> response) {
                    hidepDialog();

                  //  Toast.makeText(AddCategory.this, ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

                        // startActivity(new Intent(AddCategory.this, Search1.class));
                      /*  FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Search1 llf = new Search1();
                        ft.replace(R.id.search3, llf);
                        int count = fm.getBackStackEntryCount();
                        for(int i = 0; i < count; ++i) {
                            fm.popBackStack();
                        }*/
                        // Toast.makeText(AddCategory.this, "Fab2", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddCategory.this, "Added Category", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddCategory.this, "not success", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<AddCountrysearch2Model> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
        // preparecountry(m);
        //ataAdapter2.notifyDataSetChanged();
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
