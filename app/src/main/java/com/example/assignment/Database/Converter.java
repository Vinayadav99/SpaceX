package com.example.assignment.Database;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public List<String> stringToList(String string){

        String[] arr = string.split(",");
        List<String> list = new ArrayList<>();
        for (String s:arr){
            if(!s.isEmpty()){
                list.add(s);
            }
        }
        return list;
    }

    @TypeConverter
    public String listToString(List<String> list){
        StringBuilder string = new StringBuilder();

        for (String s : list) {
            string.append(",").append(s);
        }
        return string.toString();
    }


//    @TypeConverter
//    public byte[] fromBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        return outputStream.toByteArray();
//
//    }
//
//    @TypeConverter
//    public Bitmap fromByteArray(byte[] arr){
//        return BitmapFactory.decodeByteArray(arr,0,arr.length);
//    }


}
