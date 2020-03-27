package com.example.meditena.ui.settings;

import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditena.Buyer.LoginActivity;
import com.example.meditena.Buyer.MainActivity;
import com.example.meditena.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    private CircleImageView profileViewImage;
    private EditText fullNameEditText, userPhoneEditText, adressEditText, emailEditText;
    private String checker = "";
    private Uri imageUri;
    private String myUrl = "";
    private StorageReference storageProfilePictureRef;
    private FirebaseAuth auth;
    private Button logout;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("profile picture");

        logout = (Button) view.findViewById(R.id.logout_button_user);
        profileViewImage = (CircleImageView) view.findViewById(R.id.setting_profile_image);
        fullNameEditText = (EditText) view.findViewById(R.id.update_name_settings);
        userPhoneEditText = (EditText)view.findViewById(R.id.update_phone_number_settings);
        adressEditText = (EditText) view.findViewById(R.id.update_Adress_settings);
        TextView closeTextbtn = (TextView) view.findViewById(R.id.close_account_settings);
        TextView saveTextbtn = (TextView) view.findViewById(R.id.update_account_settings);
        emailEditText = (EditText)view.findViewById(R.id.update_Email_settings);
        auth = FirebaseAuth.getInstance();

        userInfoDisplay(profileViewImage, fullNameEditText, userPhoneEditText, adressEditText, emailEditText);


        closeTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        saveTextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")){
                    userInfoSaved();
                }
                else{
                    updateOnlyUserInfo();
                }
            }
        });
        profileViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                Intent intent = CropImage.activity(imageUri)
                        .getIntent(getContext());
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent (getActivity(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileViewImage.setImageURI(imageUri);
        }
        else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            Exception error = result.getError();
            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getActivity(), "Error try again", Toast.LENGTH_SHORT).show();

        }
    }

    private void updateOnlyUserInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

        final String uid = auth.getCurrentUser().getUid();
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("name", fullNameEditText.getText().toString());
        userMap.put("address", adressEditText.getText().toString());
        userMap.put("phone", userPhoneEditText.getText().toString());
        userMap.put("email", emailEditText.getText().toString());
        ref.child(uid).updateChildren(userMap);

        Toast.makeText(getActivity(), "Profile info updated successfully ", Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }



    private void userInfoSaved() {
        if (checker.equals("clicked")){
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (imageUri!=null){
            final String uid = auth.getCurrentUser().getUid();
            final StorageReference fileRef = storageProfilePictureRef
                    .child(uid+ ".jpg");

            StorageTask uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        final String uid = auth.getCurrentUser().getUid();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("name", fullNameEditText.getText().toString());
                        userMap.put("address", adressEditText.getText().toString());
                        userMap.put("phone", userPhoneEditText.getText().toString());
                        userMap.put("image", myUrl);
                        userMap.put("email", emailEditText.getText().toString());
                        ref.child(uid).updateChildren(userMap);

                        progressDialog.dismiss();

                        startActivity(new Intent(getActivity(), MainActivity.class) );
                        Toast.makeText(getActivity(), "Profile info updated successfully ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getActivity(), "image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(final CircleImageView profileViewImage, final EditText fullNameEditText, final EditText userPhoneEditText, final EditText adressEditText, final EditText emailEditText) {
        final String uid = auth.getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("image").exists()){
                        String image = dataSnapshot.child("image").getValue().toString();
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();
                        Picasso.get().load(image).into(profileViewImage);
                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        adressEditText.setText(address);
                        emailEditText.setText(email);
                    }
                    else {
                        String name = dataSnapshot.child("name").getValue().toString();
                        String phone = dataSnapshot.child("phone").getValue().toString();
                        String address = dataSnapshot.child("address").getValue().toString();
                        String email = dataSnapshot.child("email").getValue().toString();

                        fullNameEditText.setText(name);
                        userPhoneEditText.setText(phone);
                        adressEditText.setText(address);
                        emailEditText.setText(email);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SettingsViewModel mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

}
