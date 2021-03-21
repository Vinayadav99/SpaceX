package com.example.assignment.Utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment.Database.CountryDatabase;
import com.example.assignment.Model.Country;
import com.example.assignment.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private Context context;
    private CountryDatabase countryDatabase;
    List<Country> countryList;

    public MyAdapter(Context context, CountryDatabase countryDatabase){
        this.context = context;
        this.countryDatabase = countryDatabase;
        countryList = this.countryDatabase.getCountryDAO().getAllCountry();
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        Country country = countryList.get(position);

        Log.d("DEBUG",country.toString());

        holder.country_name.setText(context.getString(R.string.card_name,country.getName()));
        holder.country_capital.setText(context.getString(R.string.card_capital,country.getCapital()));
        holder.country_region.setText(context.getString(R.string.card_region,country.getRegion()));
        holder.country_subregion.setText(context.getString(R.string.card_subregion,country.getSubRegion()));
        holder.country_population.setText(context.getString(R.string.card_population,String.valueOf(country.getPopulation())));


        Uri url = Uri.parse(country.getImage());

        Util.fetchSvg(context, country.getImage(), holder.flag_image);

        List<String> borders = country.getBorders();
        String border = "";
        for (String s : borders) {
            border+=s+"\n";
        }
        holder.country_borders.setText(context.getString(R.string.card_borders,border));

        List<String> languages = country.getLanguages();
        String language = "";
        for (String s : languages) {
            language+=s+"\n";
        }
        holder.country_languages.setText(context.getString(R.string.card_languages,language));


    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView flag_image;
        public TextView country_name,country_capital,country_region,country_subregion,country_population,country_borders,country_languages;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flag_image = itemView.findViewById(R.id.card_flag);
            country_name = itemView.findViewById(R.id.card_name);
            country_capital = itemView.findViewById(R.id.card_capital);
            country_region = itemView.findViewById(R.id.card_region);
            country_subregion = itemView.findViewById(R.id.card_subregion);
            country_population = itemView.findViewById(R.id.card_population);
            country_borders = itemView.findViewById(R.id.card_border);
            country_languages = itemView.findViewById(R.id.card_language);

        }
    }
}
