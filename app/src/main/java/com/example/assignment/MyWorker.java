package com.example.assignment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.assignment.Database.CountryDatabase;

public class MyWorker extends Worker {


    Context context;
    private CountryDatabase countryDatabase;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

        this.context = context;
        countryDatabase = Room.databaseBuilder(getApplicationContext(),CountryDatabase.class,"countryDB").allowMainThreadQueries().build();

    }

    @NonNull
    @Override
    public Result doWork() {
        return Result.success();
    }



}
