package com.example.firstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogin extends AppCompatActivity {

    EditText edtEmail,edtPassword;
    Button btnSignIn;
    TextView txtForgotPassword,txtRegister;
    FirebaseAuth mAuth;
//    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        mAuth = FirebaseAuth.getInstance();
//        mUser = mAuth.getCurrentUser();

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,password;
                email = String.valueOf(edtEmail.getText());
                password = String.valueOf(edtPassword.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ActivityLogin.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(ActivityLogin.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityLogin.this, ActivityHome.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(ActivityLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
 
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ActivityLogin.this);
                View view = LayoutInflater.from(ActivityLogin.this).inflate(R.layout.bottom_sheet,null);
                bottomSheetDialog.setContentView(view);
                bottomSheetDialog.show();

                Button btnContinue = view.findViewById(R.id.btnContinue);

                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });

                RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
                RadioButton rdPhone = view.findViewById(R.id.rdPhone);
                RadioButton rdEmail = view.findViewById(R.id.rdEmail);

                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();

                        if (selectedId == rdPhone.getId()){
                            Intent intent = new Intent(ActivityLogin.this,ActivityForgotPassword.class);
                            startActivity(intent);
                        }

                        if (selectedId == rdEmail.getId()){
                            Intent intent = new Intent(ActivityLogin.this, ActivityVerificationEmail.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(ActivityLogin.this,ActivityHome.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}