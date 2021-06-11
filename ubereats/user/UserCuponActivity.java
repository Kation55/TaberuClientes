package com.example.ubereats.user;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ubereats.R;
import com.example.ubereats.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserCuponActivity extends AppCompatActivity
{

    EditText nomcupon,codigocupon,cantidadcupon;
    Button adCupon;
    FirebaseAuth FAuth;
    FirebaseFirestore databaseReference;
    FirebaseDatabase firebaseDatabase;
    String ncup,codc;
    String type = "Taberu Card";
    int canticup;
    String role="Cliente";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_cupones);

        nomcupon = (EditText)findViewById(R.id.inputNcodigo);
        codigocupon = (EditText)findViewById(R.id.inputcod);
        cantidadcupon = (EditText)findViewById(R.id.inputcantidad);

        adCupon = (Button)findViewById(R.id.buttonAddressFormContinue);
        context = this;


        databaseReference = FirebaseFirestore.getInstance();
        FAuth = FirebaseAuth.getInstance();

        adCupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ncup = nomcupon.getText().toString().trim();
                codc = codigocupon.getText().toString().trim();

                try {
                    canticup = Integer.parseInt(cantidadcupon.getText().toString());
                }
                catch(NumberFormatException nfe) {

                    System.out.println("Could not parse " + nfe);
                }

                if(isValid())
                {
                    createNewPayment();
                }

            }
        });



    }

    private void createNewPayment()
    {

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        CuponDetails addetails = new CuponDetails(ncup,codc,canticup);
        databaseReference.collection("Cliente").document(userId).collection("Codigos Promocionales").document()
                .set(addetails.getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UserCuponActivity.this,"Se ha agregado nuevo código promocional!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public boolean isValid(){
        nomcupon.setError("");
        codigocupon.setError("");


        boolean isValid=false,isValidnc=false,isValidcc=false;
        if(TextUtils.isEmpty(ncup))
        {
            nomcupon.setError("Ingresa un nombre para tu cupon");
        }
        else
        {
            isValidnc = true;
        }
        if(TextUtils.isEmpty(codc))
        {
            codigocupon.setError("Necesitas un Codigo Promocional");
        }
        else
        {

            isValidcc= true;
        }

        isValid = (isValidnc && isValidcc) ? true : false;
        return isValid;


    }


}