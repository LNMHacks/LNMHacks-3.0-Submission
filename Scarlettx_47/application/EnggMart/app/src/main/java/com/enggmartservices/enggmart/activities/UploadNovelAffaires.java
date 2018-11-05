package com.enggmartservices.enggmart.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.enggmart.R;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UploadNovelAffaires extends AppCompatActivity implements View.OnClickListener {

    private String upload;

    private TextView mpdftv;
    private Button mUploadNovel, mSelectPDF, mUploadCA;
    private EditText mPdfDATE;
    private ImageView mImagePDF;
    private FirebaseDatabase mfirebaseDatabase;
    private FirebaseStorage mfStorage;
    private String type;
    private Uri pdfUri;
    private ProgressDialog progressDialog;
    private Uri fileUriImage;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_novel_affairs);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
            } else {
                upload = extras.getString("upload");
            }
        } else {
            upload = (String) savedInstanceState.getSerializable("upload");
        }
        mfStorage = FirebaseStorage.getInstance();
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mImagePDF = findViewById(R.id.image_for_pdf_file);
        mPdfDATE = findViewById(R.id.date_for_pdf_file);
        mUploadNovel = findViewById(R.id.upload_pdf_button_novel);
        mUploadCA = findViewById(R.id.upload_pdf_button_current_a);
        mSelectPDF = findViewById(R.id.select_pdf_file_button);
        mpdftv = findViewById(R.id.pdffileselect_tv);
        mImagePDF.setOnClickListener(this);
        mUploadNovel.setOnClickListener(this);
        mUploadCA.setOnClickListener(this);
        mSelectPDF.setOnClickListener(this);
        mPdfDATE.setOnClickListener(this);

        if (TextUtils.isEmpty(upload)) {
            finish();
        } else if (upload.equals("novel")) {
            mImagePDF.setVisibility(View.VISIBLE);
            mPdfDATE.setVisibility(View.GONE);
            mUploadCA.setVisibility(View.GONE);
            mUploadNovel.setVisibility(View.VISIBLE);
            type = "novel";

        } else if (upload.equals("currentaffairs")) {
            mImagePDF.setVisibility(View.GONE);
            mPdfDATE.setVisibility(View.VISIBLE);
            type = "currentaffairs";
            mUploadCA.setVisibility(View.VISIBLE);
            mUploadNovel.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        if (v == mPdfDATE) {
            new DatePickerDialog(UploadNovelAffaires.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (v == mSelectPDF) {
            if (checkPermission()) {
                selectPdf();
            }
        } else if (v == mImagePDF) {
            if (checkPermission()) {
                selectImage();
            }
        } else if (v == mUploadNovel) {
            if (pdfUri == null) {
                Toast.makeText(this, "please Select Pdf file", Toast.LENGTH_SHORT).show();
            } else if (fileUriImage == null) {
                Toast.makeText(this, "please Select Image file", Toast.LENGTH_SHORT).show();
            } else
                uploadImageFile(pdfUri, fileUriImage);
        } else if (v == mUploadCA) {
            String date = mPdfDATE.getText().toString().trim();
            if (TextUtils.isEmpty(date)) {
                Toast.makeText(this, "please Enter A Date", Toast.LENGTH_SHORT).show();
            } else if (pdfUri == null) {
                Toast.makeText(this, "please Select Pdf file", Toast.LENGTH_SHORT).show();
            } else
                uploadPDFFile(pdfUri, date);
        }


    }


    final Calendar myCalendar = Calendar.getInstance();

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy"; //set date format
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            mPdfDATE.setText(sdf.format(myCalendar.getTime()));
        }

    };


    private void uploadPDFFile(Uri pdfUrl, final String date) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis() + "";
        final StorageReference storageReference = mfStorage.getReference().child(type).child(fileName + ".pdf");
        storageReference.putFile(pdfUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final Map<String, String> map = new HashMap<>();
                        map.put("pdf", uri.toString() + "");
                        map.put("date", date + "");
                        DatabaseReference databaseReference = mfirebaseDatabase.getReference();
                        databaseReference.child(type).child(fileName).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UploadNovelAffaires.this, "pdf Uploaded", Toast.LENGTH_SHORT).show();
                                    fileUriImage = null;
                                    mPdfDATE.setText("");

                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(UploadNovelAffaires.this, "pdf Not Uploaded 1", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadNovelAffaires.this, "Pdf file not Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    private void uploadImageFile(Uri pdfUrl, Uri imagePdfUrl) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Files...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis() + "";
        final Map<String, String> map = new HashMap<>();
        final StorageReference storageReference = mfStorage.getReference().child(type).child(fileName + ".pdf");
        storageReference.putFile(pdfUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        map.put("pdf", uri.toString() + "");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadNovelAffaires.this, "Pdf file not Uploaded 2", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


        final StorageReference storageReferenceImg = mfStorage.getReference().child(type).child(fileName + ".jpg");
        storageReferenceImg.putFile(imagePdfUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReferenceImg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        map.put("img", uri.toString() + "");
                        DatabaseReference databaseReference = mfirebaseDatabase.getReference();
                        databaseReference.child(type).child(fileName).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UploadNovelAffaires.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                    fileUriImage = null;
                                    mImagePDF.setImageBitmap(null);
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(UploadNovelAffaires.this, "Image Not Uploaded 1", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UploadNovelAffaires.this, "Image File Not Uploaded 2", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }


    private void selectPdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(UploadNovelAffaires.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(UploadNovelAffaires.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Permission is Required", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 86)
            if (resultCode == RESULT_OK && data != null) {
                pdfUri = data.getData();
                mpdftv.setText("File is:" + data.getData().getLastPathSegment());
            } else {
                Toast.makeText(this, "Please Select a PDF File", Toast.LENGTH_SHORT).show();
            }
        else if (requestCode == 100)
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                fileUriImage = data.getData();
                mImagePDF.setImageURI(fileUriImage);
            } else {
                Toast.makeText(this, "Please Select a Image for PDF", Toast.LENGTH_SHORT).show();
            }
        else
            Toast.makeText(this, "Error While Selecting...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
