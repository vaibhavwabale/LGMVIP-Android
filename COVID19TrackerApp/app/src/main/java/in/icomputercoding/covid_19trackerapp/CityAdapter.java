package in.icomputercoding.covid_19trackerapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import in.icomputercoding.covid_19trackerapp.databinding.TestingBinding;

public class CityAdapter extends ArrayAdapter<Model> {
    private final List<Model> models;

    public CityAdapter(Context mContext, List<Model> models) {
        super(mContext, R.layout.testing, models);
        this.models = models;

    }


    @SuppressLint("ViewHolder")
    public View getView(int position, View convertView, ViewGroup parent) {
        TestingBinding binding;
        binding =TestingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        binding.state.setText(models.get(position).getDistrictName());
        binding.deceased.setText(models.get(position).getDeceased());
        binding.confirmed.setText(models.get(position).getConfirmed());
        binding.active.setText(models.get(position).getActive());
        binding.recovered.setText(models.get(position).getRecovered());
        return binding.getRoot();

    }


}