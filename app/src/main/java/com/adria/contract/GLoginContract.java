package com.adria.contract;

import android.app.Activity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;

/**
 * Represents the View and Presenter contract
 */
public interface GLoginContract {
    interface View {
        void setMessage(String message);
        void updateUI(GoogleSignInAccount account);
    }

    interface Presenter {
        void startSignIn(Activity mContext);
        void handleSignInResult(Task<GoogleSignInAccount> completedTask);
    }
}
