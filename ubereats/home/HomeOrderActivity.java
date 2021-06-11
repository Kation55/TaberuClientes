package com.example.ubereats.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
public class HomeOrderActivity extends Fragment {
    private RecyclerView ordersRecycle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_orders,null);

        return v;
    }


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_orders);

        ordersDatabase = Room.databaseBuilder(getApplicationContext(),
                OrdersDatabase.class, "orders").build();

        //createOrders();
        new GetOrdersAsyncTask(ordersDatabase).execute();
    }

    */
}
