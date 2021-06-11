package com.example.ubereats.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;

import java.util.List;

public class OrderRecycleView extends RecyclerView.Adapter<OrderRecycleView.ViewHolder> {

    private List<Object> orders;

    public OrderRecycleView(List<Object> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_order, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecycleView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout orderCard;
        private TextView restaurant, info, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderCard = (ConstraintLayout) itemView.findViewById(R.id.orderRecycle);

            //Obtenemos los elementos
            restaurant = (TextView) orderCard.findViewById(R.id.ORRestaurant);
            info = (TextView) orderCard.findViewById(R.id.ORInfo);
            price = (TextView) orderCard.findViewById(R.id.ORPrice);
        }

        public ConstraintLayout getCategoryCard() {
            return this.orderCard;
        }

        public TextView getInfo() {
            return info;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getRestaurant() {
            return restaurant;
        }

        public ConstraintLayout getOrderCard() {
            return orderCard;
        }

        public void setStringInfo(String info) {
            this.info.setText(info);
        }

        public void setOrderCard(ConstraintLayout orderCard) {
            this.orderCard = orderCard;
        }

        public void setStringPrice(String price) {
            this.price.setText(price);
        }

        public void setStringRestaurant(String restaurant) {
            this.restaurant.setText(restaurant);
        }
    }
}
