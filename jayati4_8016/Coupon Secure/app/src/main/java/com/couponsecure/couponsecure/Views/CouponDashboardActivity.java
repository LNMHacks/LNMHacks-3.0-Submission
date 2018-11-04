package com.couponsecure.couponsecure.Views;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.couponsecure.couponsecure.ApiClient;
import com.couponsecure.couponsecure.ApiInterface;
import com.couponsecure.couponsecure.R;

import java.util.ArrayList;
import java.util.UUID;

public class CouponDashboardActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_dashboard);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        if(position==0) {

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position+1))
                    .commit();
        }

        if(position==1) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new UserInformationFragment())
                    .commit();
        }
        if(position==2) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new CouponsNearMe())
                    .commit();
        }
        if(position == 3)
        {
            startActivity(new Intent(CouponDashboardActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private RecyclerView recyclerView1;
        private ApiInterface apiService;
        private ArrayList<CouponsModel> couponList = new ArrayList<>();
        private CouponDashboardActivity activity;


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_coupon_dashboard, container, false);

            recyclerView1 = rootView.findViewById(R.id.coupons_recycler_view);

            recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));


            apiService = ApiClient.getClient().create(ApiInterface.class);

            String uniqueID = UUID.randomUUID().toString();
            couponList.add(new CouponsModel(uniqueID ,"11231","Smastam",true,true,"10% Off"));
            uniqueID = UUID.randomUUID().toString();
            couponList.add(new CouponsModel(uniqueID ,"11239","Astam",true,false,"20% Off"));
            uniqueID = UUID.randomUUID().toString();
            couponList.add(new CouponsModel(uniqueID ,"11281","Tam",true,true,"40 Cashback"));

            recyclerView1.setAdapter(new CouponAdapter(couponList, getActivity().getApplicationContext()));

            return rootView;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            ((CouponDashboardActivity) context).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
            this.activity = (CouponDashboardActivity) context;


        }
    }

}
