package com.example.ubereats.user;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ubereats.home.HomeUserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


import com.example.ubereats.R;
import com.example.ubereats.home.HomeActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class User_Delete_Update_Card extends AppCompatActivity {

    EditText numtarjeta,fechavto,client_cvv,pais,cpp;
    Button deleteCard, updaCard;
    FirebaseAuth FAuth;
    FirebaseFirestore databaseReference;
    FirebaseDatabase firebaseDatabase;
    String ncard,dvto,cvv,country;
    String type = "Taberu Card";
    int pincode;
    PaymentDetails paydet;
    String role="Cliente";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dup_cards);

        numtarjeta = (EditText)findViewById(R.id.inputNcard);
        fechavto = (EditText)findViewById(R.id.inputFvto);
        client_cvv = (EditText)findViewById(R.id.inputCvv);
        pais = (EditText)findViewById(R.id.inputAddressFormCountry);
        cpp = (EditText)findViewById(R.id.inputAddressFormCPP);

        deleteCard = (Button) findViewById(R.id.buttonAddressFormContinue);
        updaCard = (Button) findViewById(R.id.buttonAddressFormSkip);
        context = this;

        paydet = ClienteService.getInstance().getCardById(
                getIntent().getStringExtra("CARD_ID")
        );

        numtarjeta.setText(paydet.getNumerodeTarjeta());
        fechavto.setText(paydet.getFechavto());
        client_cvv.setText(paydet.getCVV());
        pais.setText(paydet.getPais());
        cpp.setText(paydet.getCPP()+"");




        databaseReference = FirebaseFirestore.getInstance();
        FAuth = FirebaseAuth.getInstance();

        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ncard = numtarjeta.getText().toString().trim();
                dvto = fechavto.getText().toString().trim();
                cvv = client_cvv.getText().toString().trim();
                country = pais.getText().toString().trim();

                try {
                    pincode = Integer.parseInt(cpp.getText().toString());
                } catch (NumberFormatException nfe) {

                    System.out.println("Could not parse " + nfe);
                }


                deleteAddress();


            }
        });


        updaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ncard = numtarjeta.getText().toString().trim();
                dvto = fechavto.getText().toString().trim();
                cvv = client_cvv.getText().toString().trim();
                country = pais.getText().toString().trim();

                String tarjeta = ncard;
                tarjeta = tarjeta.substring(0,1);

                if(tarjeta.equals("4"))
                {
                    type = "Visa";
                }
                if(tarjeta.equals("5"))
                {
                    type = "Master Card";
                }
                if(tarjeta.equals("3"))
                {
                    type = "American Express";
                }

                try {
                    pincode = Integer.parseInt(cpp.getText().toString());
                }
                catch(NumberFormatException nfe) {

                    System.out.println("Could not parse " + nfe);
                }
                createNewAddress();


            }
        });



    }

    private void createNewAddress()
    {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dirId = paydet.getId();

        PaymentDetails addetails = new PaymentDetails(ncard,dvto,cvv,type,country,pincode);
        databaseReference.collection("Cliente").document(userId).collection("Metodos Pago").document(dirId)
                .update(addetails.getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Se ha modificado metodo de pago!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void deleteAddress() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dirId = paydet.getId();

        databaseReference.collection("Cliente").document(userId).collection("Metodos Pago").document(dirId)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(User_Delete_Update_Card.this, "Se ha borrado tarjeta!", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(User_Delete_Update_Card.this, HomeActivity.class);
                startActivity(b);
            }
        });
    }
}