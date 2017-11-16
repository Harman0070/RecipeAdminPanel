package com.example.harmandeepsingh.recipeadminpanel.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Harmandeep singh on 11/14/2017.
 */

public class CountryName {
    @SerializedName("country_type")
    @Expose
    private String countryType;
    @SerializedName("country_name")
    @Expose
    private String countryName;

    /**
     * No args constructor for use in serialization
     *
     */
    public CountryName() {
    }

    /**
     *
     * @param countryName
     * @param countryType
     */
    public CountryName(String countryType, String countryName) {
        super();
        this.countryType = countryType;
        this.countryName = countryName;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
