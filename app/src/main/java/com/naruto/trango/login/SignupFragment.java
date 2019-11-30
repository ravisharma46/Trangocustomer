package com.naruto.trango.login;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.naruto.trango.R;

public class SignupFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private TextInputEditText phoneNumber, emailId, name, password;
    private Spinner mySpinner;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View inflate = inflater.inflate(R.layout.fragment_signup, container, false);

        phoneNumber = inflate.findViewById(R.id.phone_no);
        emailId = inflate.findViewById(R.id.email);
        name = inflate.findViewById(R.id.name);
        password = inflate.findViewById(R.id.password);
        mySpinner = inflate.findViewById(R.id.spinner_partner);
        onCLickListener(inflate);
        return inflate;
    }

    private void onCLickListener(View inflate) {
        inflate.findViewById(R.id.btn_signup).setOnClickListener(view -> {

            String phoneNumber = this.phoneNumber.getEditableText().toString();
            String emailId = this.emailId.getEditableText().toString();
            String name = this.name.getEditableText().toString();
            String password = this.password.getEditableText().toString();
            String spinnerdata = this.mySpinner.getSelectedItem().toString();

            Bundle bundle = new Bundle();
            bundle.putString("Password", password);
            bundle.putString("Email", emailId);
            bundle.putString("Name", name);
            bundle.putString("Phone", phoneNumber);
            bundle.putString("Spinner",spinnerdata);
            bundle.putString("Fragment","BackToLoginCredential");
            onButtonPressed(bundle);
        });
    }

    public void onButtonPressed(Bundle view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);

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
        void onFragmentInteraction(Bundle view);
    }
}
