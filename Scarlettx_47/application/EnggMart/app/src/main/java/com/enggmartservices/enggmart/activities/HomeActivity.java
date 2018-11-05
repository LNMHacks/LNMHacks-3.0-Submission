package com.enggmartservices.enggmart.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enggmartservices.enggmart.fragments.OneFragment;
import com.enggmartservices.enggmart.R;
import com.enggmartservices.enggmart.fragments.ThreeFragment;
import com.enggmartservices.enggmart.fragments.TwoFragment;
import com.enggmartservices.enggmart.utility.UserDetails;
import com.enggmartservices.enggmart.utility.Utils;
import com.enggmartservices.enggmart.fragments.FourFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private TextView uname, uemail;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private LinearLayout semesterWiseBooks, tools, myCart, myOrder, myChat, sellOnEnggMart, accountSetting, helpCentre, share;
    private CircleImageView imageProfile;
    private ImageView notifiacationa;
    private DatabaseReference mdDatabase;
    private FirebaseAuth userAuth;
    private Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;
    private ActionBarDrawerToggle toggle;
    private String uid;
    private ProgressBar progressBar;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.darkenStatusBar(this, R.color.colorPrimary);
        findIds();
        userAuth = FirebaseAuth.getInstance();
        uid = userAuth.getCurrentUser().getUid();
        UserDetails.uid = uid;
        progressBar.setVisibility(View.VISIBLE);
        mdDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        init();
        onClicking();
        notifiacationa = (ImageView) findViewById(R.id.nofication);
        notifiacationa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, Notification.class);
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setupViewPager();
    }

    private void onClicking() {
        sellOnEnggMart.setOnClickListener(this);
        semesterWiseBooks.setOnClickListener(this);
        tools.setOnClickListener(this);
        myCart.setOnClickListener(this);
        myChat.setOnClickListener(this);
        myOrder.setOnClickListener(this);
        accountSetting.setOnClickListener(this);
        helpCentre.setOnClickListener(this);
        share.setOnClickListener(this);
        imageProfile.setOnClickListener(this);
    }

    private void init() {
        mdDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Map<String, Object> userid = (Map<String, Object>) dataSnapshot.getValue();
                    Log.e("uid :", "" + userid);
                    String name = userid.get("name").toString();
                    String email = userid.get("email").toString();
                    String phone = userid.get("phone").toString();
                    String imageurl = userid.get("image").toString();
                    UserDetails.name = name;
                    UserDetails.phoneno = phone;
                    UserDetails.email = email;
                    UserDetails.img = imageurl;
                    if (!imageurl.equals("not Provided")) {
                        Glide.with(getApplicationContext()).load(imageurl).into(imageProfile);
                        progressBar.setVisibility(View.GONE);
                    }
                    uname.setText(name + "");
                    uemail.setText(email + "");
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupViewPager() {
        HomeActivity.ViewPagerAdapter adapter = new HomeActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Newsfeeds");
        adapter.addFragment(new ThreeFragment(), "Store");
        adapter.addFragment(new TwoFragment(), "Work/Job");
        adapter.addFragment(new FourFragment(), "Engg Lib");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void findIds() {
        notifiacationa = (ImageView) findViewById(R.id.nofication);
        drawer = (DrawerLayout) findViewById(R.id.dr_layout);
        semesterWiseBooks = (LinearLayout) findViewById(R.id.semesterwisebook);
        tools = (LinearLayout) findViewById(R.id.tools);
        myCart = (LinearLayout) findViewById(R.id.mycart);
        myChat = (LinearLayout) findViewById(R.id.mychat);
        myOrder = (LinearLayout) findViewById(R.id.myorder);
        sellOnEnggMart = (LinearLayout) findViewById(R.id.sellonenggmart);
        accountSetting = (LinearLayout) findViewById(R.id.accountsetting);
        share = (LinearLayout) findViewById(R.id.share);
        helpCentre = (LinearLayout) findViewById(R.id.help);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        uname = (TextView) findViewById(R.id.namedr);
        uemail = (TextView) findViewById(R.id.emaildr);
        imageProfile = (CircleImageView) findViewById(R.id.img_pdr);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        progressBar = findViewById(R.id.progress_bar_drawer);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
        }


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Toast.makeText(HomeActivity.this, "Press again back to exit", Toast.LENGTH_SHORT).show();
            this.doubleBackToExitPressedOnce = true;
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 4000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profilemenu) {
            Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(i);
        } else if (id == R.id.guide) {
            guideDialog();
        } else if (id == R.id.logoutmenu) {
            customDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void customDialog() {
        final Dialog openDialog = new Dialog(HomeActivity.this);
        openDialog.setContentView(R.layout.activity_logout);
        openDialog.setCancelable(false);

        TextView cancel = (TextView) openDialog.findViewById(R.id.cancel);
        TextView logout = (TextView) openDialog.findViewById(R.id.logout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDialog.dismiss();

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog.dismiss();

                userAuth.signOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                // [END config_signin]

                mGoogleSignInClient = GoogleSignIn.getClient(HomeActivity.this, gso);

                mGoogleSignInClient.signOut().addOnCompleteListener(HomeActivity.this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                userAuth.signOut();
                                startActivity(new Intent(HomeActivity.this, SignInSignUp.class));
                                finish();
                            }
                        });


            }
        });
        openDialog.show();
    }

    private void guideDialog() {
        final Dialog openDialog = new Dialog(HomeActivity.this);
        openDialog.setContentView(R.layout.guidelines);
        openDialog.setCancelable(false);
        TextView cancel = (TextView) openDialog.findViewById(R.id.guide_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog.dismiss();
            }
        });
        openDialog.show();
    }


    public void onClick(View v) {
        if (v == semesterWiseBooks) {
            Toast.makeText(this, "clicked On Semester Wise Book", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == tools) {
            Toast.makeText(this, "clicked On tools", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == myCart) {
            Toast.makeText(this, "clicked On My Cart", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == myOrder) {
            Toast.makeText(this, "clicked On My Order", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == myChat) {
            Intent i = new Intent(HomeActivity.this, UsersActivity.class);
            startActivity(i);
            drawer.closeDrawers();
        } else if (v == sellOnEnggMart) {
            Toast.makeText(this, "clicked On Sell On Enggmart", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        } else if (v == accountSetting) {
            Intent i = new Intent(HomeActivity.this, AdminPannel.class);
            startActivity(i);
            drawer.closeDrawers();
        } else if (v == helpCentre) {
            Intent i = new Intent(HomeActivity.this, HelpCenter.class);
            startActivity(i);
            drawer.closeDrawers();
        } else if (v == share) {

            ShareCompat.IntentBuilder.from(HomeActivity.this)
                    .setType("text/plain")
                    .setChooserTitle("Chooser title")
                    .setText("http://play.google.com/store/apps/details?id=" + getApplication().getPackageName())
                    .startChooser();
            drawer.closeDrawers();
        } else if (v == imageProfile) {
            Toast.makeText(this, "To change DP go to profile", Toast.LENGTH_SHORT).show();
            drawer.closeDrawers();
        }
    }


}
