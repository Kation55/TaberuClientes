package com.example.ubereats.recycleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.restaurant.RestaurantHomeActivity;
import com.example.ubereats.restaurant.Restaurante;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class RestaurantLargeRecycleView extends RecyclerView.Adapter<RestaurantLargeRecycleView.ViewHolder> {

    private ArrayList<Restaurante> restaurant;
    private int lastPosition = -1;
    private Context context;

    public RestaurantLargeRecycleView(ArrayList<Restaurante> restaurants) {
        this.restaurant = restaurants;
    }

    @NonNull
    @Override
    public RestaurantLargeRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_card_large_recycle, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantLargeRecycleView.ViewHolder holder, int position) {
        ((TextView) holder.getCategoryCard().findViewById(R.id.restaurantName)).setText(
                restaurant.get(position).getNombre());

        ((TextView) holder.getCategoryCard().findViewById(R.id.cardRestaurantDesc2)).setText(
                restaurant.get(position).getDescripcion());

        ((TextView) holder.getCategoryCard().findViewById(R.id.cardRestaurantTime2)).setText(
                "30 min");

        ((TextView) holder.getCategoryCard().findViewById(R.id.cardRestaurantRate2)).setText(
                restaurant.get(position).getPuntuacion() + " Estrellas");

        if (restaurant.get(position).getBitmap() != null) {
            ((ImageView) holder.getCategoryCard().findViewById(R.id.cardRestaurantImg2)).setImageBitmap(
                    restaurant.get(position).getBitmap()
            );
        }

        setAnimation(holder.itemView, position);

        holder.getCategoryCard().setOnClickListener((view) -> {
            Intent intent = new Intent(context, RestaurantHomeActivity.class);

            TextView name = view.findViewById(R.id.restaurantName);
            TextView time = view.findViewById(R.id.cardRestaurantTime2);
            TextView raiting = view.findViewById(R.id.cardRestaurantRate2);

            Pair<View, String> p1 = Pair.create((View)name, "restaurantName");
            Pair<View, String> p2 = Pair.create((View)time, "waitingTime");
            Pair<View, String> p3 = Pair.create((View)raiting, "raiting");

            intent.putExtra("RESTAURANT_NAME", restaurant.get(position).getNombre());
            intent.putExtra("RESTAURANT_TIME", "30 - 40 min");
            intent.putExtra("RESTAURANT_RATE", restaurant.get(position).getPuntuacion() + " Estrellas");
            intent.putExtra("RESTAURANT_CATEGORY", restaurant.get(position).getCategoria());

            intent.putExtra("RESTAURANT_ID", restaurant.get(position).getId());

            ActivityOptionsCompat option = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, p1, p2, p3);

            context.startActivity(intent, option.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return restaurant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout categoryCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCard = (ConstraintLayout) itemView.findViewById(R.id.cardRestaurant2);
        }

        public ConstraintLayout getCategoryCard() {
            return this.categoryCard;
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition && position <= 0)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setStartOffset(250 + 200 / (position+1));
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        } else {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setStartOffset(250);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
