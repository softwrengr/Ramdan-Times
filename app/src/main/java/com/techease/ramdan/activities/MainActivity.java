package com.techease.ramdan.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techease.ramdan.R;
import com.techease.ramdan.fragments.HomeFragment;
import com.techease.ramdan.utilities.GeneralUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeneralUtils.connectFragmentWithoutBack(this,new HomeFragment());
    }
}
