package com.example.restaurantfinder.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.restaurantfinder.R;
import com.example.restaurantfinder.model.Restaurant;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantHolder> {
    private OnItemClickListener listener;

    public RestaurantAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK = new DiffUtil.ItemCallback<Restaurant>() {
        @Override
        public boolean areItemsTheSame(Restaurant oldRestaurant, Restaurant newRestaurant) {
            return oldRestaurant.getId() == newRestaurant.getId();
        }

        @Override
        public boolean areContentsTheSame(Restaurant oldRestaurant, Restaurant newRestaurant) {
            return oldRestaurant.getRestaurants().equals(newRestaurant.getRestaurants());
        }
    };

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        Restaurant currentRestaurant = getItem(position);
        holder.restaurantTxt.setText(currentRestaurant.getRestaurants());
    }

    public Restaurant getRestaurantAt(int position) {
        return getItem(position);
    }

    class RestaurantHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView restaurantTxt;

        public RestaurantHolder(View itemView) {
            super(itemView);
            restaurantTxt = itemView.findViewById(R.id.restaurants_txt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
