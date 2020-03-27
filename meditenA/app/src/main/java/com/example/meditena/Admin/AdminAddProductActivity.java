package com.example.meditena.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meditena.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AdminAddProductActivity extends AppCompatActivity {
    private String CategoryName, Description, Price, Discount, DPrice;
    private String  Pname, saveCurrentDate, saveCurrentTime;
    private int  DiscPrice = 0;
    private Button addnewproductbtn;
    private ImageView inputProductImage;
    private EditText inputProductName, inputProductDescription, inputProductPrice, inputProductDiscount;
    private static final int GalleryPick = 1;
    private DatabaseReference ProductRef;
    private Uri imageUri;
    private String productRandomKey, downloadImageUrl, newDiscount = "";
    private StorageReference ProdectImagesRef;

    private ProgressDialog loadingBar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_product);

        CategoryName = Objects.requireNonNull(getIntent().getExtras()).get("category").toString();
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        ProdectImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        addnewproductbtn = (Button)findViewById(R.id.add_new_productbtn);
        inputProductName = (EditText)findViewById(R.id.product_name);
        inputProductDiscount = (EditText)findViewById(R.id.product_discount);
        inputProductDescription = (EditText)findViewById(R.id.product_description);
        inputProductPrice = (EditText)findViewById(R.id.product_price);
        inputProductImage = (ImageView) findViewById(R.id.add_product_image);
        loadingBar = new ProgressDialog(this);

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();

            }
        });
        addnewproductbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateProductData();
            }
        });

    }



    private void openGallery() {
        Intent galleryintent = new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            inputProductImage.setImageURI(imageUri);
        }
    }
    private void validateProductData() {
        Description = inputProductDescription.getText().toString();
        Price = inputProductPrice.getText().toString();
        Pname = inputProductName.getText().toString();
        Discount = inputProductDiscount.getText().toString();
        int valueDiscountPrice = ((((Integer.valueOf(Price))) * Integer.valueOf(Discount))/ 100);
        DiscPrice =  ((Integer.valueOf(Price) - (valueDiscountPrice)));
        DPrice = Integer.toString(DiscPrice);

        if (imageUri == null){
            Toast.makeText(this, "Product Image is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please write product description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please write product Price", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Please write product Product Name", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }
    }


    private void StoreProductInformation() {

        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("Please wait, while we are Adding new products.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calandar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MMM-dd");
        saveCurrentDate = currentDate.format(calandar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calandar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProdectImagesRef.child(imageUri.getLastPathSegment()+ productRandomKey + ".jpg");
        final UploadTask uploadTask = filePath.putFile(imageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddProductActivity.this, "Error : "+ message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddProductActivity.this, "Product Image uploaded successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {

                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()){

                            throw task.getException();
                        }
                        else {

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddProductActivity.this, " Getting Product image Url", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void saveProductInfoToDatabase() {
        //......Products...................................
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate );
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", CategoryName);
        productMap.put("discount", Discount);
        productMap.put("dprice", DPrice);
        productMap.put("price", Price);
        productMap.put("pname", Pname);
        productMap.put("pnameSearch", Pname.toLowerCase());

        ProductRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent (AdminAddProductActivity.this, AddNewProductsAdminActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddProductActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddProductActivity.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
