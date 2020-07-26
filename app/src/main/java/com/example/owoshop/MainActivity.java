package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText email_address, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        loginButton=(Button)findViewById(R.id.login_btn);
        email_address = (EditText)findViewById(R.id.admin_email_address);
        password = (EditText)findViewById(R.id.admin_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                varify();
            }
        });

    }

    private void varify() {

        String email = email_address.getText().toString();
        String login_password = password.getText().toString();

        if(email.isEmpty())
        {
            email_address.setError("Please enter an email address");
            email_address.requestFocus();
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_address.setError("Please enter a valid email address");
            email_address.requestFocus();
        }

        else if(login_password.isEmpty())
        {
            password.setError("Please enter a password");
            password.requestFocus();
        }

        else
        {
            mAuth.signInWithEmailAndPassword(email, login_password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(MainActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }


}
