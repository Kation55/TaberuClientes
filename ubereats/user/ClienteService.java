package com.example.ubereats.user;

import androidx.annotation.NonNull;

import com.example.ubereats.recycleView.AddressRecycleView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ClienteService
{
    private static ClienteService clienteService;
    private ArrayList<AddressDetails> addressArrayList;
    private ArrayList<PaymentDetails> paymentArrayList;
    private ArrayList<CuponDetails> cuponArrayList;
    private FirebaseFirestore db;
    private FirebaseAuth FAuth;


    public ClienteService(){

        FAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        addressArrayList = new ArrayList<>();
        paymentArrayList= new ArrayList<>();
        cuponArrayList= new ArrayList<>();
    }

    public PaymentDetails getCardById(String id) {

        for (int i = 0; i < paymentArrayList.size(); i++) {
            PaymentDetails paymentDetails = paymentArrayList.get(i);

            if (paymentDetails.getId().equals(id))
                return paymentDetails;
        }

        return null;
    }

    public AddressDetails getAddressById(String id) {

        for (int i = 0; i < addressArrayList.size(); i++) {
            AddressDetails addressDetails = addressArrayList.get(i);

            if (addressDetails.getId().equals(id))
                return addressDetails;
        }

        return null;
    }

    public static ClienteService getInstance() {
        if (clienteService == null)
            clienteService = new ClienteService();

        return clienteService;
    }

    public Future<ArrayList> getAddress ()
    {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CompletableFuture<ArrayList> status = new CompletableFuture<>();
        addressArrayList.clear();

        db.collection("Cliente").document(userId).collection("Direcciones").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    task.getResult().getDocuments().forEach( documentSnapshot -> {

                        AddressDetails address = AddressDetails.of(documentSnapshot.getData());
                        address.setId(documentSnapshot.getId());
                        addressArrayList.add(address);

                    });

                    status.complete(addressArrayList);
                }
                else
                {
                    status.complete(addressArrayList);
                }

            }
        });

        return status;
    }

    public Future<ArrayList> getPayment()
    {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CompletableFuture<ArrayList> status = new CompletableFuture<>();
        paymentArrayList.clear();

        db.collection("Cliente").document(userId).collection("Metodos Pago").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    task.getResult().getDocuments().forEach( documentSnapshot -> {

                        PaymentDetails payment = PaymentDetails.of(documentSnapshot.getData());
                        payment.setId(documentSnapshot.getId());
                        paymentArrayList.add(payment);

                    });

                    status.complete(paymentArrayList);
                }
                else
                {
                    status.complete(paymentArrayList);
                }

            }
        });

        return status;
    }

    public Future<ArrayList> getCupon()
    {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        CompletableFuture<ArrayList> status = new CompletableFuture<>();
        cuponArrayList.clear();

        db.collection("Cliente").document(userId).collection("Codigos Promocionales").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    task.getResult().getDocuments().forEach( documentSnapshot -> {

                        CuponDetails cupon = CuponDetails.of(documentSnapshot.getData());
                        cuponArrayList.add(cupon);

                    });

                    status.complete(cuponArrayList);
                }
                else
                {
                    status.complete(cuponArrayList);
                }

            }
        });

        return status;
    }
}
