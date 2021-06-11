package com.example.ubereats.recycleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.restaurant.FoodDetails;
import com.example.ubereats.restaurant.RestaurantHomeActivity;
import com.example.ubereats.restaurant.RestaurantProductActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuRecycleView extends RecyclerView.Adapter<MenuRecycleView.ViewHolder> {

    private ArrayList<FoodDetails> menu;
    private int lastPosition = -1;
    private Context context;

    public MenuRecycleView(ArrayList<FoodDetails> menu) {
        this.menu = menu;
    }

    @NonNull
    @Override
    public MenuRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_menu, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuRecycleView.ViewHolder holder, int position) {
        setAnimation(holder.getMenu(), position);

        ((TextView) holder.getMenu().findViewById(R.id.textView30)).setText(
                (String) menu.get(position).getNombre()
        );

        ((TextView) holder.getMenu().findViewById(R.id.textView31)).setText(
                (String) menu.get(position).getDescripcion()
        );

        ((TextView) holder.getMenu().findViewById(R.id.textView32)).setText(
                "$" + menu.get(position).getPrecio() + ".00"
        );

        holder.getMenu().setOnClickListener((view) -> {
            Intent intent = new Intent(context, RestaurantProductActivity.class);

            TextView name = view.findViewById(R.id.textView30);
            TextView price = view.findViewById(R.id.textView32);

            Pair<View, String> p1 = Pair.create((View)name, "productName");
            Pair<View, String> p2 = Pair.create((View)price, "price");

            intent.putExtra("POSITION", position);

            ActivityOptionsCompat option = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((Activity) context, p1, p2);

            context.startActivity(intent, option.toBundle());
        });
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            menu = (ConstraintLayout) itemView.findViewById(R.id.menuRecycleView);
        }

        public ConstraintLayout getMenu() {
            return menu;
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition && position <= 2)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setStartOffset(250 + 100 * (position+1));
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
