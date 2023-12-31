package com.gabby.librarylogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvDontHave;
    private TextInputEditText edEmail, edPassword;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private TextView forgotPassword;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        initViews();
        clickListeners();
        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

    }

    private void clickListeners() {
        tvDontHave.setOnClickListener(this);
    }

    private void sendToMainActivity(String s) {
        startActivity(new Intent(this, MainActivity3.class).putExtra("name", s));
        finish();
    }

    private void checkEmailAndPassword() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
        if (edEmail.getText().toString().matches(emailPattern)) {
            if (edPassword.length() >= 8) {
                disable();
                try {

                    firebaseAuth.signInWithEmailAndPassword(edEmail.getText().toString(), edPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    try {
                                        FirebaseUser user = task.getResult().getUser();
                                        if (task.isSuccessful()) {
                                            databaseReference = FirebaseDatabase.getInstance().getReference("users");
                                            databaseReference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                        UserHelper pd = dataSnapshot.getValue(UserHelper.class);
                                                        if (Objects.equals(pd.email, edEmail.getText().toString())) {
                                                            SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                                            SharedPreferences.Editor myEdit = sh.edit();
                                                            myEdit.putString("Username", pd.name.toString());
                                                            sendToMainActivity(user.getDisplayName());
                                                        }

                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });

                                        } else {
                                            //                                    disable();
                                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    catch (Exception ex){
                                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void enable() {
        btnSignIn.setEnabled(true);
        btnSignIn.setTextColor(Color.rgb(255, 255, 255));
    }

    private void disable() {
        btnSignIn.setEnabled(false);
        btnSignIn.setTextColor(Color.argb(50, 255, 255, 255));
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(edEmail.getText())) {
            if (!TextUtils.isEmpty(edPassword.getText())) {
                enable();
            } else {
                disable();
            }
        } else {
            disable();
        }
    }

    private void initViews() {
        tvDontHave = findViewById(R.id.sign_in_dont_have);
        edEmail = findViewById(R.id.sign_in_email);
        edPassword = findViewById(R.id.sign_in_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        forgotPassword = findViewById(R.id.sign_in_forgot);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_dont_have:
                startActivity(new Intent(this, SignUpActivity.class));
                break;
        }
    }
}