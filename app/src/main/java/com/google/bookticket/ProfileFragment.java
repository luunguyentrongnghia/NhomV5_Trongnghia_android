package com.google.bookticket;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    Button profile_Update , profile_ok, profile_lichsudatve, profile_logout;
    EditText profile_userEmail, profile_userPhone, profile_userFullname;
    Button ChangePass;
    FirebaseFirestore fdb;
    ProgressDialog progressDialog;
    CollectionReference coll_users;

    MainActivity mainActivity;
    String userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        progressDialog= new ProgressDialog(getActivity());
        initID(view);

        fdb = FirebaseFirestore.getInstance();
        coll_users = fdb.collection("users");

        coll_users.document(mainActivity.userID).
                get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            profile_userEmail.setText(documentSnapshot.getString("userEmail"));
                            profile_userPhone.setText(documentSnapshot.getString("userPhone"));
                            profile_userFullname.setText(documentSnapshot.getString("userFullName"));
                        }
                    }
                });



        profile_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_ok.setVisibility(View.VISIBLE);
                profile_Update.setVisibility(View.INVISIBLE);
                profile_userEmail.setEnabled(true);
                profile_userPhone.setEnabled(true);
                profile_userFullname.setEnabled(true);

            }
        });

        profile_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile_Update.setVisibility(View.VISIBLE);
                profile_ok.setVisibility(View.INVISIBLE);

                profile_userEmail.setEnabled(false);
                profile_userPhone.setEnabled(false);
                profile_userFullname.setEnabled(false);
                Map<String, Object> update = new HashMap<>();
                update.put("userFullName", profile_userFullname.getText().toString().trim());
                update.put("userEmail", profile_userEmail.getText().toString().trim());
                update.put("userPhone", profile_userPhone.getText().toString().trim());
                coll_users.document(mainActivity.userID).update(update)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.change_password);

                EditText old_pass = (EditText) dialog.findViewById(R.id.edt_old_Password);
                EditText new_pass = (EditText) dialog.findViewById(R.id.edt_new_Password);
                EditText check_pass = (EditText) dialog.findViewById(R.id.edt_re_Password);
                Button btnXacnhan = (Button) dialog.findViewById(R.id.btnXacnhan);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

                coll_users = fdb.collection("users");

                coll_users.document(mainActivity.userID).
                        get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()) {
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    old_pass.setText(documentSnapshot.getString("userPassword"));
                                }
                            }
                        });

                String old_Password= old_pass.getText().toString();
                String new_Password=new_pass.getText().toString();
                String Check_new=check_pass.getText().toString();

                btnXacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!old_Password.equals(new_Password)){
                            new_pass.setError("Please Enter new Password");
                        }else if(!new_Password.equals(Check_new)){
                            check_pass.setError("Password not match");
                        }else {
                            progressDialog.setMessage("Please wait for Change...");
                            progressDialog.setTitle("Change Password");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();

                            Map<String, Object> update = new HashMap<>();
                            update.put("userPassword", new_pass.getText().toString().trim());
                            coll_users.document(mainActivity.userID).update(update)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });

                        }
                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        profile_lichsudatve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getContext(),LichSuDatVeActivity.class);
                Bundle bundle= new Bundle();
                bundle.putString("userID",mainActivity.getUserID());
                intent.putExtra("userID",bundle);
                startActivity(intent);
            }
        });
        profile_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getContext(),LoginActivity.class);
               startActivity(intent);
                Toast.makeText(getContext(), "đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initID(View view) {
        profile_logout = view.findViewById(R.id.profile_logout);
        profile_lichsudatve = view.findViewById(R.id.profile_lichsudatve);
        profile_Update = view.findViewById(R.id.profile_Update);
        profile_ok = view.findViewById(R.id.profile_ok);
        profile_userEmail = view.findViewById(R.id.profile_userEmail);
        profile_userPhone = view.findViewById(R.id.profile_userPhone);
        profile_userFullname = view.findViewById(R.id.profile_userFullname);
        ChangePass=view.findViewById(R.id.profile_ChangePass);
    }

}