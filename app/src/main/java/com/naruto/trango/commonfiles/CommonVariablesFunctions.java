package com.naruto.trango.commonfiles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.naruto.trango.GPSTracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CommonVariablesFunctions {

    private static final String TAG = "CoomanVarAndFun";

    //Company_id
    public static final int SUZUKI   = 1;
    public static final int HONDA    = 2;
    public static final int HYUNDAI  = 3;
    public static final int RENAULT  = 4;
    public static final int TOYOTA   = 5;

    // Keys
    public static final String KEY_BIKE = "bike";
    public static final String KEY_CAR = "car";

    public static final String MANUAL    = "manual";
    public static final String AUTOMATIC = "automatic";

    public static final String HATCHBACK = "hatchback";
    public static final String SEDAN     = "sedan";
    public static final String SUV       = "suv";
    public static final String PREMIUM   = "premium";

    public static final String BASE_URL = "https://mekpark.com/mani14/user/";
    public static final String BASE_IMAGE_PATH = BASE_URL+"vehicles_images/";
    public static final int RETRY_SECONDS = 8 ;
    public static final int NO_OF_RETRY = 0 ;

    public static final String OTP_KEY = "192785A1sPOm0965a584ea5" ;

    public static final String TIME_FORMAT  = "hh:mm a";
    public static final String TODAY        = "Today";
    public static final String TOMMARROW    = "Tommarrow";

    public static  final String LOCATION_NOT_FOUND      = "can't find the location";

    public static final String PETROL   = "petrol";
    public static final String DIESEL   = "diesel" ;
    public static final String CNG      = "cng";

    public static final String CUSTOMER_CARE   = "1234567890";
    public static final String CONTACT_MEKPARK = "+917838878899";

    public static final String PAYMENT_MERCHANT_NAME = "Mekpark";

    //Payment type
    public static final String PARKING_BOOKING   = "PARKING_BOOKING";
    public static final String CHAUFFEUR_SERVICE = "CHAUFFEUR_SERVICE";
    public static final String EMERGENCY_SERVICE = "EMERGENCY_SERVICE";
    public static final String MEKCOINS_WALLET_ADDITION = "MEKCOINS_WALLET_ADDITION ";

    public static final String NOTIFY_VIA_CALL   = "NOTIFY_VIA_CALL";
    public static final String NOTIFY_VIA_SMS    = "NOTIFY_VIA_SMS";

    public static final String REDEEMED_CODE     = "REDEEMED_CODE";

    //Notification types
    public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";

    public static final String NORMAL = "NORMAL";
    public static final String GOT_STUCK_AMONG_VEHICLE = "GOT_STUCK_AMONG_VEHICLE";
    //For feedback to customer
    public static final String GOT_STUCK_AMONG_VEHICLE_FEEDBACK = "GOT_STUCK_AMONG_VEHICLE_FEEDBACK";
    public static final String REWARD_EARNED = "REWARD_EARNED";


    @SuppressLint("ResourceAsColor")
    public static void setStatusBarColor(Window window, int a){

        // if a=0 => primarydark color else light black

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(a == 0)
            window.setStatusBarColor(Color.parseColor("#B71C1C"));
        else
            window.setStatusBarColor(Color.argb(255, 133, 146, 158));
    }

    public static void sendNavigateIntent(Context context, double latitude, double longitude){

        String uri = "google.navigation:q="+latitude+","+longitude;

        Uri gmmIntentUri = Uri.parse(uri);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);

    }

    public static void sendEmailIntent(Context context){

        String email_id = "support@mekpark.com";

        String email_subject = "Feedback for Trango App";


        Intent emailIntent =  new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto",email_id,null));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, email_subject);


        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        if(isIntentSafe)
            context.startActivity(Intent.createChooser(emailIntent,"Send email via ..."));
        else
            Toast.makeText(context,"Email can't be send from here",Toast.LENGTH_SHORT).show();


    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

//    public static void handleVolleyError(Context context, VolleyError error){
//
//        if(error instanceof TimeoutError){
//            Toast.makeText(context,"Timeout Error",Toast.LENGTH_LONG).show();
//        }
//        else if (error instanceof NoConnectionError){
//            Toast.makeText(context,"No Connection Error",Toast.LENGTH_LONG).show();
//        }
//        else if (error instanceof AuthFailureError){
//            Toast.makeText(context,"Authentication Error",Toast.LENGTH_LONG).show();
//        }
//        else if (error instanceof ServerError){
//            Toast.makeText(context,"Server Error",Toast.LENGTH_LONG).show();
//        }
//        else if (error instanceof NetworkError){
//            Toast.makeText(context,"Network Error",Toast.LENGTH_LONG).show();
//        }
//        else if(error instanceof ParseError){
//            Toast.makeText(context,"Parse Error",Toast.LENGTH_LONG).show();
//        }
//        else {
//            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
//        }
//    }

    // Format : 06:45 PM
    public static String getFormattedTime(String TAG, String s) {

        String time = "NA";
        Date date;
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

        try {
            Long unix   = Long.valueOf(s);
            date        = new java.util.Date(unix*1000L);
            time        = sdf.format(date);

        }catch (Exception e){
            Log.e(TAG,"Exception cought while converting time : "+e.toString());

        }

        return time;

    }

    // Format : 1st Mar, 2019
    public static String getFormattedDate(String TAG, String unix) {

        String formatedstring;

        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        Long longUnix = null;

        try {

            longUnix = Long.valueOf(unix);
            String date = sdf.format(new Date(longUnix * 1000L));

            if (date.endsWith("1") && !date.endsWith("11"))
                sdf = new SimpleDateFormat("d'st' MMM, yyyy", Locale.getDefault());
            else if (date.endsWith("2") && !date.endsWith("12"))
                sdf = new SimpleDateFormat("d'nd' MMM, yyyy", Locale.getDefault());
            else if (date.endsWith("3") && !date.endsWith("13"))
                sdf = new SimpleDateFormat("d'rd' MMM, yyyy", Locale.getDefault());
            else
                sdf = new SimpleDateFormat("d'th' MMM, yyyy", Locale.getDefault());

        }catch (Exception e){
            Log.e(TAG,"Exception cought while converting time : "+e.toString());
        }


        formatedstring = sdf.format(new Date(longUnix*1000L));


        return formatedstring;



    }

    public static String getFormattedDate2(String TAG, String unix) {

        String formatedstring;

        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        Long longUnix = null;

        try {

            longUnix = Long.valueOf(unix);
            String date = sdf.format(new Date(longUnix * 1000L));

            if (date.endsWith("1") && !date.endsWith("11"))
                sdf = new SimpleDateFormat("EEEE, d'st' MMM", Locale.getDefault());
            else if (date.endsWith("2") && !date.endsWith("12"))
                sdf = new SimpleDateFormat("EEEE, d'nd' MMM", Locale.getDefault());
            else if (date.endsWith("3") && !date.endsWith("13"))
                sdf = new SimpleDateFormat("EEEE, d'rd' MMM", Locale.getDefault());
            else
                sdf = new SimpleDateFormat("EEEE, d'th' MMM", Locale.getDefault());

        }catch (Exception e){
            Log.e(TAG,"Exception cought while converting time : "+e.toString());
        }

        formatedstring = sdf.format(new Date(longUnix*1000L));
        return formatedstring;
    }

//    public static void sentNotificationToPartner(Context context, final int partnerId, final String title, final String message){
//
//        Log.e(TAG,"called: sendNotificationToPartner");
//
//        final String URL_NOTI = BASE_URL + "send_notification_to_partner.php";
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NOTI, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.e(TAG,response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG,error.toString());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("partner_id", String.valueOf(partnerId));
//                params.put("n_title",title);
//                params.put("n_message",message);
//                return params;
//            }
//        };
//
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(RETRY_SECONDS*1000,NO_OF_RETRY,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getInstance(context).addToRequestQueue(stringRequest);
//
//    }

    public static LatLng getDeviceLocation(Context context) throws Exception {

        LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1000);

        GPSTracker tracker = new GPSTracker(context);

        if (!tracker.canGetLocation()) {
            Log.e("asdf","shoud show aleart");
            tracker.showSettingsAlert();
        }

        return new LatLng(tracker.getLatitude(), tracker.getLongitude());
    }

//    public static void showExtraBill2(final Context context, String extraDur, String extraBaseFare, String discount,
//                                      String extraAddCharges, String totalExtraFare) {
//
//        final Dialog dialog = new Dialog(context);
//
//        View view = LayoutInflater.from(context).inflate(R.layout.due_page2, null);
//
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(view);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//
//        view.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        TextView tv_totalFare1   = view.findViewById(R.id.total_extra_fare);
//        TextView tv_needHelp     = view.findViewById(R.id.need_help);
//        TextView tv_eDur         = view.findViewById(R.id.total_extra_duration);
//        TextView tv_eBaseFare    = view.findViewById(R.id.extra_base_fare);
//        TextView tv_discount     = view.findViewById(R.id.discount);
//        TextView tv_eAddCharges  = view.findViewById(R.id.extra_additinal_charges);
//        TextView tv_totalFare2   = view.findViewById(R.id.total_extra_fare2);
//
//
//        tv_eDur.setText(formatTimeForBill(Long.parseLong(extraDur)*1000));
//
//        tv_eBaseFare.setText(extraBaseFare);
//        tv_discount.setText(discount);
//        tv_eAddCharges.setText(extraAddCharges);
//
//        tv_totalFare1.setText(totalExtraFare);
//        tv_totalFare2.setText(totalExtraFare);
//
//        tv_needHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context,"Need Help",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        dialog.show();
//
//    }

    public static String formatTimeForBill(long timeinMilli) {

        int hrs     = (int) (timeinMilli / 1000) / 3600;
        int minutes = (int) ( ((timeinMilli / 1000) / 60 ) - ( hrs * 60) );
        int seconds = (int) (timeinMilli / 1000) % 60;

        Log.e("hrs:min:time",hrs+":"+minutes+":"+seconds);

        String formattedTime = String.format(Locale.getDefault(), "%2d:%02d:%02d", hrs,minutes, seconds);

        if(hrs == 0){

            if(minutes == 0)
                formattedTime = seconds + " Sec";
            else
                formattedTime = minutes + " Min";
        }
        else {
            formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hrs,minutes);
            formattedTime = formattedTime + " Hrs";
        }

        return formattedTime;

    }

    public static void callIntent(Context context, String phoneNumber){

        String phone = phoneNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        context.startActivity(intent);

    }

}