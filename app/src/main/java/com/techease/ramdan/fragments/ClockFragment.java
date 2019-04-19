package com.techease.ramdan.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iigo.library.ClockHelper;
import com.iigo.library.ClockView;
import com.techease.ramdan.R;
import com.techease.ramdan.classes.MyVectorClock;
import com.techease.ramdan.utilities.GeneralUtils;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class ClockFragment extends Fragment {
    View view;
    ClockView mClockView;
    ClockHelper clockHelper;
    TextView tvIftarTime, tvSehriTime, tvCountDown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clock, container, false);
        tvIftarTime = view.findViewById(R.id.iftar_time);
        tvSehriTime = view.findViewById(R.id.sehri_time);
        tvCountDown = view.findViewById(R.id.tv_countdown);
        mClockView = view.findViewById(R.id.clockView);
        clockHelper = new ClockHelper(mClockView);
        clockHelper.start();
        initViews();
        return view;
    }

    private void initViews() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -2);

        mClockView.setTime(Calendar.HOUR, Calendar.MINUTE, Calendar.SECOND);

        tvSehriTime.setText(GeneralUtils.getSehriTime(getActivity()));
        tvIftarTime.setText(GeneralUtils.getIftarTime(getActivity()));

        countDownTimer();
    }

    private void countDownTimer() {

        new CountDownTimer(200000, 1000) {

            public void onTick(long millisUntilFinished) {
                String text =
                        String.format(Locale.getDefault(), "Time Remaining\n %02d min: %02d sec",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                tvCountDown.setText(text);

            }

            public void onFinish() {
                tvCountDown.setText("Iftar Time!");
            }

        }.start();
    }
}
