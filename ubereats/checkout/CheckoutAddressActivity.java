package com.example.ubereats.checkout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.recycleView.AddressRecycleView;
import com.example.ubereats.user.AddressDetails;
import com.example.ubereats.user.ClienteService;
import com.example.ubereats.user.UserAddressActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CheckoutAddressActivity extends AppCompatActivity {
    private RecyclerView address;
    private Button addAddress, order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_address);

        addAddress = findViewById(R.id.button);
        order = findViewById(R.id.button9);

        address = findViewById(R.id.addressRecycle);
        address.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        new LoadAddress().execute();

        addAddress.setOnClickListener((view) -> {
            Intent intent = new Intent(this, UserAddressActivity.class);
            startActivity(intent);
        });

        order.setOnClickListener((view) -> {
            if (CheckoutService.getInstance().getAddressId() != null) {
                Intent intent = new Intent(this, CheckoutFinishActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        new LoadAddress().execute();

        super.onResume();
    }

    private class LoadAddress extends AsyncTask<Void, Void, Boolean> {

        ArrayList<AddressDetails> addressArrayList;

        public LoadAddress(){}

        @Override //Crea nuevo hilo
        protected Boolean doInBackground(Void... voids) {

            try {
                addressArrayList = ClienteService.getInstance().getAddress().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override//Regresa hilo principal
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (result)
            {
                AddressRecycleView addressRecycleView = new AddressRecycleView(addressArrayList, true);
                address.setAdapter(addressRecycleView);
            }
        }
    }
}
