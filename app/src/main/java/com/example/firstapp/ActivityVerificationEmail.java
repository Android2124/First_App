package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityVerificationEmail extends AppCompatActivity {

    EditText edtEmail;
    Button btnContinue;
    String email;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_email);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edtEmail);
        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

    private void validateData() {
        email = edtEmail.getText().toString();
        if (email.isEmpty()) {
            edtEmail.setError("Required");
            edtEmail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ActivityVerificationEmail.this, "Please Check Your Email to Reset Password", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityVerificationEmail.this,ActivityLogin.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(ActivityVerificationEmail.this, "Failed to Reset Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}