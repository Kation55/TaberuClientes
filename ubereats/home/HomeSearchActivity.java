package com.example.ubereats.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.recycleView.CategoryRecycleView;
import com.example.ubereats.recycleView.LoadCardRecycle;
import com.example.ubereats.recycleView.RestaurantLargeRecycleView;
import com.example.ubereats.recycleView.RestaurantSmallRecycleView;
import com.example.ubereats.restaurant.RestaurantService;
import com.example.ubereats.restaurant.Restaurante;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HomeSearchActivity extends Fragment {
    private ArrayList<String> categoryName;
    private ArrayList<Drawable> categoryIcon;

    private RecyclerView restaurants;
    private RecyclerView categoryCards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.home_search,null);

        categoryCards = (RecyclerView) v.findViewById(R.id.searchCategoryRecycle);
        categoryCards.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryName = new ArrayList<>();
        categoryIcon = new ArrayList<>();

        String[] arrayCategoryName = getResources().getStringArray(R.array.CategoryArray);
        for (int i = 0; i < arrayCategoryName.length; i++) {
            categoryName.add(arrayCategoryName[i]);
            categoryIcon.add(getIcon(i, v));
        }

        EditText editText = (EditText) v.findViewById(R.id.inputAddressFormAddress3);

        restaurants = (RecyclerView) v.findViewById(R.id.searchRestaurantRecycle);
        restaurants.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));
        LoadCardRecycle loadCardRecycle = new LoadCardRecycle();

        restaurants.setAdapter(loadCardRecycle);

        CategoryRecycleView categoryRecycleView = new CategoryRecycleView(categoryName, categoryIcon, editText, restaurants);
        categoryCards.setAdapter(categoryRecycleView);

        new LoadRestaurants(v, null).execute();

        return v;
    }

    public Drawable getIcon(int id, View v) {
        switch (id) {
            case 0:
                return v.getContext().getApplicationContext().getDrawable(R.drawable.ic_food_pizzas);
            case 1:
                return v.getContext().getApplicationContext().getDrawable(R.drawable.ic_food_desert);
            case 2:
                return v.getContext().getApplicationContext().getDrawable(R.drawable.ic_food_breakfast);
            case 3:
                return v.getContext().getApplicationContext().getDrawable(R.drawable.ic_food_tacos);
            case 4:
                return v.getContext().getApplicationContext().getDrawable(R.drawable.ic_food_hamburger);
        }

        return null;
    }

    private void setRestaurants(RecyclerView recyclerView, ArrayList<Restaurante> restaurantes) {
        RestaurantLargeRecycleView restaurantRecycleView = new RestaurantLargeRecycleView(restaurantes);
        recyclerView.setAdapter(restaurantRecycleView);
    }

    public class LoadRestaurants extends AsyncTask<Void, Void, Boolean> {

        private View view;
        private String filter;
        private ArrayList<Restaurante> restaurantes;

        public LoadRestaurants(View view, String filter) {
            this.view = view;
            this.filter = filter;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                if (filter != null) {
                    restaurantes = RestaurantService.getInstance().getRestaurantsByCategory(filter).get();
                } else {
                    restaurantes = RestaurantService.getInstance().getRestaurants().get();
                }
                restaurantes.forEach( restaurante -> {
                    try {
                        String img = RestaurantService.getInstance().loadImage(restaurante.getId()).get();
                        if (img != null) {
                            URL newurl = new URL(img);
                            Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                            restaurante.setBitmap(mIcon_val);
                        } else {
                            restaurante.setBitmap(null);
                        }
                    } catch (ExecutionException | InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result) {
                setRestaurants(restaurants, restaurantes);
            }
        }
    }
}