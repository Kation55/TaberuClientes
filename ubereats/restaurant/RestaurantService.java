package com.example.ubereats.restaurant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class RestaurantService {

    private static RestaurantService restaurantService;
    private ArrayList<Restaurante> restauranteArrayList;
    private FirebaseFirestore db;

    public RestaurantService () {
        db = FirebaseFirestore.getInstance();
        restauranteArrayList = new ArrayList<>();
    }

    public static RestaurantService getInstance() {
        if (restaurantService == null)
            restaurantService = new RestaurantService();

        return restaurantService;
    }

    public Future<ArrayList> getRestaurants () {
        CompletableFuture<ArrayList> status = new CompletableFuture<>();

        restauranteArrayList.clear();

        db.collection("Restaurante").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getDocuments().forEach( documentSnapshot -> {
                        Restaurante restaurante = Restaurante.of(documentSnapshot.getData());

                        try {
                            restaurante.setId(documentSnapshot.getId());
                            restauranteArrayList.add(restaurante);
                        } catch (Exception ignored) {
                        }
                    });

                    status.complete(restauranteArrayList);
                }
            }
        });

        return status;
    }

    public Future<ArrayList> getRestaurantsByCategory (String category) {
        CompletableFuture<ArrayList> status = new CompletableFuture<>();

        restauranteArrayList.clear();

        db.collection("Restaurante").whereEqualTo("Categoria", category).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    task.getResult().getDocuments().forEach( documentSnapshot -> {
                        Restaurante restaurante = Restaurante.of(documentSnapshot.getData());
                        try {
                            restaurante.setId(documentSnapshot.getId());
                            restauranteArrayList.add(restaurante);
                        } catch (Exception ignored) {
                        }
                    });

                    status.complete(restauranteArrayList);
                }
            }
        });

        return status;
    }

    public Future<String> loadImage(String id) {
        CompletableFuture<String> status = new CompletableFuture<>();

        db.collection("Restaurante").document(id).collection("Platillos")
                .whereEqualTo("EsPortada", true).get().addOnCompleteListener(
            new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful()) {

                        if (!task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            status.complete((String)documentSnapshot.get("ImageURL"));
                        } else {
                            status.complete(null);
                        }
                    } else {

                        status.complete(null);
                    }
                }
            }
        );

        return status;
    }
}
