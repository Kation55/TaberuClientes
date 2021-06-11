package com.example.ubereats.home;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubereats.R;
import com.example.ubereats.user.AddressDetails;
import com.example.ubereats.user.Cliente;
import com.example.ubereats.recycleView.AddressRecycleView;
import com.example.ubereats.recycleView.CouponRecycleView;
import com.example.ubereats.recycleView.CreditCardsRecycleView;
import com.example.ubereats.user.ClienteService;
import com.example.ubereats.user.CuponDetails;
import com.example.ubereats.user.PaymentDetails;
import com.example.ubereats.user.UserAddressActivity;
import com.example.ubereats.user.UserPaymentActivity;
import com.example.ubereats.user.UserCuponActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class HomeUserActivity extends Fragment {


    private Button addAddress,addPayment,addCupon;
    private Context context;
    private RecyclerView direcciones,mpago,rcupones;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.home_user,null);

        setText(v);

        context = container.getContext();
        addAddress = (Button) v.findViewById(R.id.button);
        addPayment = (Button) v.findViewById(R.id.button3);
        addCupon = (Button) v.findViewById(R.id.button2);

        direcciones = v.findViewById(R.id.addressRecycleView);
        direcciones.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        mpago = v.findViewById(R.id.creditCardRecycleView);
        mpago.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        rcupones = v.findViewById(R.id.cuponRecycleView);
        rcupones.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        new LoadAddress().execute();
        new LoadPayment().execute();
        new LoadCupones().execute();

        addAdd(v);
        addPay(v);
        addCupon(v);
        return v;
    }

    @Override
    public void onResume() {
        new LoadAddress().execute();
        new LoadPayment().execute();
        new LoadCupones().execute();

        super.onResume();
    }

    public void addAdd(View view)
    {


        addAddress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, UserAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addPay(View view)
    {


        addPayment.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, UserPaymentActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addCupon(View view)
    {


        addCupon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, UserCuponActivity.class);
                startActivity(intent);
            }
        });
    }


    public void setText(View view)
    {
        TextView textView = (TextView) view.findViewById(R.id.userNameTitle);
        textView.setText(Cliente.getInstance().getNombre());

        TextView textView02 = (TextView) view.findViewById(R.id.textView17);
        textView02.setText(Cliente.getInstance().getNombre());

        TextView textView03 = (TextView) view.findViewById(R.id.textView19);
        textView03.setText(Cliente.getInstance().getEmail());

        TextView textView04 = (TextView) view.findViewById(R.id.textView21);
        textView04.setText(Cliente.getInstance().getTelefono());

        TextView textView05 = (TextView) view.findViewById(R.id.textView12);
        textView05.setText(String.valueOf(Cliente.getInstance().getPuntuacion()) + " Puntuación");
    }

    private class LoadAddress extends AsyncTask<Void, Void, Boolean> {

        ArrayList<AddressDetails> addressArrayList;

        public LoadAddress()

        {

        }

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
                AddressRecycleView addressRecycleView = new AddressRecycleView(addressArrayList, false);
                direcciones.setAdapter(addressRecycleView);
            }
        }
    }

    private class LoadPayment extends AsyncTask<Void, Void, Boolean> {

        ArrayList<PaymentDetails> paymentArrayList;

        public LoadPayment()

        {

        }

        @Override //Crea nuevo hilo
        protected Boolean doInBackground(Void... voids) {

            try {
                paymentArrayList = ClienteService.getInstance().getPayment().get();
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
                CreditCardsRecycleView paymentRecycleView = new CreditCardsRecycleView(paymentArrayList, false);
                mpago.setAdapter(paymentRecycleView);
            }
        }
    }

    private class LoadCupones extends AsyncTask<Void, Void, Boolean> {

        ArrayList<CuponDetails> cuponArrayList;

        public LoadCupones()

        {

        }

        @Override //Crea nuevo hilo
        protected Boolean doInBackground(Void... voids) {

            try {
                cuponArrayList = ClienteService.getInstance().getCupon().get();
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
                CouponRecycleView cuponRecycleView = new CouponRecycleView(cuponArrayList);
                rcupones.setAdapter(cuponRecycleView);
            }
        }
    }


}