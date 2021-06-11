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

public class User_Delete_Update_Address extends AppCompatActivity {

    EditText alias, nexterior, descripcion, pais, estado, cpp, calle01, calle02, referencias;
    Button deleteAdd,updaAdd;
    FirebaseAuth FAuth;
    FirebaseFirestore databaseReference;
    FirebaseDatabase firebaseDatabase;
    String ali, next, desc, country, cal01, cal02, ref, state;
    AddressDetails addetails;
    int pincode;
    String role = "Cliente";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dup_address);

        alias = (EditText) findViewById(R.id.inputAddressFormAlias);
        nexterior = (EditText) findViewById(R.id.inputAddressFormExtNumb);
        descripcion = (EditText) findViewById(R.id.inputAddressFormDescription);
        pais = (EditText) findViewById(R.id.inputAddressFormCountry);
        estado = (EditText) findViewById(R.id.inputAddressFormTown);
        cpp = (EditText) findViewById(R.id.inputAddressFormCPP);
        calle01 = (EditText) findViewById(R.id.inputAddressFormAddress1);
        calle02 = (EditText) findViewById(R.id.inputAddressFormAddress);
        referencias = (EditText) findViewById(R.id.inputAddressFormAddress2);

        deleteAdd = (Button) findViewById(R.id.buttonAddressFormContinue);
        updaAdd = (Button) findViewById(R.id.buttonAddressFormSkip);
        context = this;

        addetails = ClienteService.getInstance().getAddressById(
                getIntent().getStringExtra("ADDRESS_ID")
        );

        alias.setText(addetails.getAlias());
        nexterior.setText(addetails.getNumeroExterior());
        descripcion.setText(addetails.getDescripcion());
        pais.setText(addetails.getPais());
        estado.setText(addetails.getEstado());
        cpp.setText(addetails.getCPP()+"");
        calle01.setText(addetails.getCalle01());
        calle02.setText(addetails.getCalle02());
        referencias.setText(addetails.getRefencias());


        databaseReference = FirebaseFirestore.getInstance();
        FAuth = FirebaseAuth.getInstance();

        deleteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ali = alias.getText().toString().trim();
                next = nexterior.getText().toString().trim();
                desc = descripcion.getText().toString().trim();
                country = pais.getText().toString().trim();
                state = estado.getText().toString().trim();
                cal01 = calle01.getText().toString().trim();
                cal02 = calle02.getText().toString().trim();
                ref = referencias.getText().toString().trim();

                try {
                    pincode = Integer.parseInt(cpp.getText().toString());
                } catch (NumberFormatException nfe) {

                    System.out.println("Could not parse " + nfe);
                }


                deleteAddress();


            }
        });


        updaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ali = alias.getText().toString().trim();
                next = nexterior.getText().toString().trim();
                desc = descripcion.getText().toString().trim();
                country = pais.getText().toString().trim();
                state = estado.getText().toString().trim();
                cal01 = calle01.getText().toString().trim();
                cal02 = calle02.getText().toString().trim();
                ref = referencias.getText().toString().trim();

                try {
                    pincode = Integer.parseInt(cpp.getText().toString());
                } catch (NumberFormatException nfe) {

                    System.out.println("Could not parse " + nfe);
                }

                createNewAddress();


            }
        });



    }

    private void createNewAddress()
    {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dirId = addetails.getId();

        AddressDetails addetails = new AddressDetails(ali,next,desc,country,state,cal01,cal02,ref,pincode);
        databaseReference.collection("Cliente").document(userId).collection("Direcciones").document(dirId)
                .update(addetails.getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context,"Se ha modificado tu direccion!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void deleteAddress() {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String dirId = addetails.getId();

        AddressDetails addetails = new AddressDetails(ali, next, desc, country, state, cal01, cal02, ref, pincode);
        databaseReference.collection("Cliente").document(userId).collection("Direcciones").document(dirId)
                .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(User_Delete_Update_Address.this, "Se ha agregado nueva direccion!", Toast.LENGTH_SHORT).show();
                Intent b = new Intent(User_Delete_Update_Address.this, HomeActivity.class);
                startActivity(b);
            }
        });
    }
}