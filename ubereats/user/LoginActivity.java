package com.example.ubereats.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubereats.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.ubereats.home.HomeActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;
    Button Signin;
    TextView  signup,forgotp;
    FirebaseAuth Fauth;
    String emailid,pwd;
    FirebaseFirestore databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        try{

            email = (EditText)findViewById(R.id.inputLoginEmail);
            pass = (EditText)findViewById(R.id.inputLoginPassword);
            Signin = (Button)findViewById(R.id.buttonLogin);
            signup = (TextView) findViewById(R.id.textButtonRegister);
            forgotp = (TextView) findViewById(R.id.textButtonForgotPassword);

            databaseReference = FirebaseFirestore.getInstance();
            Fauth = FirebaseAuth.getInstance();

            Signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    emailid = email.getText().toString().trim();
                    pwd = pass.getText().toString().trim();

                    if(isValid()){

                        final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Ingresando a tu cuenta");
                        mDialog.show();

                        Fauth.signInWithEmailAndPassword(emailid,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    mDialog.dismiss();

                                    if(Fauth.getCurrentUser().isEmailVerified())
                                    {
                                        String useridd = Fauth.getCurrentUser().getUid();
                                        DocumentReference docRef = databaseReference.collection("Cliente").document(useridd);

                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists())
                                                    {
                                                        Cliente.setCliente(Cliente.of(document.getData()));
                                                    }

                                                }
                                            }
                                        });

                                        mDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Se ha ingresado correctamente", Toast.LENGTH_SHORT).show();
                                        Intent Z = new Intent(LoginActivity.this,HomeActivity.class);
                                        startActivity(Z);
                                        finish();

                                    }else{
                                        ReusableCode.ShowAlert(LoginActivity.this,"Verificacion Fallida","No has verificado tu correo");

                                    }
                                }else{
                                    mDialog.dismiss();
                                    ReusableCode.ShowAlert(LoginActivity.this,"Error",task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,UserRegisterActivity.class));
                    finish();
                }
            });
            forgotp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
                    finish();
                }
            });
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void createUser(View view) {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }

    String emailpattern  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid(){

        email.setError("");
        pass.setError("");

        boolean isvalid=false,isvalidemail=false,isvalidpassword=false;
        if(TextUtils.isEmpty(emailid)){
            email.setError("Se requiere correo");
        }else{
            if(emailid.matches(emailpattern)){
                isvalidemail=true;
            }else{
                email.setError("Correo invalido");
            }
        }
        if(TextUtils.isEmpty(pwd)){

            pass.setError("Se requiere contraseña");
        }else{
            isvalidpassword=true;
        }
        isvalid=(isvalidemail && isvalidpassword)?true:false;
        return isvalid;
    }

}