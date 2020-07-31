package com.example.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText nameUpdate,descriptionUpdate,priceUpdate,discountUpdate;
    private Button updateButton;
    private ImageView imageUpdate;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        nameUpdate=(EditText)findViewById(R.id.product_name_update);
        descriptionUpdate=(EditText)findViewById(R.id.product_description_update);
        priceUpdate=(EditText)findViewById(R.id.product_price_update);
        discountUpdate=(EditText)findViewById(R.id.product_discount_update);
        updateButton=(Button)findViewById(R.id.update_products_button);
        imageUpdate=(ImageView)findViewById(R.id.product_image_update);
        loadingbar=new ProgressDialog(this);

        final com.example.model.Products products = (Products) getIntent().
                getSerializableExtra("Products");
        Picasso.get().load(products.getImage()).into(imageUpdate);
        nameUpdate.setText(products.getPname());
        descriptionUpdate.setText(products.getDescription());
        priceUpdate.setText(products.getPrice());
        discountUpdate.setText(products.getDiscount());
        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProductActivity.this, "Product Image can not be changed.", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Update Product");
                loadingbar.setMessage("Please wait, we are updating the particular product.");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

                products.setPname(nameUpdate.getText().toString());
                products.setDescription(descriptionUpdate.getText().toString());
                products.setPrice(priceUpdate.getText().toString());
                products.setDiscount(discountUpdate.getText().toString());

                reference.child(products.getPid()).setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateProductActivity.this, "Product information updated successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateProductActivity.this,HomeActivity.class);
                            loadingbar.dismiss();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else
                        {
                            Toast.makeText(UpdateProductActivity.this, "Can not update product's data"+task.getException().toString(), Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }
                    }
                });

            }
        });
    }
}
