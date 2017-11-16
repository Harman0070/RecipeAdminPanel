package com.example.harmandeepsingh.recipeadminpanel;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiClient;
import com.example.harmandeepsingh.recipeadminpanel.Interface.ApiService;
import com.example.harmandeepsingh.recipeadminpanel.Models.AddCountrysearch2Model;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCountry extends AppCompatActivity {
    EditText country_search2,category_search2;
    Button saveBtn;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_country);
        setTitle("Add Country");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        country_search2=(EditText)findViewById(R.id.country_search2);
        category_search2=(EditText)findViewById(R.id.category_search2);


        saveBtn=(Button)findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   countrysearch2 = country_search2.getText().toString();
                // categorysearch2 = category_search2.getText().toString();
                if(validate(country_search2.getText().toString(),category_search2.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddCountry.this);
                    builder.setTitle(R.string.app_name);
                    builder.setMessage("Are you Sure?");
                    // builder.setIcon(R.drawable.ic_launcher);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            addtodatabase(country_search2.getText().toString(),category_search2.getText().toString());
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

    private boolean validate(String countrysearch2,String categorysearch2){
        if (countrysearch2.equals("") ){
            // country_search2.setError("Data is null or short");
            // country_search2.setFocusable(true);
            Toast.makeText(AddCountry.this, "Country Not Selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (categorysearch2.equals("") ){
            category_search2.setError("Data is null or short");
            category_search2.setFocusable(true);
            Toast.makeText(AddCountry.this, "Category Not Selected", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void addtodatabase(String country_name,String category_name){

        pDialog=new ProgressDialog(AddCountry.this);
        pDialog.setMessage("Adding data...");
        pDialog.setCancelable(false);
        showpDialog();

        //check internet state
        if (!checkInternetState.getInstance(AddCountry.this).isOnline()) {
            hidepDialog();
            Toast.makeText(AddCountry.this, "Please Check Your Internet Connection.", Toast.LENGTH_LONG).show();

        }else {

            ApiService service = ApiClient.getClient().create(ApiService.class);
            Call<AddCountrysearch2Model> userCall = service.countryid(country_name,category_name);
            userCall.enqueue(new Callback<AddCountrysearch2Model>() {
                @Override
                public void onResponse(Call<AddCountrysearch2Model> call, Response<AddCountrysearch2Model> response) {
                    hidepDialog();
                    // Log.d("spinnerresponse"," "+response.body().getSuccess());
                    Toast.makeText(AddCountry.this, ""+response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                    if (response.isSuccessful()) {

                        // startActivity(new Intent(getActivity(), Search1.class));
                 /*       FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Search1 llf = new Search1();
                        ft.replace(R.id.search2, llf);
                        int count = fm.getBackStackEntryCount();
                        for(int i = 0; i < count; ++i) {
                            fm.popBackStack();
                        }
                        ft.commit();*/
                        Toast.makeText(AddCountry.this, "Country Added", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(AddCountry.this, "not success", Toast.LENGTH_SHORT).show();
                    }

                }

                private void finish() {
                }

                @Override
                public void onFailure(Call<AddCountrysearch2Model> call, Throwable t) {
                    //hidepDialog();
                    Log.d("onFailure", t.toString());
                }
            });

        }
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
