package com.example.assignment.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.assignment.Model.Country;

@Database(entities = {Country.class},version = 1)
@TypeConverters(Converter.class)
public abstract class CountryDatabase extends RoomDatabase {

    public abstract CountryDAO getCountryDAO();

}
