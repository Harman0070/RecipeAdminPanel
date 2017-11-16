package com.example.harmandeepsingh.recipeadminpanel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.harmandeepsingh.recipeadminpanel.Models.DeleteCountry;

public class MainActivity extends AppCompatActivity {

    TextView countrybt,categorybt,deleteCountryBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countrybt=(TextView)findViewById(R.id.countrybt);
        countrybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddCountry.class));
            }
        });

        categorybt=(TextView)findViewById(R.id.categorybt);
        categorybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddCategory.class));
            }
        });

        deleteCountryBtn=(TextView)findViewById(R.id.deleteCountryBtn);
        deleteCountryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DeleteCountry.class));
            }
        });
    }
}
