package com.example.restaurantfinder.activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import android.util.Log;


public class RestaurantDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_RESTAURANT = "extra_restaurant";
    public static final String EXTRA_RESTAURANT_ORT = "extra_restaurant_ort";

    EditText input_edit_restaurant_name, input_edit_restaurant_ort;
    Button button_save_changes;
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        input_edit_restaurant_name = findViewById(R.id.editTextTextRestaurantName);
        input_edit_restaurant_ort = findViewById(R.id.editTextTextRestaurantOrt);
        button_save_changes = findViewById(R.id.buttonSaveChanges);
        title = findViewById(R.id.textViewTitle);
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            //setTitle("Restaurant upgraden");
            title.setText("Restaurant ändern");
            input_edit_restaurant_name.setText(intent.getStringExtra(EXTRA_RESTAURANT));
            input_edit_restaurant_ort.setText(intent.getStringExtra(EXTRA_RESTAURANT_ORT));
        } else {
            //setTitle("Restaurant ändern");
            title.setText("Restaurant hinzufügen");
        }

        button_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restaurant = input_edit_restaurant_name.getText().toString();
                String restaurant_ort = input_edit_restaurant_ort.getText().toString();

                if (TextUtils.isEmpty(restaurant)) {
                    Toast.makeText(RestaurantDetailsActivity.this, "Please enter some Restaurant to be added", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_RESTAURANT, restaurant);
                data.putExtra(EXTRA_RESTAURANT_ORT, restaurant_ort);

                //This -1 is because if the database already have the value or not
                //in simple terms we are checking whether the value should be updated or should added new
                int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1) {
                    data.putExtra(EXTRA_ID, id);
                }

                setResult(RESULT_OK, data);
                finish();

                Toast.makeText(RestaurantDetailsActivity.this, "Restaurant changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /*
    private void saveChanges() {
        String restaurant = input_edit_restaurant_name.getText().toString();

        if (TextUtils.isEmpty(restaurant)) {
            Toast.makeText(this, "Please enter some Restaurant to be added", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_RESTAURANT, restaurant);

        //This -1 is because if the database already have the value or not
        //in simple terms we are checking whether the value should be updated or should added new
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save_quotes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_quote:
                saveQuote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

}
