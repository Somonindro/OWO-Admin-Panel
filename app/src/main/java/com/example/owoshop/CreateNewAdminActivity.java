package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.hashing.sha_256;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import static com.example.hashing.sha_256.getSHA;

public class CreateNewAdminActivity extends AppCompatActivity {

    private Switch approveShop,maintainShop,addProducts,
            updateProducts,createOffers,maintainUsers,messaging;

    private EditText newAdminMobileNumber, newAdminPassword, newAdminConfirmPassword;

    private Button createNewAdminBtn;

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

        createNewAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            try {
                String hashed_password = sha_256.toHexString(getSHA(password));

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
                    com.example.model.semi_admins new_Semi_Admin = new com.example.model.semi_admins(hashed_password, approve_shop, maintain_shop,
                            add_products, update_products, create_offers, maintain_users, messaging_);

                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();

                    if(user!=null)
                    {
                        DatabaseReference myRef = database.getReference().child("Semi Admins").child(mobile);

                        myRef.setValue(new_Semi_Admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(CreateNewAdminActivity.this, "New Admin added successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateNewAdminActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }


            } catch (NoSuchAlgorithmException e) {
                Toast.makeText(CreateNewAdminActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }
}
