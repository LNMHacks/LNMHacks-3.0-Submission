package com.enggmartservices.enggmart.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public class AdminConsole extends AppCompatActivity implements View.OnClickListener {
    private static final int PICK_IMAGE_REQUEST = 101;
    private ImageView mItemImage;
    private EditText mItemName, mItemPrice, mItemDescription;
    private Button submitItem;
    private AlertDialog.Builder builder;
    private Uri fileUriImage;
    private StorageReference mStorageRef;
    private DatabaseReference mdDatabase;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_console);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setTitle("Admin Console");
        context = AdminConsole.this;
        mStorageRef = FirebaseStorage.getInstance().getReference().child("storeImages");
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("storeDetails");
        mItemImage = findViewById(R.id.image_item_to_server);
        mItemName = findViewById(R.id.name_item_to_server);
        mItemPrice = findViewById(R.id.price_item_to_server);
        mItemDescription = findViewById(R.id.description_item_to_server);
        submitItem = findViewById(R.id.submit_item_to_server);
        mItemImage.setOnClickListener(this);
        submitItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mItemImage) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Select Image for Item").setMessage("Taking Image from")
                    .setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(AdminConsole.this, "cancled", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, PICK_IMAGE_REQUEST);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else if (v == submitItem) {
            String iName = mItemName.getText().toString().trim();
            String iPrice = mItemPrice.getText().toString().trim();
            String iDescription = mItemDescription.getText().toString().trim();

            if (fileUriImage == null) {
                Toast.makeText(AdminConsole.this, "Please select A Images Of Product", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(iName)) {
                mItemName.setError("please Enter Item name");
            } else if (TextUtils.isEmpty(iPrice) || !(Pattern.matches("[0-9]+", iPrice))) {
                mItemPrice.setError("please enter Valid price");
            } else if (TextUtils.isEmpty(iDescription)) {
                mItemDescription.setError("Please Enter Some Discription of item");
            } else
                uploadImage(iName, iPrice, iDescription);
        }
    }

    private void uploadImage(final String iName, final String iPrice, final String iDescription) {
        progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        progressDialog.setMessage("uploading...");
        progressDialog.show();

        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        final StorageReference sRef1 = mStorageRef.child(timeStamp).child("itemImage.jpg");

        sRef1.putFile(fileUriImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        sRef1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("itemName", iName + " ");
                                map.put("itemPrice", iPrice + ".00");
                                map.put("itemDescription", iDescription + " ");
                                map.put("itemImage", uri.toString());
                                map.put("storagefolderName", timeStamp);
                                mdDatabase.push().setValue(map);

                            }
                        });
                        Toast.makeText(context, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        mItemName.setText("");
                        mItemPrice.setText("");
                        mItemDescription.setText("");
                        mItemImage.setImageResource(R.mipmap.photocamera);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUriImage = data.getData();
            mItemImage.setImageURI(fileUriImage);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
