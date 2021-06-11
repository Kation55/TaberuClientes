package com.example.ubereats.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;


import com.example.ubereats.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRegisterActivity extends AppCompatActivity
{

    EditText Uname,Email,Pass,cpass,mobileno,houseno,area,pincode;
    Button signup;
    FirebaseAuth FAuth;
    FirebaseFirestore db;
    String uname,ctegoria,emailid,password,confpassword,mobile;
    String role="Cliente";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);

        Uname = (EditText)findViewById(R.id.inputRegisterName);
        Email = (EditText)findViewById(R.id.inputRegisterEmail);
        Pass = (EditText)findViewById(R.id.inputRegisterPassword);
        cpass = (EditText)findViewById(R.id.inputRegisterPassword2);
        mobileno = (EditText)findViewById(R.id.inputRegisterPhone);

        signup = (Button)findViewById(R.id.button4);



        db = FirebaseFirestore.getInstance();
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = Uname.getText().toString().trim();
                emailid = Email.getText().toString().trim();
                mobile = mobileno.getText().toString().trim();
                password = Pass.getText().toString().trim();
                confpassword = cpass.getText().toString().trim();

                if (isValid()){
                    final ProgressDialog mDialog = new ProgressDialog(UserRegisterActivity.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registro en proceso, por favor espera");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                db.collection("Cliente").document(useridd).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Cliente cliente = new Cliente(uname, emailid, mobile);

                                        db.collection("Cliente").document(useridd)
                                                .set(cliente.getMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                                                            builder.setTitle("Un regalo para ti");
                                                            builder.setMessage("Te obsequiamos 10 % de descuento con usando el código TABPRI01");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();

                                                                    startActivity(new Intent(UserRegisterActivity.this,LoginActivity.class));
                                                                    finish();
                                                                }
                                                            });
                                                            AlertDialog Alert = builder.create();
                                                            Alert.show();
                                                        }else{
                                                            mDialog.dismiss();
                                                            ReusableCode.ShowAlert(UserRegisterActivity.this,"Error",task.getException().getMessage());
                                                        }
                                                    }
                                                });

                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });
                }
//
            }
        });



    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setError("");
        Uname.setError("");
        Pass.setError("");
        mobileno.setError("");
        cpass.setError("");

        boolean isValid=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false;
        if(TextUtils.isEmpty(uname)){
            Uname.setError("Ingresa Nombre");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setError("Se requiere correo");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setError("Ingresa un correo válido");
            }
        }
        if(TextUtils.isEmpty(password)){
            Pass.setError("Ingresa Password");
        }else{
            if(password.length()<8){
                Pass.setError("Tu Password es muy sencillo");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            cpass.setError("Ingresa Password nuevamente");
        }else{
            if(!password.equals(confpassword)){
                cpass.setError("Password diferentes");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobile)){
            mobileno.setError("Tu teléfono es requerido");
        }else{
            if(mobile.length()<10){
                mobileno.setError("Teléfono invalido");
            }else{
                isValidmobilenum = true;
            }
        }


        isValid = (isValidconfpassword && isValidpassword && isValidemail && isValidmobilenum && isValidname) ? true : false;
        return isValid;


    }
}
