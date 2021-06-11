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

public class UserPaymentActivity extends AppCompatActivity
{

    EditText numtarjeta,fechavto,client_cvv,pais,cpp;
    Button adPayment;
    FirebaseAuth FAuth;
    FirebaseFirestore databaseReference;
    FirebaseDatabase firebaseDatabase;
    String ncard,dvto,cvv,country;
    String type = "Taberu Card";
    int pincode;
    String role="Cliente";

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_payment);

        numtarjeta = (EditText)findViewById(R.id.inputNcard);
        fechavto = (EditText)findViewById(R.id.inputFvto);
        client_cvv = (EditText)findViewById(R.id.inputCvv);
        pais = (EditText)findViewById(R.id.inputAddressFormCountry);
        cpp = (EditText)findViewById(R.id.inputAddressFormCPP);

        adPayment = (Button)findViewById(R.id.buttonAddressFormContinue);
        context = this;




        databaseReference = FirebaseFirestore.getInstance();
        FAuth = FirebaseAuth.getInstance();

        adPayment.setOnClickListener(new View.OnClickListener() {
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

        PaymentDetails addetails = new PaymentDetails(ncard,dvto,cvv,type,country,pincode);
        databaseReference.collection("Cliente").document(userId).collection("Metodos Pago").document()
                .set(addetails.getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(UserPaymentActivity.this,"Se ha agregado nuevo metodo de pago!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public boolean isValid(){
        numtarjeta.setError("");
        fechavto.setError("");
        client_cvv.setError("");
        pais.setError("");
        cpp.setError("");


        boolean isValid=false,isValidntar=false,isValidfecha=false,isValidcv=false,isValidcon=false;
        if(TextUtils.isEmpty(ncard))
        {
            numtarjeta.setError("Ingresa Número de Tarjeta");
        }
        else
        {
            if(numtarjeta.length()<16|numtarjeta.length()>16){
                numtarjeta.setError("Numero de tarjeta no valido");
            }else{

                isValidntar = true;
            }
        }
        if(TextUtils.isEmpty(dvto))
        {
            fechavto.setError("Se requiere fecha de vencimiento");
        }
        else
        {

            isValidfecha = true;
        }
        if(TextUtils.isEmpty(cvv))
        {
            client_cvv.setError("Ingresa CVV");
        }
        else
        {

            isValidcv = true;
        }
        if(TextUtils.isEmpty(country)){
            pais.setError("Ingresa Pais");
        }else{

            isValidcon = true;

        }

        isValid = (isValidntar && isValidfecha && isValidcv && isValidcon) ? true : false;
        return isValid;


    }


}