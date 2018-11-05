package com.enggmartservices.enggmart.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE = 1022;
    private CircleImageView profileImage;
    private TextView namepro, phonepro, emailpro, detailedpro, update;
    private EditText etnamepro, etphonepro;
    private StorageReference mStorageRef;
    private DatabaseReference mdDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressBar progressBar;
    private ProfileActivity context;
    private ProgressDialog progressDialog;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("User Profile");
        findIds();
        progressBar.setVisibility(View.VISIBLE);
        context = ProfileActivity.this;
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mCurrentUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid + "");
        mdDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                // String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                namepro.setText(name);
                emailpro.setText(email);
                phonepro.setText(phone);
                if (!image.equals("not Provided")) {
                    Glide.with(getApplicationContext()).load(image).into(profileImage);
                    //Picasso.get().load(imageUri).placeholder(R.mipmap.usera).into(profileImage);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void findIds() {
        profileImage = findViewById(R.id.userdpprofile);
        namepro = findViewById(R.id.nameprofiletv);
        phonepro = findViewById(R.id.phoneprofiletv);
        detailedpro = findViewById(R.id.detailedprofile);
        update = findViewById(R.id.updatepro);
        etnamepro = findViewById(R.id.nameprofileet);
        etphonepro = findViewById(R.id.phoneprofileet);
        emailpro = findViewById(R.id.emailprofiletv);
        progressBar = findViewById(R.id.progress_bar_pro_dp);
        profileImage.setOnClickListener(this);
        phonepro.setOnClickListener(this);
        namepro.setOnClickListener(this);
        detailedpro.setOnClickListener(this);
        update.setOnClickListener(this);
        etphonepro.setOnClickListener(this);
        etnamepro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == profileImage) {
            profileImageUploadMethod();
        } else if (view == namepro) {
        } else if (view == phonepro) {
        } else if (view == etnamepro) {
        } else if (view == etphonepro) {
        } else if (view == detailedpro) {
        } else if (view == update) {
        }
    }

    private void profileImageUploadMethod() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Update Display Picture")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openFileChooser();
                    }
                })
                .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(R.mipmap.photocamera)
                .show();
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            CropImage.activity(imageUri).setAspectRatio(1, 1).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                File thumb_filePath = new File(result.getUri().getPath());
                uploadImage(result.getUri(), thumb_filePath);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadImage(Uri imageUri, final File thumb_filePath) {
        progressDialog = new ProgressDialog(ProfileActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setTitle("Uploading Image...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);
        String userID = mCurrentUser.getUid();

        Bitmap thumb_bitmap = null;
        byte[] thumb_byte = null;
        try {
            thumb_bitmap = new Compressor(this)
                    .setMaxWidth(180)
                    .setMaxWidth(180)
                    .setQuality(60)
                    .compressToBitmap(thumb_filePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            thumb_byte = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final StorageReference filePath = mStorageRef.child("profileImages").child(userID + ".jpg");
        final StorageReference thumb_filePathS = mStorageRef.child("profileImages").child("thumbs").child(userID + ".jpg");

        final byte[] finalThumb_byte = thumb_byte;
        filePath.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {

                            UploadTask uploadTask = thumb_filePathS.putBytes(finalThumb_byte);
                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return thumb_filePathS.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri thumb_downloadUri = task.getResult();
                                        Map uploadUris = new HashMap<>();
                                        uploadUris.put("image", uri.toString());
                                        uploadUris.put("thumb_image", thumb_downloadUri.toString());
                                        mdDatabase.updateChildren(uploadUris).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ProfileActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                    progressBar.setVisibility(View.GONE);
                                                } else {
                                                    Toast.makeText(ProfileActivity.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "Image Not Uploaded", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        }
                    });
                } else {
                    Toast.makeText(ProfileActivity.this, "Profile Image Not Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
