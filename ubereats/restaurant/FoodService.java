package com.example.ubereats.restaurant;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class FoodService {

    private ArrayList<FoodDetails> foodList;
    private FirebaseFirestore db;
    private String lastId;
    private static FoodService foodService;

    public FoodService() {
        foodList = new ArrayList();
        db = FirebaseFirestore.getInstance();
        lastId = "";
    }

    public static FoodService getInstance() {
        if (foodService == null)
            foodService = new FoodService();

        return foodService;
    }

    public FoodDetails getPosition(int position) {
        return foodList.get(position);
    }

    public Future<ArrayList> getFood (String id) {
        CompletableFuture<ArrayList> status = new CompletableFuture<>();

        if (foodList.isEmpty() || !id.contentEquals(lastId)) {
            foodList.clear();
            lastId = id;
            db.collection("Restaurante").document(id).collection("Platillos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        task.getResult().getDocuments().forEach( documentSnapshot -> {
                            FoodDetails foodDetails = FoodDetails.of(documentSnapshot.getData());
                            foodDetails.itemId = documentSnapshot.getId();
                            foodDetails.restaurantId = id;
                            foodList.add(foodDetails);
                        });

                        status.complete(foodList);
                    } else {
                        status.complete(foodList);
                    }
                }
            });
        } else {
            status.complete(foodList);
        }

        return status;
    }
}
