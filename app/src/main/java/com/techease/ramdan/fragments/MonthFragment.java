package com.techease.ramdan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techease.ramdan.R;
import com.techease.ramdan.adapters.TimesAdapter;
import com.techease.ramdan.models.MonthTimingModel;
import com.techease.ramdan.utilities.AlertUtils;
import com.techease.ramdan.utilities.Config;
import com.techease.ramdan.utilities.GeneralUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MonthFragment extends Fragment {
    android.support.v7.app.AlertDialog alertDialog;
    View view;
    @BindView(R.id.rv_month_data)
    RecyclerView rvRamdanTiming;
    ArrayList<MonthTimingModel> timingModelArrayList;
    TimesAdapter timesAdapter;

    String strLat = "33.996132",
            strLng = "71.465827",
            strMonth = "4",
            strYear = "2019";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_month, container, false);
        ButterKnife.bind(this,view);
        getActivity().setTitle("Ramadan Timing");

        initViews();
        return view;
    }

    private void initViews() {
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH);
        strMonth = String.valueOf(month + 1);

        strLat = GeneralUtils.getLatitude(getActivity());
        strLng = GeneralUtils.getLongitude(getActivity());

        Toast.makeText(getActivity(), strLat, Toast.LENGTH_SHORT).show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvRamdanTiming.setLayoutManager(linearLayoutManager);
        timingModelArrayList = new ArrayList<>();
        alertDialog = AlertUtils.createProgressDialog(getActivity());
        alertDialog.show();
        getCategories();
    }


    private void getCategories() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.GET_TIMING + strLat + "&longitude=" + strLng + "&method=2&month=" + strMonth + "&year=" + strYear
                , new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    alertDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject itemObject = jsonArray.getJSONObject(i);

                        JSONObject timingObject = itemObject.getJSONObject("timings");

                        String strSeharTime = timingObject.getString("Fajr");
                        String strIftarTime = timingObject.getString("Maghrib");

                        JSONObject dateObject = itemObject.getJSONObject("date");
                        JSONObject hijriObject = dateObject.getJSONObject("hijri");
                        JSONObject hijriMonthObject = hijriObject.getJSONObject("month");


                        String dayHijriMonth = hijriObject.getString("day");
                        String hijriMonthName = hijriMonthObject.getString("en");

                        JSONObject gregorianObject = dateObject.getJSONObject("gregorian");
                        String date = gregorianObject.getString("date");

                        MonthTimingModel model = new MonthTimingModel();
                        model.setStrMonthDay(date);
                        model.setStrHijriDay(dayHijriMonth + " " + hijriMonthName);
                        model.setStrSeharTime(strSeharTime);
                        model.setStrIftarTime(strIftarTime);


                        timingModelArrayList.add(model);
                        timesAdapter = new TimesAdapter(getActivity(), timingModelArrayList);
                        rvRamdanTiming.setAdapter(timesAdapter);
                        timesAdapter.notifyDataSetChanged();

                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        String formattedDate = df.format(c);

                        if (formattedDate.equals(date)) {
                            rvRamdanTiming.scrollToPosition(i - 3);
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }

        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }

}
