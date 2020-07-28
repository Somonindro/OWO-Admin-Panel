package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewAdminActivity extends AppCompatActivity {

    private Switch approveShop,maintainShop,addProducts,
            updateProducts,createOffers,maintainUsers,messaging;
    private EditText newAdminMobileNumber, newAdminPassword, newAdminConfirmPassword;
    private Button createNewAdminBtn;
    private ProgressBar progressBar;
    private String admin_email;
    private ImageView s_password, s_c_password;
    private Boolean isShowPassword = false, isShowConfirmPassword = false;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_admin);

        getSupportActionBar().hide();

        approveShop=(Switch)findViewById(R.id.approve_shop);
        maintainShop=(Switch)findViewById(R.id.maintain_shop);
        addProducts=(Switch)findViewById(R.id.add_products);
        updateProducts=(Switch)findViewById(R.id.update_products);
        createOffers=(Switch)findViewById(R.id.create_offers);
        maintainUsers=(Switch)findViewById(R.id.maintain_users);
        messaging=(Switch)findViewById(R.id.messaging);
        newAdminMobileNumber = (EditText)findViewById(R.id.new_admin_mobile_number);
        newAdminPassword = (EditText)findViewById(R.id.new_admin_password);
        newAdminConfirmPassword = (EditText)findViewById(R.id.new_admin_confirm_password);
        createNewAdminBtn=(Button)findViewById(R.id.create_new_admin_btn);
        s_password = findViewById(R.id.show_password);
        s_c_password = findViewById(R.id.show_confirmed_password);
        progressBar = findViewById(R.id.complete_progress);

        s_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowPassword) {
                    newAdminPassword.setTransformationMethod(new PasswordTransformationMethod());
                    s_password.setImageResource(R.drawable.ic_visibility_off);
                    isShowPassword = false;

                }else{
                    newAdminPassword.setTransformationMethod(null);
                    s_password.setImageResource(R.drawable.ic_visibility);
                    isShowPassword = true;
                }
            }
        });

        s_c_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isShowConfirmPassword) {
                    newAdminConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                    s_c_password.setImageResource(R.drawable.ic_visibility_off);
                    isShowConfirmPassword = false;

                }else{
                    newAdminConfirmPassword.setTransformationMethod(null);
                    s_c_password.setImageResource(R.drawable.ic_visibility);
                    isShowConfirmPassword = true;
                }
            }
        });

        createNewAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                verification();
            }
        });

    }

    private void verification() {
        String mobile = newAdminMobileNumber.getText().toString();
        String password = newAdminPassword.getText().toString();
        String confirmpassword = newAdminConfirmPassword.getText().toString();

        if(mobile.isEmpty())
        {
            newAdminMobileNumber.setError("Please enter an email address");
            newAdminMobileNumber.requestFocus();
        }
        else if(mobile.length() < 11)
        {
            newAdminMobileNumber.setError("Please enter a valid mobile number");
            newAdminMobileNumber.requestFocus();
        }
        else if(password.isEmpty())
        {
            newAdminPassword.setError("Please enter a password");
            newAdminPassword.requestFocus();
        }
        else if(confirmpassword.isEmpty())
        {
            newAdminConfirmPassword.setError("Please enter password to confirm");
            newAdminConfirmPassword.requestFocus();
        }

        else if(!confirmpassword.equals(password))
        {
            newAdminConfirmPassword.setError("Password did not match");
            newAdminConfirmPassword.requestFocus();
        }
        else
        {

            Boolean approve_shop, maintain_shop, add_products, update_products,
                    create_offers, maintain_users, messaging_;

            approve_shop = approveShop.isChecked();
            maintain_shop = maintainShop.isChecked();
            add_products = addProducts.isChecked();
            update_products = updateProducts.isChecked();
            create_offers = createOffers.isChecked();
            maintain_users = maintainUsers.isChecked();
            messaging_ = messaging.isChecked();

            if(!approve_shop && !maintain_shop && !add_products &&
                    !update_products && !create_offers && !maintain_users && !messaging_)
            {
                Toast.makeText(CreateNewAdminActivity.this, "Please enter a permission", Toast.LENGTH_SHORT).show();
            }

            else
            {
                final com.example.model.semi_admins new_Semi_Admin = new com.example.model.semi_admins(approve_shop, maintain_shop,
                        add_products, update_products, create_offers, maintain_users, messaging_);

                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final FirebaseUser user1 = mAuth.getCurrentUser();
                admin_email = user1.getEmail();

                if(user1 != null)
                {
                    final DatabaseReference myRef = database.getReference().child("Semi Admins").child(mobile);

                    String email = newAdminMobileNumber.getText().toString()+"@gmail.com";

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(CreateNewAdminActivity.this, "New admin authentication successful", Toast.LENGTH_SHORT).show();

                                        FirebaseAuth.getInstance().signOut();

                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(admin_email, MainActivity.login_password)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if(task.isSuccessful())
                                                        {
                                                            myRef.setValue(new_Semi_Admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if(task.isSuccessful())
                                                                    {
                                                                        Toast.makeText(CreateNewAdminActivity.this, "New Admin added successfully", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(CreateNewAdminActivity.this, HomeActivity.class);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }

                                                                    else
                                                                    {
                                                                        Toast.makeText(CreateNewAdminActivity.this, "Can not write semi admin access to database", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                    }
                                    else {
                                        Toast.makeText(CreateNewAdminActivity.this, "Can not authenticate new admin", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        }

    }
}
