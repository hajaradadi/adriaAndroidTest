package com.adria.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.adria.contract.GLoginContract;
import com.adria.model.GLoginModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

/**
 * Represents the P part in MVP architecture
 * Simple presenter that implements {@link GLoginContract.Presenter},
 * delegate view requests to model, and update the view with data
 */
public class GLoginPresenter implements GLoginContract.Presenter,GoogleApiClient.OnConnectionFailedListener {
    GLoginModel mModel;
    GLoginContract.View mView;
    public GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 9001;
    GoogleSignInAccount account;
    public static String TAG="GLoginPresenter";

    public GLoginPresenter(GLoginContract.View view) {
        this.mView = view;
    }



    public void signIn(Context mContext){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestScopes(new Scope(Scopes.PLUS_ME))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mContext, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                //.enableAutoManage(mContext, mContext)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("ConnectionResult", "G+ " + connectionResult.toString());
    }


    @Override
    public void startSignIn(Activity mContext){


        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        mContext.startActivityForResult(signInIntent, RC_SIGN_IN);
    }




    @Override
    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            Log.w("TAG", "signInResult:failed code=" + account.getDisplayName());
            // Signed in successfully, show authenticated UI.
            mView.updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            mView.updateUI(null);
        }
    }

}
