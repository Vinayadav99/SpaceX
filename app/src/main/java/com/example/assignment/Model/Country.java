package com.example.assignment.Model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "country")
public class Country {

    @PrimaryKey
    @ColumnInfo(name = "country_name")
    @NonNull
    private String name;

    @ColumnInfo(name = "country_capital")
    private String capital;

    @ColumnInfo(name = "country_region")
    private String region;

    @ColumnInfo(name = "country_subRegion")
    private String subRegion;

    @ColumnInfo(name = "country_population")
    private long population;

    private List<String> borders;

    private List<String> languages;

    private String image;

    @Ignore
    public Country(){

    }

    public Country(String name,String capital,String region,String subRegion,long population){
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subRegion = subRegion;
        this.population = population;

    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
