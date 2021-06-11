package com.example.ubereats.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.orders.Orders;

import java.util.List;

public class LoadCardRecycle extends RecyclerView.Adapter<LoadCardRecycle.ViewHolder> {

    public LoadCardRecycle() {

    }

    @NonNull
    @Override
    public LoadCardRecycle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.loading_card, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadCardRecycle.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout orderCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderCard = (ConstraintLayout) itemView.findViewById(R.id.cardsLoad);

        }

        public ConstraintLayout getCategoryCard() {
            return this.orderCard;
        }

        public void setOrderCard(ConstraintLayout orderCard) {
            this.orderCard = orderCard;
        }
    }
}
