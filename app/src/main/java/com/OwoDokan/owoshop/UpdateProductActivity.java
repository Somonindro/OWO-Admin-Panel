package com.OwoDokan.owoshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.OwoDokan.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText descriptionUpdate, priceUpdate, discountUpdate, quantity_update;
    private Button updateButton;
    private ImageView imageUpdate;
    private ProgressDialog loadingbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Button calculate_new_price, delete_product;
    private ImageView back_to_home;
    private TextView updated_price;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        descriptionUpdate = (EditText)findViewById(R.id.product_description_update);
        priceUpdate = (EditText)findViewById(R.id.product_price_update);
        discountUpdate = (EditText)findViewById(R.id.product_discount_update);
        updateButton = (Button)findViewById(R.id.update_products_button);
        imageUpdate = (ImageView)findViewById(R.id.product_image_update);
        collapsingToolbarLayout = findViewById(R.id.product_name_update);
        calculate_new_price = findViewById(R.id.new_price_update_calculate);
        delete_product = findViewById(R.id.delete_products_button);
        back_to_home = findViewById(R.id.back_to_home);
        updated_price = findViewById(R.id.new_price_update);
        quantity_update = findViewById(R.id.product_quantity_update);

        loadingbar =new ProgressDialog(this);

        final com.OwoDokan.model.Products products = (Products) getIntent().getSerializableExtra("Products");

        Picasso.get().load(products.getProduct_image()).into(imageUpdate);
        collapsingToolbarLayout.setTitle(products.getProduct_name());
        descriptionUpdate.setText(products.getProduct_description());
        priceUpdate.setText(products.getProduct_price());
        discountUpdate.setText(products.getProduct_discount());
        quantity_update.setText(products.getProduct_quantity());

        calculate_new_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double discounted_price = Double.parseDouble(priceUpdate.getText().toString()) - Double.parseDouble(discountUpdate.getText().toString());
                updated_price.setText(String.valueOf(discounted_price));
            }
        });

        imageUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProductActivity.this, "Product Image can not be changed.", Toast.LENGTH_SHORT).show();
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CharSequence options[]=new CharSequence[]{"Yes","No"};
                final AlertDialog.Builder builder=new AlertDialog.Builder(UpdateProductActivity.this);
                builder.setTitle("Are you sure you want to delete this product?");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if (i==0)
                        {

                            loadingbar.setTitle("Update Product");
                            loadingbar.setMessage("Please wait while we are updating the product...");
                            loadingbar.setCanceledOnTouchOutside(false);
                            loadingbar.show();


                            StorageReference ProductImagesRef = FirebaseStorage.getInstance().getReferenceFromUrl(products.getProduct_image());

                            ProductImagesRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(UpdateProductActivity.this, "Product Image removed successfully", Toast.LENGTH_SHORT).show();

                                    reference.child(String.valueOf(products.getProduct_id())).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(UpdateProductActivity.this, "Product removed", Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
                                                finish();
                                            }
                                            else
                                            {
                                                Toast.makeText(UpdateProductActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                loadingbar.dismiss();
                                            }
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(UpdateProductActivity.this, "Can not delete product.  Try again", Toast.LENGTH_SHORT).show();
                                    loadingbar.dismiss();
                                }
                            });


                        }

                        else if(i == 1)
                        {

                            dialog.cancel();
                        }

                    }
                });

                builder.show();

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingbar.setTitle("Update Product");
                loadingbar.setMessage("Please wait, we are updating the product description");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();


                products.setProduct_description(descriptionUpdate.getText().toString());
                products.setProduct_price(priceUpdate.getText().toString());
                products.setProduct_discount(discountUpdate.getText().toString());
                products.setProduct_quantity(quantity_update.getText().toString());

                reference.child(String.valueOf(products.getProduct_id())).setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Toast.makeText(UpdateProductActivity.this, "Product information updated successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateProductActivity.this, ProductAvailabilityActivity.class);
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
