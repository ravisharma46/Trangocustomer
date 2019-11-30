package com.naruto.trango.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.naruto.trango.R;
import com.naruto.trango.commonfiles.CommonVariablesFunctions;
import com.naruto.trango.homepage.MainHomepage;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        LoginCredentialFragment.OnFragmentInteractionListener, SignupFragment.OnFragmentInteractionListener{

    private ImageView iv_bg;
    private LinearLayout footerLayout;
    private ProgressDialog progdialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progdialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();


        iv_bg = findViewById(R.id.bg_img);
        TextView tvFaq = findViewById(R.id.faq);
        TextView tvFeedback = findViewById(R.id.send_feedback);
        footerLayout = findViewById(R.id.login_footer);

        tvFaq.setOnClickListener(view ->
                Snackbar.make(view, "To be implemented", BaseTransientBottomBar.LENGTH_SHORT).show());

        tvFeedback.setOnClickListener(view -> CommonVariablesFunctions.sendEmailIntent(LoginActivity.this));

        replaceFragment(LoginFragment.newInstance(), "LoginFragment");

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()<3) {
            footerLayout.setVisibility(View.VISIBLE);
        }
        super.onBackPressed();


    }

    @Override
    protected void onResume() {
        super.onResume();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int windowWidth = size.x;
        int windowHeight = size.y;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                windowWidth, (windowHeight / 2)-40);
        iv_bg.setLayoutParams(params);
        iv_bg.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void replaceFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (!fragmentTag.equals("LoginFragment")) {
            ft.addToBackStack(fragmentTag);
        }
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Bundle view) {

        String fragmentName = view.getString("Fragment");

        assert fragmentName != null;
        switch (fragmentName) {
            case "signUp":
                replaceFragment(SignupFragment.newInstance(), "SignUp");
                break;
            case "LoginCredential":
                //            String password = view.getString("Password");
//            String phoneNumber = view.getString("Phone");
//
//            if( !TextUtils.isEmpty(phoneNumber) || !TextUtils.isEmpty(password)){
//                progdialog.setTitle("Logging In");
//                progdialog.setMessage("Please Wait while we check your Credential");
//                progdialog.setCanceledOnTouchOutside(false);
//                progdialog.show();
//
//               // loginUser(phoneNumber,password);
//            }
                footerLayout.setVisibility(View.GONE);
                replaceFragment(LoginCredentialFragment.newInstance(), "LoginCredential");
                break;
            case "BackToLoginCredential":
                String emailId = view.getString("Email");
                String password = view.getString("Password");
                String name = view.getString("Name");
                String phoneNumber = view.getString("Phone");
                String spinnerdata = view.getString("Spinner");

                if (!TextUtils.isEmpty(emailId) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(name) || !TextUtils.isEmpty(phoneNumber) || !TextUtils.isEmpty(spinnerdata)) {

                    progdialog.setTitle("Register User");
                    progdialog.show();
                    progdialog.setCanceledOnTouchOutside(false);
                    progdialog.setMessage("Please wait while we creating your account");

                    registerUser(emailId, password, name, phoneNumber, spinnerdata);
                }
                break;
            case "MainHomePage":
                Intent intent = new Intent(LoginActivity.this, MainHomepage.class);
                startActivity(intent);
                break;
        }


    }



    private void registerUser(String emailId, String password, String name, String phoneNumber, String spinnerdata) {

        mAuth.createUserWithEmailAndPassword(emailId,password).addOnCompleteListener(LoginActivity.this, task -> {
            if(task.isSuccessful()){

                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = firebaseUser.getUid();

                mdatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                HashMap<String,String> usermap = new HashMap<>();
                usermap.put("name",name);
                usermap.put("password",password);
                usermap.put("phoneNumber",phoneNumber);
                usermap.put("spinnerdata",spinnerdata);

                mdatabase.setValue(usermap).addOnCompleteListener(task1 -> {

                    if(task1.isSuccessful()){

                        progdialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().popBackStack();
                    }
                });

            }else{
                progdialog.hide();
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        });

    }


}