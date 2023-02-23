package com.example.fourmencoffee.leftnagivationview;

import static com.example.fourmencoffee.activity.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fourmencoffee.R;
import com.example.fourmencoffee.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    CircleImageView profileImg;
    EditText name, email, number, address;
    Button update;
    private  MainActivity mainActivity;
    FirebaseStorage storage;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    FirebaseDatabase database;

    private  Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);

        update = root.findViewById(R.id.button_update_profile);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database= FirebaseDatabase.getInstance();

        setUserInformation();
        mainActivity = (MainActivity) getActivity();
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile();
            }

        });



        return root;
    }

    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            mainActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED)
        {
            mainActivity.openGallery();
        }
            else
            {
                String [] perrmissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                getActivity().requestPermissions(perrmissions, MY_REQUEST_CODE);
            }
    }



    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
            return;
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());
        Glide.with(getActivity()).load(user.getPhotoUrl())
                .error(R.drawable.black_profile_avt).into(profileImg);
    }

    public void setBitmapImageView(Bitmap bitmapImageView)
    {
        profileImg.setImageBitmap(bitmapImageView);
    }

    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
            return;
        String strFullName = name.getText().toString();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .setPhotoUri(uri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            mainActivity.showUserInformation();
                        }
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data.getData() != null)
        {
            Uri profileUri =data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());
            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                }
            });


        }


    }
}