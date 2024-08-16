package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ActivityVerification extends AppCompatActivity {

    EditText edtP1,edtP2,edtP3,edtP4,edtP5,edtP6;
    String getotpbackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        Button btnContinue = findViewById(R.id.btnContinue);

        edtP1 = findViewById(R.id.edtP1);
        edtP2 = findViewById(R.id.edtP2);
        edtP3 = findViewById(R.id.edtP3);
        edtP4 = findViewById(R.id.edtP4);
        edtP5 = findViewById(R.id.edtP5);
        edtP6 = findViewById(R.id.edtP6);

        TextView txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtPhoneNumber.setText(String.format("+91-%s",getIntent().getStringExtra("mobile")));

        getotpbackend = getIntent().getStringExtra("backendotp");

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtP1.getText().toString().trim().isEmpty() && !edtP2.getText().toString().trim().isEmpty() && !edtP3.getText().toString().trim().isEmpty() && !edtP4.getText().toString().trim().isEmpty() && !edtP5.getText().toString().trim().isEmpty() && !edtP6.getText().toString().trim().isEmpty()){
                    String entercodeotp = edtP1.getText().toString() +
                            edtP2.getText().toString() +
                            edtP3.getText().toString() +
                            edtP4.getText().toString() +
                            edtP5.getText().toString() +
                            edtP6.getText().toString();

                    if (getotpbackend != null) {
                        progressBar.setVisibility(View.VISIBLE);
                        btnContinue.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend, entercodeotp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        btnContinue.setVisibility(View.INVISIBLE);

                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(getApplicationContext(), ActivityReset.class);
                                            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(ActivityVerification.this, "Enter the currect otp", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }else {
                        Toast.makeText(ActivityVerification.this, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(ActivityVerification.this, "Otp Verify", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ActivityVerification.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        numbermove();
        TextView txtResend = findViewById(R.id.txtResend);

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        ActivityVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(ActivityVerification.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackendotp;
                                Toast.makeText(ActivityVerification.this, "otp send successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }

    private void numbermove() {
        edtP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()){
                    edtP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}