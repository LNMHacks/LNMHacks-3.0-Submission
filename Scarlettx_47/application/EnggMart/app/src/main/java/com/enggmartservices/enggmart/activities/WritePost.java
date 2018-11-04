package com.enggmartservices.enggmart.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.enggmart.R;
import com.enggmartservices.enggmart.models.PostModel;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class WritePost extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 100;
    private CircleImageView crossImage, userDp;
    private EditText mwritePostEditText;
    private ImageView mWritePostImageView, mSelectImage;
    private TextView mPostUpdate;
    private Uri fileUriImage;
    private StorageReference mStorageRef;
    private DatabaseReference mdDatabase;
    private DatabaseReference mdDatabaseUser;
    private FirebaseAuth userAuth;
    private String writeyourView;
    private ProgressBar progressBar;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_post);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        crossImage = findViewById(R.id.write_post_cross);
        userDp = findViewById(R.id.user_dp_write);
        mwritePostEditText = findViewById(R.id.write_your_views_et);
        mWritePostImageView = findViewById(R.id.image_write_posted_to_be);
        mSelectImage = findViewById(R.id.image_select_write_post);
        mPostUpdate = findViewById(R.id.post_write);
        progressBar = findViewById(R.id.progress_bar_write_post);
        mSelectImage.setOnClickListener(this);
        mPostUpdate.setOnClickListener(this);
        userAuth = FirebaseAuth.getInstance();
        mdDatabaseUser = FirebaseDatabase.getInstance().getReference().child("users").child(userAuth.getCurrentUser().getUid()).child("image");

        mdDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("howto", dataSnapshot.getValue().toString());
                if (!dataSnapshot.getValue().toString().equals("not Provided"))
                    Glide.with(getApplicationContext()).load(dataSnapshot.getValue().toString()).into(userDp);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        crossImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mSelectImage) {
            openFileChoser();
        } else if (v == mPostUpdate) {
            writeyourView = mwritePostEditText.getText().toString().trim();
            if (fileUriImage != null || !writeyourView.equals(null)) {
                AlertDialog.Builder builder;

                if (fileUriImage != null) {
                    builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                    builder.setTitle("Share This Post?")
                            .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    uploadImage(fileUriImage);
                                }
                            })
                            .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(R.mipmap.photocamera)
                            .show();

                } else {
                    if (TextUtils.isEmpty(writeyourView)) {
                        Toast.makeText(WritePost.this, "Add SomeThing in The Post", Toast.LENGTH_SHORT).show();
                    } else {

                        builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                        builder.setTitle("Share This Post?")
                                .setPositiveButton("Share", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        progressBar.setVisibility(View.VISIBLE);
                                        updateDataBase("no Image");
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
                }


            }
        }
    }


    private void uploadImage(Uri fileUriImage) {
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        mStorageRef = FirebaseStorage.getInstance().getReference().child("posts").child(timeStamp);
        final StorageReference sRef = mStorageRef.child("post.jpg");

        sRef.putFile(fileUriImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                updateDataBase(uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(WritePost.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(WritePost.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void updateDataBase(String uri) {
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        final String timeStamp = new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss a",
                Locale.getDefault()).format(new Date());
        PostModel postModel = new PostModel();
        postModel.setPostUrl(uri);
        postModel.setPostCommentsCount("0");
        postModel.setUid(userAuth.getCurrentUser().getUid());
        if (writeyourView != null)
            postModel.setPostDescription(writeyourView);
        else
            postModel.setPostDescription("");
        postModel.setPostLikesCount("0");
        postModel.setPostTime(timeStamp);

        mdDatabase.push().setValue(postModel);
        progressBar.setVisibility(View.GONE);
        finish();
    }


    private void openFileChoser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUriImage = data.getData();
            mWritePostImageView.setVisibility(View.VISIBLE);
            mWritePostImageView.setImageURI(fileUriImage);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
