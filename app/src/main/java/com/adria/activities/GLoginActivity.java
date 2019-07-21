package com.adria.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.adria.contract.GLoginContract;
import com.adria.presenter.GLoginPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import android.support.v7.app.AppCompatActivity;

import com.adria.R;

public class GLoginActivity extends AppCompatActivity implements View.OnClickListener, GLoginContract.View {

    private static final String TAG = "MainActivity";

    SignInButton signInButton;
    Button goContact;

    private int userIdCounter = 0;
    private Context mContext;

    //private FragmentActivity mFragmentActivity;
    private GoogleTokenResponse mResponseListener;
    GLoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glogin_main);

        mPresenter = new GLoginPresenter(this);




        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);


        goContact= findViewById(R.id.contacplus);

        signInButton.setOnClickListener(this);
        goContact.setOnClickListener(this);

        mContext = this;
        mPresenter.signIn(this);
        updateUI(null);


    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                mPresenter.startSignIn((Activity) mContext);
                break;
            case R.id.contacplus:
                finish();
                startActivity(new Intent(this, GContactActivity.class));

                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleGLoginClient.getSignInIntent(...);
        if (requestCode == GLoginPresenter.RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mPresenter.handleSignInResult(task);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    @Override
    public void updateUI(GoogleSignInAccount account) {

        if(account!=null){

            setMessage(account.getDisplayName());
            signInButton.setVisibility(View.INVISIBLE);

        }else
        {
            setMessage( getResources().getString(R.string.notConnected));
        }


    }



    @Override
    public void setMessage(String text){
        ((TextView)findViewById(R.id.status)).setText(text);
    }
}


