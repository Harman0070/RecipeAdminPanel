package com.example.harmandeepsingh.recipeadminpanel.Interface;

import com.example.harmandeepsingh.recipeadminpanel.Models.AddCountrysearch2Model;
import com.example.harmandeepsingh.recipeadminpanel.Models.CountryrecipeModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Harmandeep singh on 11/14/2017.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("add_search2_country.php")
    Call<AddCountrysearch2Model> countryid(@Field("country_name") String country_name, @Field("category_name") String category_name);

    @FormUrlEncoded
    @POST("recipenavigationtbl.php")
    Call<CountryrecipeModel> getcountryiid(@Field("") String country_type);

    @FormUrlEncoded
    @POST("add_search3_category.php")
    Call<AddCountrysearch2Model> getcountryid(@Field("category_name") String category_name,@Field("country_type") String countryid);


    @FormUrlEncoded
    @POST("delete_country_admin.php")
    Call<CountryrecipeModel> getcountrytype(@Field("country_id") String country_type);
}
