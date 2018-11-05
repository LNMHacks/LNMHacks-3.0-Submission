package com.enggmartservices.enggmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.utility.ConnectivityReceiver;
import com.enggmartservices.enggmart.utility.MyApplication;
import com.enggmartservices.enggmart.utility.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private ImageView Imv;
    private EditText email, password, uname, phone;
    private Button btnregister;
    private TextView login;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        btnregister = findViewById(R.id.buttonregister);
        email = findViewById(R.id.emailsignup);
        uname = findViewById(R.id.namesignup);
        password = findViewById(R.id.passwordsignup);
        login = findViewById(R.id.logintvsignup);
        phone = findViewById(R.id.mobnosignup);
        Imv = findViewById(R.id.imv);
        progressBar = findViewById(R.id.progress_bar_signup);
        btnregister.setOnClickListener(this);
        login.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        Imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnregister) {
            registerUser();
        } else if (view == login) {
            startActivity(new Intent(Signup.this, SignIn.class));
            finish();
        }
    }

    public void registerUser() {
        final String name = uname.getText().toString().trim();
        final String emailid = email.getText().toString().trim();
        final String phoneno = phone.getText().toString().trim();
        String pass = password.getText().toString();
        if (userValidte(name, emailid, phoneno, pass)) {
            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(emailid, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = task.getResult().getUser();
                                if (user != null) {
                                    user.sendEmailVerification().addOnCompleteListener(
                                            new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Signup.this,
                                                            "Verification email sent to " + user.getEmail(),
                                                            Toast.LENGTH_SHORT).show();
                                                    updateUI(user, name, phoneno, emailid);

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(Signup.this, e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                            user.delete();
                                            Toast.makeText(Signup.this,
                                                    "Registration is not Successful!",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                if (task.getException() instanceof FirebaseAuthUserCollisionException)
                                    Toast.makeText(Signup.this, "You Are Already Registered", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }

    private void updateUI(@NonNull final FirebaseUser user, final String name, final String phoneno, final String emailid) {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        HashMap<String, String> userMap = new HashMap<>();
        userMap.put("phone", phoneno + "");
        userMap.put("email", emailid + "");
        userMap.put("image", "not Provided");
        userMap.put("name", name + "");
        userMap.put("status", "Hey Thare I'm using EnggMart");
        userMap.put("pass", "yes");
        userMap.put("thumb_image", "not Provided");
        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    completeTask();
                } else
                    Toast.makeText(Signup.this, "Registration Not Completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean userValidte(String name, String emailid, String phoneno, String pass) {
        if (name.equals("")) {
            uname.setError("Please Enter User Name");
            uname.requestFocus();
        } else if (name.length() < 4) {
            uname.setError("User Name Length Should be Atleast 4");
            uname.requestFocus();
        } else if (emailid.equals("")) {
            email.setError("Please Enter an Email ID");
            email.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
            email.setError("Please Provide a Valid Email");
            email.requestFocus();
        } else if (!Patterns.PHONE.matcher(phoneno).matches() || phoneno.length() < 10) {
            phone.setError("enter valid phone no.");
            phone.requestFocus();
        } else if (pass.length() < 6) {
            password.setError("at least 6 characters long");
            password.requestFocus();
        } else
            return true;
        return false;
    }

    private void completeTask() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Registration Successfull..!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Signup.this, SignIn.class));
        auth.signOut();
        finish();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        //showSnack(isConnected);
    }

/*
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Signup.this, SignInSignUp.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
