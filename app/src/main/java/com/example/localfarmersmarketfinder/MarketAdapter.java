package com.example.localfarmersmarketfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.MarketViewHolder> {

    private List<Farmer> farmers;

    public MarketAdapter(List<Farmer> farmers) {
        this.farmers = farmers;
    }

    @NonNull
    @Override
    public MarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market, parent, false);
        return new MarketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarketViewHolder holder, int position) {
        Farmer farmer = farmers.get(position);
        holder.tvMarketName.setText(farmer.getName());
        holder.tvMarketLocation.setText("Location: " + farmer.getLocation());
        holder.tvMarketContact.setText("Contact: " + farmer.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return farmers.size();
    }

    static class MarketViewHolder extends RecyclerView.ViewHolder {
        TextView tvMarketName, tvMarketLocation, tvMarketContact;

        MarketViewHolder(View itemView) {
            super(itemView);
            tvMarketName = itemView.findViewById(R.id.tvMarketName);
            tvMarketLocation = itemView.findViewById(R.id.tvMarketLocation);
            tvMarketContact = itemView.findViewById(R.id.tvMarketContact);
        }
    }
}