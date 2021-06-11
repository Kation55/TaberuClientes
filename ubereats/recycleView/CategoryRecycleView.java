package com.example.ubereats.recycleView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.home.HomeSearchActivity;
import com.example.ubereats.restaurant.RestaurantService;
import com.example.ubereats.restaurant.Restaurante;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CategoryRecycleView extends RecyclerView.Adapter<CategoryRecycleView.ViewHolder> {

    private ArrayList<String> categoryName;
    private ArrayList<Drawable> categoryIcon;
    private EditText editText;
    private Context context;
    private RecyclerView recyclerView;
    private int lastPosition = -1;

    public CategoryRecycleView(ArrayList<String> categoryName, ArrayList<Drawable> categoryIcon, EditText editText, RecyclerView recyclerView) {
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
        this.editText = editText;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public CategoryRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_category, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRecycleView.ViewHolder holder, int position) {
        holder.getCategoryCard().setText(categoryName.get(position));
        holder.getCategoryCard().setCompoundDrawablesWithIntrinsicBounds(null, categoryIcon.get(position), null, null);

        setAnimation(holder.itemView, position);

        holder.getCategoryCard().setOnClickListener( (view) -> {
            editText.setText(categoryName.get(position));

            recyclerView.setAdapter(new LoadCardRecycle());
            new LoadRestaurants(recyclerView, categoryName.get(position)).execute();
        });
    }

    @Override
    public int getItemCount() {
        return categoryName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView categoryCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryCard = (TextView) itemView.findViewById(R.id.categoryCard);
        }

        public TextView getCategoryCard() {
            return this.categoryCard;
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition && position <= 3)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setStartOffset(250 + 300 / (position+1));
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        } else {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setStartOffset(250);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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
                RestaurantLargeRecycleView restaurantRecycleView = new RestaurantLargeRecycleView(restaurantes);
                recyclerView.setAdapter(restaurantRecycleView);
            }
        }
    }
}
