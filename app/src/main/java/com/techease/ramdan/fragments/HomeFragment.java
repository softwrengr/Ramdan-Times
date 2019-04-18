package com.techease.ramdan.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.techease.ramdan.R;
import com.techease.ramdan.utilities.GeneralUtils;

import butterknife.ButterKnife;


public class HomeFragment extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Ramadan Timing");
        initUI();
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GeneralUtils.connectHomeFragment(getActivity(), new MonthFragment());
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_month:
                    GeneralUtils.connectHomeFragment(getActivity(), new MonthFragment());
                    return true;
                case R.id.navigation_clock:
                    GeneralUtils.connectHomeFragment(getActivity(), new ClockFragment());
                    return true;
                case R.id.navigation_duaw:
                    GeneralUtils.connectHomeFragment(getActivity(), new DuasFragment());
                    return true;

                case R.id.navigation_contact:
                    GeneralUtils.connectHomeFragment(getActivity(), new MonthFragment());
                    return true;
            }
            return false;
        }
    };

}
