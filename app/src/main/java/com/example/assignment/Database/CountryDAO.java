package com.example.assignment.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.assignment.Model.Country;
import java.util.List;

@Dao
public interface CountryDAO {

    @Insert
    public void addCountry(Country country);

    @Update
    public void updateCountry(Country country);

    @Delete
    public void deleteCountry(Country country);

    @Query("select * from country")
    public List<Country> getAllCountry();

    @Query("delete from country")
    public void deleteAllCountry();

}
