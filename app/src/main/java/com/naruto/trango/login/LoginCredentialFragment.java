package com.naruto.trango.login;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.naruto.trango.R;

public class LoginCredentialFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private TextInputEditText phoneNumber,password;

    public LoginCredentialFragment() {
        // Required empty public constructor
    }

    public static LoginCredentialFragment newInstance() {
        return new LoginCredentialFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_login_credential, container, false);

        phoneNumber = inflate.findViewById(R.id.phone_no);
        password = inflate.findViewById(R.id.password);

        onClickListener(inflate);
        return inflate;
    }

    private void onClickListener(View inflate) {
        inflate.findViewById(R.id.login_to_app).setOnClickListener(view -> {

            Bundle bundle = new Bundle();
            bundle.putString("phone", phoneNumber.getEditableText().toString());
            bundle.putString("password", password.getEditableText().toString());
            bundle.putString("Fragment","MainHomePage");
            onButtonPressed(bundle);
        });

        inflate.findViewById(R.id.btv_signup).setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("Fragment","signUp");
            onButtonPressed(bundle);
        });
    }

    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Bundle bundle);
    }
}
