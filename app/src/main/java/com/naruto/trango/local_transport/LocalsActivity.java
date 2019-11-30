package com.naruto.trango.local_transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.naruto.trango.R;
import com.naruto.trango.homepage.Adapter.ExpandableListAdapter;
import com.naruto.trango.homepage.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocalsActivity extends AppCompatActivity implements StarredFragment.OnFragmentInteractionListener,
        BusFragment.OnFragmentInteractionListener, TrainFragment.OnFragmentInteractionListener,
        MetroFragment.OnFragmentInteractionListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_locals_homepage);

        setTabLayout();

        setNavigationDrawer();

        String tab;
        try {
            tab = getIntent().getStringExtra("tab");

            if (tab != null) {
                switch (tab) {
                    case "bus":
                        tabLayout.getTabAt(1).select();
                        break;
                    case "train":
                        tabLayout.getTabAt(2).select();
                        break;
                    case "metro":
                        tabLayout.getTabAt(3).select();
                        break;
                        default:
                            tabLayout.getTabAt(0).select();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setTabLayout(){

        viewPager = findViewById(R.id.locals_viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.locals_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(StarredFragment.newInstance(), "Starred");
        adapter.addFragment(BusFragment.newInstance(), "Bus");
        adapter.addFragment(TrainFragment.newInstance(), "Train");
        adapter.addFragment(MetroFragment.newInstance(), "Metro");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(View view) {

    }

    public static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
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

    private void setNavigationDrawer(){

        Toolbar toolbar = findViewById(R.id.toolbar_widget);
        toolbar.setNavigationIcon(R.drawable.home_navicon);
        setSupportActionBar(toolbar);

        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(

                this, drawer, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(false);
//        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.drawer_icon,NavActivity.this.getTheme());
//        toggle.setHomeAsUpIndicator(icon);

        toggle.setToolbarNavigationClickListener(v -> {
//                mMenuMyVehicleLayout.setVisibility(View.GONE);
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        // Set Navigation Header
        View headerView      =  navigationView.getHeaderView(0);

        ImageView iv_profile =  headerView.findViewById(R.id.profile_pic);
//        TextView tv_name     =  headerView.findViewById(R.id.name);
//        TextView tv_mobile   =  headerView.findViewById(R.id.mobile);

        // need to set a profile pic -
        iv_profile.setOnClickListener(v -> {
            //startActivity(new Intent(AppHomePage.this,Profile.class));
            drawer.closeDrawer(GravityCompat.START);
        });
        //tv_name.setText(mUserInfo.get(LoginSessionManager.NAME));
        //tv_mobile.setText(mUserInfo.get(LoginSessionManager.MOBILE));

    }

    private void prepareMenuData() {

        List<MenuModel> childModelsList = new ArrayList<>();

        MenuModel menuModel = new MenuModel(1,"Home",false,true,R.drawable.ic_home);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(2,"Profile",false,true,R.drawable.ic_profile);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(3,"Locals", true, true, R.drawable.ic_locals);
        headerList.add(menuModel);

        MenuModel childModel = new MenuModel(31,"All", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel(32,"Bus", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel(33,"Train", false, false, 0);
        childModelsList.add(childModel);
        childModel = new MenuModel(34,"Metro", false, false, 0);
        childModelsList.add(childModel);

        if (menuModel.isHasChildren()) {
            childList.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel(4,"Transaction History",false,true,R.drawable.ic_wallet);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(5,"Notifications",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(6,"About Us",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(7,"Support",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(8,"",false,false, 0);  //Blank Space
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel(9,"Logout",false,true,R.drawable.active_dots);
        headerList.add(menuModel);
        if (!menuModel.isHasChildren()) {
            childList.put(menuModel, null);
        }

    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);

        ViewGroup footerView = (ViewGroup) getLayoutInflater().inflate(R.layout.home_page_expandable_view_footer, expandableListView, false);
        expandableListView.addFooterView(footerView);

        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {

//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            MenuModel menuModel = headerList.get(groupPosition);
            int id_int = (int) id;

            if (menuModel.isGroup()) {

                if (!menuModel.isHasChildren()) {
                    switch (id_int) {
                        case 0:
                            //startActivity(new Intent(NavActivity.this, UserProfile.class));
                            break;
                        case 1: //startActivity(new Intent(AppHomePage.this, MyAddressHomePage.class));

//                            delay(new InitialProfilePage());

                            break;
                        case 2: //startActivity(new Intent(AppHomePage.this,MekCoinsWallet.class));
                            break;
                        case 3:
//                            delay(new Transaction_details_01());
                            //delay(new OrderHistory());

                            break;
                        case 4:
                            break;
                        case 5:
//                            delay(new AboutUs());
                            // delay(new AboutUs());
                            break;
                        case 6:
//                            delay(new Help());
                            //delay(new Help());
                            break;
                        case 7: //startActivity(new Intent(AppHomePage.this, OffersHomePage.class));
                            break;
                        case 8: //startActivity(new Intent(AppHomePage.this, AboutUsPage.class));
                            // mSession.logoutUser();
                            finish();
                            break;
                    }
                    onBackPressed();
                }
            }

            return false;
        });

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {

            if (childList.get(headerList.get(groupPosition)) != null) {

                if (groupPosition == 2) {
                    switch (childPosition) {

                        case 0:
                            startActivity(new Intent(LocalsActivity.this, LocalsActivity.class));
                            break;
                        case 1:
//                            Objects.requireNonNull(tabLayout.getTabAt(1)).select();
                            break;
                        case 2:
//                            Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                            break;
                        case 3:
//                            Objects.requireNonNull(tabLayout.getTabAt(3)).select();
                            break;
                    }
                }

                onBackPressed();

            }

            return false;

        });

        expandableListView.setOnGroupExpandListener(groupPosition -> {

            if (lastExpandedPosition != -1
                    && groupPosition != lastExpandedPosition) {
                expandableListView.collapseGroup(lastExpandedPosition);
            }
            lastExpandedPosition = groupPosition;

        });
    }

    @Override
    public void onBackPressed() {
//        mMenuMyVehicleLayout.setVisibility(View.GONE);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
