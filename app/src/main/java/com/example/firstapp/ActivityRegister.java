package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ActivityRegister extends AppCompatActivity {

    Button btnRegister;
    TextView txtSignIn;
    CheckBox checkBox;
    EditText edtEmail,edtUserName,edtPassword;
    FirebaseAuth mAuth;
    ImageView imgGoogle;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        txtSignIn = findViewById(R.id.txtSignin);
        checkBox = findViewById(R.id.checkBox);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserName = findViewById(R.id.edtUserName);
        mAuth = FirebaseAuth.getInstance();
        imgGoogle = findViewById(R.id.imgGoogle);

        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLogIn();
            }
        });
        progressRequest();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edtEmail.getText().toString();
                String user = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();

                if (email.isEmpty() || !email.contains("@")) {
                    edtEmail.setError("Please enter your email address");
                } else if (user.isEmpty()) {
                    edtUserName.setError("Please enter your user name");
                } else if (password.isEmpty()) {
                    edtPassword.setError("Please enter your password!");
                } else if (!checkBox.isChecked()){
//                    checkBox.setError("You must agree to the terms and condition!");
                    Toast.makeText(ActivityRegister.this, "You must agree the terms and condition", Toast.LENGTH_SHORT).show();
                }else {
//                    checkBox.setError(null);
//                    Intent intent = new Intent(ActivityRegister.this, ActivityHome.class);
//                    startActivity(intent);

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendToNextUser();
                                Toast.makeText(ActivityRegister.this, "Regigster Succesfully", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ActivityRegister.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

    }

    private void progressLogIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,101);
    }

    private void progressRequest() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    private void sendToNextUser() {
        Intent intent = new Intent(ActivityRegister.this, ActivityHome.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            }catch (ApiException e){
                Toast.makeText(getApplicationContext(), "Error in geting Google's information", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            Intent intent  =new Intent(ActivityRegister.this,ActivityHome.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Problem found in firebase login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}