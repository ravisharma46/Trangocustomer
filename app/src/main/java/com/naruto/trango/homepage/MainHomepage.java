package com.naruto.trango.homepage;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.naruto.trango.R;
import com.naruto.trango.SOSActivity;
import com.naruto.trango.checkIn_checkOut.RoutesActivity;
import com.naruto.trango.trango_services.Services_Home_Page;
import com.naruto.trango.cabs_n_more.CabHomepageActivity;
import com.naruto.trango.checkIn_checkOut.Checkout;
import com.naruto.trango.homepage.Adapter.Container1Adapter;
import com.naruto.trango.homepage.Adapter.ContainerAdapter5;
import com.naruto.trango.homepage.Adapter.ExpandableListAdapter;
import com.naruto.trango.homepage.Adapter.ParkingZoneAdapter;
import com.naruto.trango.homepage.SimpleClasses.Container1;
import com.naruto.trango.homepage.SimpleClasses.Container5;
import com.naruto.trango.homepage.SimpleClasses.ParkingZone;
import com.naruto.trango.local_transport.LocalsActivity;
import com.naruto.trango.trango_profile.ProfileActivity;
import com.naruto.trango.transaction_history.TrangoWalletActivity;
import com.naruto.trango.transit.MainMap;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainHomepage extends AppCompatActivity {

    private TextView tvCityName;
    private SearchView searchView;
    private Toolbar toolbar;

    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    private int lastExpandedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_main_homepage);

        toolbar = findViewById(R.id.toolbar_widget);
        toolbar.setNavigationIcon(R.drawable.home_navicon);
        setSupportActionBar(toolbar);

        tvCityName = toolbar.findViewById(R.id.tv_current_city);
        searchView = toolbar.findViewById(R.id.home_search_view);

        String title1 = "Real Time Traffic Congestion";
        String title2 = "Passenger Density";
        String title3 = "Research, prepay & save";

        String desc1 = "Get real time updates on vehicular congestion and how it has affected public transport timings";
        String desc2 = "Real time information about passenger density within the metro train or bus you are going to travel from.";
        String desc3 = "Explore various amazing parking spots you want to park your vehicle at!";

        List<Container1> container1List1 = new ArrayList<>();

        container1List1.add(new Container1(R.drawable.m_c1_1,title1,desc1));
        container1List1.add(new Container1(R.drawable.m_c1_2,title2,desc2));
        container1List1.add(new Container1(R.drawable.m_c1_3,title3,desc3));

        RecyclerView recyclerView = findViewById(R.id.recycler_view1_agenda);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        Container1Adapter adaptor1 = new Container1Adapter(container1List1);
        recyclerView.setAdapter(adaptor1);

        //At a time only one view can be shown
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        // pager indicator
        recyclerView.addItemDecoration(new LinePagerIndicatorDecoration());

        /*----------------------------Smart Parking Featured----------------------*/

        /*---------------------------  Featured venues --------------------------- */

        List<ParkingZone> parkingZoneList = new ArrayList<>();

        parkingZoneList.add(new ParkingZone(R.mipmap.park6,"Zone 1"));
        parkingZoneList.add(new ParkingZone(R.mipmap.park5,"Zone 2"));
        parkingZoneList.add(new ParkingZone(R.mipmap.park4,"Zone 3"));

        RecyclerView recyclerView3 = findViewById(R.id.recycler_view3_agenda);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ParkingZoneAdapter adapter3 = new ParkingZoneAdapter(parkingZoneList, recyclerView3.getContext());
        recyclerView3.setAdapter(adapter3);

        /*------------------------- Where to Park----------------------------------------*/

        /*---------------------- Everywhere you want to park-----------------------------*/

        String desc5 = "Our services are available at 6+ cities in India.";
        String desc6 = "More than 5000 parking spots are available.";
        String desc7 = "10,000+ transactions done per year.";

        List<Container5> descList = new ArrayList<>();

        descList.add(new Container5("6+","Cities",desc5));
        descList.add(new Container5("5000+","Parking",desc6));
        descList.add(new Container5("10,000+","Transactions",desc7));

        RecyclerView recyclerView5 = findViewById(R.id.recycler_view5_agenda);
        recyclerView5.setHasFixedSize(true);

        recyclerView5.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ContainerAdapter5 adapter5 = new ContainerAdapter5(descList);
        recyclerView5.setAdapter(adapter5);

        //At a time only one view can be shown
        PagerSnapHelper snapHelper2 = new PagerSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerView5);

        IndefinitePagerIndicator indicator5 = findViewById(R.id.recycler_view5_indicator);
        indicator5.attachToRecyclerView(recyclerView5);

        setNavigationDrawer();
        setBottomNavigation();

        setMyLocation();

        findViewById(R.id.locate_me).setOnClickListener(view -> startActivity(new Intent(MainHomepage.this, MainMap.class)));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("query", query);
                Intent intent = new Intent(MainHomepage.this, RoutesActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private  void setBottomNavigation(){

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
//                Fragment fragment;
        String classname = this.getClass().getSimpleName();
        switch (item.getItemId()) {
            case R.id.navigation_home:
                if (!classname.equals("MainHomepage"))
                    startActivity(new Intent(this, MainHomepage.class));
                return true;
            case R.id.navigation_trips:
                startActivity(new Intent(this, Services_Home_Page.class));
                return true;
            case R.id.navigation_wallet:
                startActivity(new Intent(this, TrangoWalletActivity.class));
                return true;
            case R.id.navigation_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
        }
        return false;
    };

    private void setNavigationDrawer(){

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
                    String classname = this.getClass().getSimpleName();
                    switch (id_int) {
                        case 0:
                            if (!classname.equals("MainHomepage"))
                                startActivity(new Intent(this, MainHomepage.class));
                            break;
                        case 1:
                            delay(new ProfileActivity());
                            break;
                        case 2: //startActivity(new Intent(AppHomePage.this,MekCoinsWallet.class));
                            break;
                        case 3:
                            delay(new TrangoWalletActivity());
                            //delay(new OrderHistory());
                            break;
                        case 4:
                            break;
                        case 5:
//                            delay(new AboutUs());
                            // delay(new AboutUs());
                            break;
                        case 6:
                            delay(new SOSActivity());
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
                            startActivity(new Intent(MainHomepage.this, LocalsActivity.class));
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void setMyLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location location = null;
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location != null) {
                String cityName = getMyLocation(location.getLatitude(), location.getLongitude());
                tvCityName.setText(cityName);
            }
        } else {
            Snackbar.make(tvCityName, "Please enable location permission from settings", BaseTransientBottomBar.LENGTH_SHORT).setAction("OK", null).show();
        }
    }

    private String getMyLocation(double lat, double lon) {
        String cityname = "City";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if (addresses.size() > 0) {
                for (Address address : addresses) {
                    if (address.getLocality() != null && address.getLocality().length() > 0) {
                        cityname = address.getLocality();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityname;
    }

    public void delay(Activity activity){
        Handler handler = new Handler();

        handler.postDelayed(() -> {
            Intent intent = new Intent(MainHomepage.this, activity.getClass());
            startActivity(intent);

        }, 250);
    }

    public void cabHomePageIntent(View view) {
        Intent intent = new Intent(MainHomepage.this, CabHomepageActivity.class);
        startActivity(intent);
    }

    public void callScanHomepageIntent(View view) {
        startActivity(new Intent(this, Checkout.class));
    }
}