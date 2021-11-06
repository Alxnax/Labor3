package com.example.restaurantfinder.activity;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.restaurantfinder.R;
import com.example.restaurantfinder.viewmodel.RestaurantsViewModel;
import com.example.restaurantfinder.adapter.RestaurantAdapter;
import com.example.restaurantfinder.db.RestaurantsDataBase;
import com.example.restaurantfinder.model.Restaurant;

import java.util.List;
import android.widget.Button;
import android.widget.EditText;




public class MainActivity extends AppCompatActivity {

    private RestaurantsViewModel restaurantsViewModel;
    Button button_add;
    EditText input_restaurant_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        input_restaurant_name = findViewById(R.id.editTextRestaurantName);
        button_add = findViewById(R.id.buttonAdd);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurant_string = input_restaurant_name.getText().toString();

                Restaurant new_restaurant = new Restaurant();
                new_restaurant.setRestaurants(restaurant_string);

                restaurantsViewModel.insert(new_restaurant);

                input_restaurant_name.getText().clear();

                Toast.makeText(MainActivity.this, "Restaurant added", Toast.LENGTH_SHORT).show();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final RestaurantAdapter adapter = new RestaurantAdapter();
        recyclerView.setAdapter(adapter);
        restaurantsViewModel = ViewModelProviders.of(this).get(RestaurantsViewModel.class);
        restaurantsViewModel.getAllRestaurants().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable List<Restaurant> restaurants) {
                adapter.submitList(restaurants);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                restaurantsViewModel.delete(adapter.getRestaurantAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Restaurant deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        /*adapter.setOnItemClickListener(new RestaurantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurant restaurant) {
                Intent intent = new Intent(MainActivity.this, AddEditRestaurantActivity.class);
                intent.putExtra(AddEditRestaurantActivity.EXTRA_ID, restaurant.getId());
                intent.putExtra(AddEditRestaurantActivity.EXTRA_QUOTE, restaurant.getRestaurants());
                startActivityForResult(intent, REQUEST_CODE_EDIT_QUOTE);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_delete_all_restaurants, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_restaurants:
                restaurantsViewModel.deleteAllRestaurants();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.recreate_db:
                RestaurantsDataBase.getInstance(this).reCreateDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_QUOTE && resultCode == RESULT_OK) {
            String retrievedRestaurant = data.getStringExtra(AddEditRestaurantActivity.EXTRA_QUOTE);

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurants(retrievedRestaurant);

            restaurantsViewModel.insert(restaurant);

            Toast.makeText(this, "Restaurant saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_CODE_EDIT_QUOTE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditRestaurantActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Restaurant can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String retrievedRestaurant = data.getStringExtra(AddEditRestaurantActivity.EXTRA_QUOTE);

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurants(retrievedRestaurant);
            restaurant.setId(id);
            restaurantsViewModel.update(restaurant);

            Toast.makeText(this, "Restaurant updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Restaurant not saved", Toast.LENGTH_SHORT).show();
        }
    }*/
}
