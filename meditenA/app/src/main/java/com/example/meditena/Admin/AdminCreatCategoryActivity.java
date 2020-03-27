package com.example.meditena.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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

public class AdminCreatCategoryActivity extends AppCompatActivity {
    private String saveCurrentDate, saveCurrentTime, Category;
    private Button addnewproductbtn;
    private ImageView inputCategoryImage;
    private EditText inputCategoryName;
    private static final int GalleryPick = 1;
    private DatabaseReference CategoryRef;
    private Uri imageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProdectImagesRef;

    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_creat_category);

        getSupportActionBar().hide();
        CategoryRef = FirebaseDatabase.getInstance().getReference().child("categories");
        ProdectImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        addnewproductbtn = (Button)findViewById(R.id.add_new_productbtn);
        inputCategoryName = (EditText)findViewById(R.id.add_Category_name);
        inputCategoryImage = (ImageView) findViewById(R.id.add_Category_image);
        loadingBar = new ProgressDialog(this);


        inputCategoryImage.setOnClickListener(new View.OnClickListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null){
            imageUri = data.getData();
            inputCategoryImage.setImageURI(imageUri);
        }
    }
    private void validateProductData() {

         Category = inputCategoryName.getText().toString();


        if (imageUri == null){
            Toast.makeText(this, "Product Image is required", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Category)){
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
                Toast.makeText(AdminCreatCategoryActivity.this, "Error : "+ message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminCreatCategoryActivity.this, "Product Image uploaded successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(AdminCreatCategoryActivity.this, " Getting Product image Url", Toast.LENGTH_SHORT).show();

                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }
    private void saveProductInfoToDatabase() {
        //......................Products in The categories
        HashMap<String, Object> categoryMap = new HashMap<>();
        categoryMap.put("pid", productRandomKey);
        categoryMap.put("date", saveCurrentDate );
        categoryMap.put("time", saveCurrentTime);
        categoryMap.put("image", downloadImageUrl);
        categoryMap.put("category", Category);
        categoryMap.put("categorySearch", Category.toLowerCase());

        CategoryRef.child(Category).updateChildren(categoryMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent (AdminCreatCategoryActivity.this, AddNewProductsAdminActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminCreatCategoryActivity.this, "Product is added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminCreatCategoryActivity.this, "Error: "+ message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
