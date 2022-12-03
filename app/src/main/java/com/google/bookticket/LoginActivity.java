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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    EditText login_userEmail, login_userPassword;
    TextView login_gotoRegister;
    Button login_btnLogin;
    FirebaseDatabase db;
    FirebaseFirestore fdb;
    CollectionReference coll_users;
    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mauth;
    FirebaseUser muser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initID();
        progressDialog= new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
        muser=mauth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        DatabaseReference mRef = db.getReference().child("users");
        fdb = FirebaseFirestore.getInstance();
        coll_users = fdb.collection("users");

        login_userEmail=findViewById(R.id.edt_email);
        login_userPassword=findViewById(R.id.edt_password);
        login_btnLogin=findViewById(R.id.btn_login);
        login_gotoRegister=findViewById(R.id.tv_register);

        login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = login_userEmail.getText().toString().trim();
                String userPassword = login_userPassword.getText().toString().trim();


                if(login_userEmail.getText().toString().trim().isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                } else {

                    coll_users.whereEqualTo("userEmail", userEmail)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()) {
                                        if(task.getResult().size() != 0) {
                                            coll_users.whereEqualTo("userEmail", userEmail)
                                                    .whereEqualTo("userPassword", userPassword)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                            if(task.isSuccessful()) {

                                                                if(task.getResult().size() != 0) {
                                                                    String userID = "";
                                                                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                                                                    for(QueryDocumentSnapshot snapshot: task.getResult()) {
                                                                        userID = snapshot.getId();
                                                                    }
                                                                    gotoMain(userID);
                                                                } else {
                                                                    Toast.makeText(LoginActivity.this, "Mật khẩu sai", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        }
                                                    });
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Email chưa đăng ký.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        login_gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regiser();
            }
        });


    }

    private void regiser() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }
    private void gotoMain(String userID) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle= new Bundle();
        bundle.putString("userID",userID);
        intent.putExtra("userID", bundle);
        startActivity(intent);
    }
    public void initID() {
        login_userEmail = findViewById(R.id.edt_email);
        login_userPassword = findViewById(R.id.edt_password);
        login_gotoRegister = findViewById(R.id.tv_register);
        login_btnLogin = findViewById(R.id.btn_login);
    }
}