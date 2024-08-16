package com.example.firstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityForgotPassword extends AppCompatActivity {

    TextInputEditText edtPhone;
    Button btnContinue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edtPhone = findViewById(R.id.edtPhoneNumber);
        btnContinue = findViewById(R.id.btnContinue);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtPhone.getText().toString().trim().isEmpty()) {
                    if ((edtPhone.getText().toString().trim()).length() == 10) {

                        progressBar.setVisibility(View.VISIBLE);
                        btnContinue.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + edtPhone.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                ActivityForgotPassword.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        btnContinue.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        btnContinue.setVisibility(View.VISIBLE);
                                        Toast.makeText(ActivityForgotPassword.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        btnContinue.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),ActivityVerification.class);
                                        intent.putExtra("mobile",edtPhone.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );

                    }else {
                        Toast.makeText(ActivityForgotPassword.this, "Please Enter Your Currect Phone Number", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ActivityForgotPassword.this, "Please Enter YOur Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}