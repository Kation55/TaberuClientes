package com.example.ubereats.recycleView;

import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.cart.Cart;
import com.example.ubereats.cart.CartDatabase;

import java.util.List;

public class ItemRecycleView extends RecyclerView.Adapter<ItemRecycleView.ViewHolder> {

    private List<Cart> carts;
    private TextView amount;
    private int totalPrice;
    private CartDatabase database;

    public ItemRecycleView(List<Cart> cards, CartDatabase database, TextView amount) {
        this.carts = cards;
        this.database = database;
        this.amount = amount;

        this.totalPrice = 0;
    }

    @NonNull
    @Override
    public ItemRecycleView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_order_item, null, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecycleView.ViewHolder holder, int position) {
        holder.getName().setText(carts.get(position).name);

        int quantity = carts.get(position).quantity;
        holder.getQuantity().setText((String)(quantity + ""));

        int price = carts.get(position).standarCost;
        holder.getPrice().setText((String)("$" + price * quantity + ".00"));

        totalPrice += carts.get(position).quantity * carts.get(position).standarCost;

        holder.getDecrease().setOnClickListener((view) -> {
            new DecreaseItem(holder, carts.get(position), database).execute();
        });

        holder.getIncrease().setOnClickListener((view) -> {
            new IncreaseItem(holder, carts.get(position), database).execute();
        });

        holder.getDelete().setOnClickListener((view) -> {
            new RemoveItem(holder, carts.get(position), database).execute();
        });

        if (amount == null) {
            holder.getDelete().setVisibility(View.INVISIBLE);
            holder.getIncrease().setVisibility(View.INVISIBLE);
            holder.getQuantity().setVisibility(View.INVISIBLE);
            holder.getDecrease().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout itemRecycle;
        private TextView name, quantity, price;
        private Button increase, decrease, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemRecycle = (ConstraintLayout) itemView.findViewById(R.id.itemRecycle);

            //Obtenemos los elementos
            name = (TextView) itemRecycle.findViewById(R.id.ROIName);
            quantity = (TextView) itemRecycle.findViewById(R.id.ROIQuantity);
            price = (TextView) itemRecycle.findViewById(R.id.ROIPrice);

            increase = (Button) itemRecycle.findViewById(R.id.button8);
            decrease = (Button) itemRecycle.findViewById(R.id.button7);
            delete = (Button) itemView.findViewById(R.id.button6);
        }

        public ConstraintLayout getItemRecycle() {
            return itemRecycle;
        }

        public Button getDecrease() {
            return decrease;
        }

        public Button getDelete() {
            return delete;
        }

        public Button getIncrease() {
            return increase;
        }

        public TextView getName() {
            return name;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getQuantity() {
            return quantity;
        }

        public void setItemRecycle(ConstraintLayout itemRecycle) {
            this.itemRecycle = itemRecycle;
        }

        public void setDecrease(Button decrease) {
            this.decrease = decrease;
        }

        public void setDelete(Button delete) {
            this.delete = delete;
        }

        public void setIncrease(Button increase) {
            this.increase = increase;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public void setPrice(TextView price) {
            this.price = price;
        }

        public void setQuantity(TextView quantity) {
            this.quantity = quantity;
        }
    }

    private class DecreaseItem extends AsyncTask<Void, Void, Void> {

        private ViewHolder itemCard;
        private Cart item;
        private CartDatabase database;

        public DecreaseItem (ViewHolder itemCard, Cart item, CartDatabase database) {
            this.itemCard = itemCard;
            this.item = item;
            this.database = database;

            itemCard.getDecrease().setEnabled(false);
            itemCard.getIncrease().setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if (item.quantity != 1) {
                item.quantity --;
                database.cartDAO().updateItem(item);
                totalPrice -= item.standarCost;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            itemCard.getPrice().setText("$" + item.quantity * item.standarCost + ".00");
            itemCard.getQuantity().setText( item.quantity + "");

            amount.setText("$" + totalPrice + ".00");

            itemCard.getDecrease().setEnabled(true);
            itemCard.getIncrease().setEnabled(true);
        }
    }

    private class IncreaseItem extends AsyncTask<Void, Void, Void> {

        private ViewHolder itemCard;
        private Cart item;
        private CartDatabase database;

        public IncreaseItem (ViewHolder itemCard, Cart item, CartDatabase database) {
            this.itemCard = itemCard;
            this.item = item;
            this.database = database;

            itemCard.getDecrease().setEnabled(false);
            itemCard.getIncrease().setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            item.quantity ++;
            totalPrice += item.standarCost;
            database.cartDAO().updateItem(item);

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            itemCard.getPrice().setText("$" + item.quantity * item.standarCost + ".00");
            itemCard.getQuantity().setText( item.quantity + "");

            amount.setText("$" + totalPrice + ".00");

            itemCard.getDecrease().setEnabled(true);
            itemCard.getIncrease().setEnabled(true);
        }
    }

    private class RemoveItem extends AsyncTask<Void, Void, Void> {

        private ViewHolder itemCard;
        private Cart item;
        private CartDatabase database;

        public RemoveItem (ViewHolder itemCard, Cart item, CartDatabase database) {
            this.itemCard = itemCard;
            this.item = item;
            this.database = database;

            this.itemCard.getItemRecycle().setVisibility(View.INVISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            database.cartDAO().deleteItem(item);
            totalPrice -= item.quantity * item.standarCost;
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            amount.setText("$" + totalPrice + ".00");
        }
    }

}
