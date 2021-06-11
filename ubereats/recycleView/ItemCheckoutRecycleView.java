package com.example.ubereats.recycleView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;

import java.util.List;

public class ItemCheckoutRecycleView extends RecyclerView.Adapter<ItemCheckoutRecycleView.ViewHolder> {

    private List<Cart> carts;
    private TextView amount;
    private CartDatabase database;

    public ItemCheckoutRecycleView(List<Cart> cards) {
        this.carts = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_order_item_checkout, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getName().setText(carts.get(position).name);

        int quantity = carts.get(position).quantity;

        int price = carts.get(position).standarCost;
        holder.getPrice().setText((String)("$" + price * quantity + ".00"));

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemRecycle;
        private TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemRecycle = (ConstraintLayout) itemView.findViewById(R.id.itemRecycle);

            //Obtenemos los elementos
            name = (TextView) itemRecycle.findViewById(R.id.ROIName);
            price = (TextView) itemRecycle.findViewById(R.id.ROIPrice);
        }

        public ConstraintLayout getItemRecycle() {
            return itemRecycle;
        }

        public TextView getName() {
            return name;
        }

        public TextView getPrice() {
            return price;
        }

        public void setItemRecycle(ConstraintLayout itemRecycle) {
            this.itemRecycle = itemRecycle;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }
    }

}
