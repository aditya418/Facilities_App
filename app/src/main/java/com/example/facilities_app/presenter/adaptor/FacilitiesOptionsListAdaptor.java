package com.example.facilities_app.presenter.adaptor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facilities_app.R;
import com.example.facilities_app.model.Exclusion;
import com.example.facilities_app.model.FacilitiesType;
import com.example.facilities_app.model.Options;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FacilitiesOptionsListAdaptor extends RecyclerView.Adapter<FacilitiesOptionsListAdaptor.MyViewHolder> {
    FacilitiesType facilitiesType;
    List<Options> optionsList;
    int selectedIndex = -1;
    ItemSelectedListener listener;
    List<List<Exclusion>> exclusion;


    public FacilitiesOptionsListAdaptor(FacilitiesType facilitiesType, List<List<Exclusion>> exclusion, ItemSelectedListener listener) {
        this.facilitiesType = facilitiesType;
        this.optionsList = facilitiesType.getOptionsList();
        this.listener = listener;
        this.exclusion = exclusion;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.facilitiestypesoption, parent, false);

        return new MyViewHolder(view);
    }

    private Drawable getIcon(Context context, String name) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name.replace("-", "_"), "drawable",
                context.getPackageName());
        return resources.getDrawable(resourceId);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilitiesOptionsListAdaptor.MyViewHolder holder, int position) {
        holder.radioButton.setChecked(selectedIndex == holder.getAdapterPosition());
        holder.imageView.setImageDrawable(getIcon(holder.imageView.getContext(), facilitiesType.getOptionsList().get(position).getIcon()));

        Log.d("test", String.valueOf(exclusion.size()));
        holder.optionName.setText(optionsList.get(holder.getAdapterPosition()).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevIndex = selectedIndex;
                selectedIndex = holder.getAdapterPosition();
                holder.itemView.setEnabled(true);
                for (int i = 0; i < exclusion.size(); i++) {
                    List<Exclusion> exclusionItem = exclusion.get(i);
                    if (exclusionItem.get(0).getFacilityId().equals(facilitiesType.getFacilityId()) &&
                            exclusionItem.get(0).getOptionsId().equals(optionsList.get(holder.getAdapterPosition()).getId())) {
                        if (Objects.equals(listener.getSelectedList().get(exclusionItem.get(1).getFacilityId()), exclusionItem.get(1).getOptionsId())) {
                            selectedIndex = prevIndex;
                            showAlert(v.getContext());
                        }
                    } else if (exclusionItem.get(1).getFacilityId().equals(facilitiesType.getFacilityId()) &&
                            exclusionItem.get(1).getOptionsId().equals(optionsList.get(holder.getAdapterPosition()).getId())) {
                        if (Objects.equals(listener.getSelectedList().get(exclusionItem.get(0).getFacilityId()), exclusionItem.get(0).getOptionsId())) {
                            selectedIndex = prevIndex;
                            showAlert(v.getContext());
                        }
                    }
                }

                if (selectedIndex != prevIndex) {
                    holder.radioButton.setChecked(true);
                    listener.onSelect(optionsList.get(holder.getAdapterPosition()).getId());
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView optionName;
        RadioButton radioButton;
        ImageView imageView;
        View view;

        public MyViewHolder(View itemView2) {
            super(itemView2);
            view = itemView2.findViewById(R.id.rootview);
            this.optionName = (TextView) itemView2.findViewById(R.id.idOptionName);
            this.imageView = (ImageView) itemView2.findViewById(R.id.icon);
            this.radioButton = (RadioButton) itemView2.findViewById(R.id.selected_item);
        }
    }

    private void showAlert(Context context) {
        new AlertDialog.Builder(context).setTitle("Invalid Selection!")
                .setMessage("This choice cannot be selected in combination with previously selected facility.")
                .create().show();
    }

    interface ItemSelectedListener {
        void onSelect(String selectedItem);

        HashMap<String, String> getSelectedList();
    }
}
