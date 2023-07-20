package com.example.facilities_app.presenter.adaptor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilities_app.R;
import com.example.facilities_app.model.Exclusion;
import com.example.facilities_app.model.FacilitiesType;

import java.util.HashMap;
import java.util.List;

public class FacilitiesListAdaptor extends RecyclerView.Adapter<FacilitiesListAdaptor.MyViewHolder> {
    List<FacilitiesType> facilitiesList;
    LinearLayoutManager layoutManager;
    List<List<Exclusion>> exclusion;
    HashMap<String, String> selectedItems = new HashMap<String, String>();

    public FacilitiesListAdaptor(List<FacilitiesType> facilitiesList, List<List<Exclusion>> exclusions) {
        this.facilitiesList = facilitiesList;
        this.exclusion = exclusions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facilitiesinformationlist, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilitiesListAdaptor.MyViewHolder holder, int position) {
        FacilitiesType facilitiesType = facilitiesList.get(holder.getAdapterPosition());
        holder.facilitiesType.setText(facilitiesType.getName());
        FacilitiesOptionsListAdaptor facilitiesOptionsListAdaptor =
                new FacilitiesOptionsListAdaptor(facilitiesType, exclusion, new FacilitiesOptionsListAdaptor.ItemSelectedListener() {
                    @Override
                    public void onSelect(String selectedItem) {
                        selectedItems.put(facilitiesType.getFacilityId(), selectedItem);
                    }

                    @Override
                    public HashMap<String, String> getSelectedList() {
                        return selectedItems;
                    }
                });
        layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.optionListView.setLayoutManager(layoutManager);
        holder.optionListView.setAdapter(facilitiesOptionsListAdaptor);
    }

    @Override
    public int getItemCount() {
        return facilitiesList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView facilitiesType;
        RecyclerView optionListView;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.facilitiesType = (TextView) itemView.findViewById(R.id.idFacilitiesType);
            this.optionListView = (RecyclerView) itemView.findViewById(R.id.idOptionListView);
        }
    }
}
