package com.adria.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.adria.contract.GContactContract;
import com.adria.presenter.GContactPresenter;

import com.adria.R;

public class GContactActivity extends AppCompatActivity implements  GContactContract.View {

    private static final String TAG = "MainActivity";
    GContactPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gcontact_main);

        mPresenter = new GContactPresenter(this);


        try
        {
            mPresenter.setUpForPeopleAPI();
            mPresenter.getCOnnections();

        }catch (Exception ex){

            Log.i("Setup","Setup ex: "+ex.toString());
        }



    }




}


