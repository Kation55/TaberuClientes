package com.example.ubereats.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.recycleView.CategoryRecycleView;
import com.example.ubereats.recycleView.LoadCardRecycle;
import com.example.ubereats.recycleView.RestaurantSmallRecycleView;
import com.example.ubereats.restaurant.RestaurantService;
import com.example.ubereats.restaurant.Restaurante;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeMenuActivity extends Fragment {
    private ArrayList<String> categoryName;
    private ArrayList<Drawable> categoryIcon;

    private RecyclerView categoryCards;
    private RecyclerView restaurant1Cards;
    private RecyclerView restaurant2Cards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_menu, null);

        categoryCards = (RecyclerView) v.findViewById(R.id.CategoryMenuRecycle);
        categoryCards.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));

        categoryName = new ArrayList<>();
        categoryIcon = new ArrayList<>();

        String[] arrayCategoryName = getResources().getStringArray(R.array.CategoryArray);
        for (int i = 0; i < arrayCategoryName.length; i++) {
            categoryName.add(arrayCategoryName[i]);
            categoryIcon.add(getIcon(i, v));
        }

        CategoryRecycleView categoryRecycleView = new CategoryRecycleView(categoryName, categoryIcon, null, null);
        categoryCards.setAdapter(categoryRecycleView);

        restaurant1Cards = (RecyclerView) v.findViewById(R.id.Restaurant1);
        restaurant1Cards.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));


        restaurant2Cards = (RecyclerView) v.findViewById(R.id.Restaurante2);
        restaurant2Cards.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));

        restaurant1Cards.setAdapter(new LoadCardRecycle());
        restaurant2Cards.setAdapter(new LoadCardRecycle());

        TextView title1 = (TextView) v.findViewById(R.id.textView3);
        TextView title2 = (TextView) v.findViewById(R.id.textView4);

        Animation animation = AnimationUtils.loadAnimation(getContext().getApplicationContext(), android.R.anim.slide_in_left);
        animation.setDuration(1000);

        title1.setVisibility(View.GONE);
        title2.setVisibility(View.GONE);

        new LoadRestaurants(v, title1, title2, animation).execute();

        return v;
    }

    private void setRestaurants(RecyclerView recyclerView, ArrayList<Restaurante> restaurantes) {
        RestaurantSmallRecycleView restaurantRecycleView = new RestaurantSmallRecycleView(restaurantes);
        recyclerView.setAdapter(restaurantRecycleView);
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

    private class LoadRestaurants extends AsyncTask<Void, Void, Boolean> {

        private View view;
        private TextView title1, title2;
        private Animation animation;
        private ArrayList<Restaurante> restaurantes;

        public LoadRestaurants(View view, TextView title1, TextView title2, Animation animation) {
            this.view = view;
            this.title1 = title1;
            this.title2 = title2;
            this.animation = animation;
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                restaurantes = RestaurantService.getInstance().getRestaurants().get();
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
                setRestaurants(restaurant1Cards, restaurantes);
                ((TextView) view.findViewById(R.id.textView3)).setVisibility(View.VISIBLE);
                title1.startAnimation(animation);

                setRestaurants(restaurant2Cards, restaurantes);
                ((TextView) view.findViewById(R.id.textView4)).setVisibility(View.VISIBLE);
                title2.startAnimation(animation);
            }
        }
    }
}
