package com.google.bookticket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtEmail,edtPassword,edtPasswordAgain;
    private Button btnRegister;
    private TextView tvLogin;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    FirebaseAuth mauth;
    FirebaseUser muser;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtPasswordAgain = findViewById(R.id.edt_againpassword);
        btnRegister = findViewById(R.id.btn_register);
        progressDialog= new ProgressDialog(this);
        tvLogin = findViewById(R.id.tv_login);
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });

    }

    private void PerforAuth() {
        String email= edtEmail.getText().toString();
        String password=edtPassword.getText().toString();
        String Cofirm=edtPasswordAgain.getText().toString();

        if (!email.matches(emailPattern)){
            edtEmail.setError("Enter context Email");
        }else if(password.isEmpty() || password.length()<6){
            edtPassword.setError("Enter proper password");
        }else if(!password.equals(Cofirm)){
            edtPasswordAgain.setError("Password not match");
        }else {
            progressDialog.setMessage("Please wait for register...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegisterActivity.this,"Registration",Toast.LENGTH_LONG).show();

                        db = FirebaseFirestore.getInstance();

                        Map<String,Object> user= new HashMap<>();
                        user.put("userEmail",email);
                        user.put("userFullName","");
                        user.put("userImages","");
                        user.put("userPassword",password);
                        user.put("userPhone","");

                        db.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    private void sendUserToNextActivity() {
        Intent intent= new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}