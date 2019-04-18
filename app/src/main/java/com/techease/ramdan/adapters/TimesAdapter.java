package com.techease.ramdan.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.techease.ramdan.R;
import com.techease.ramdan.models.MonthTimingModel;
import com.techease.ramdan.utilities.GeneralUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimesAdapter extends RecyclerView.Adapter<TimesAdapter.MyViewHolder> {
    List<MonthTimingModel> allProductsModelArrayList;
    Context context;

    public TimesAdapter(Activity context, List<MonthTimingModel> allProductsModelArrayList) {
        this.context = context;
        this.allProductsModelArrayList = allProductsModelArrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_month_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public long getItemId(int position) {
        final MonthTimingModel model = allProductsModelArrayList.get(position);
        if (allProductsModelArrayList != null && allProductsModelArrayList.size() > position)
            return allProductsModelArrayList.size();
        return 0;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int position) {
        final MonthTimingModel model = allProductsModelArrayList.get(position);

        viewHolder.tvMonth.setText(model.getStrMonthDay());
        viewHolder.tvHijriDay.setText(model.getStrHijriDay());
        viewHolder.tvSeharTime.setText(model.getStrSeharTime());
        viewHolder.tvIftareTime.setText(model.getStrIftarTime());


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c);

        if (formattedDate.equals(model.getStrMonthDay())) {
            viewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.green));
            GeneralUtils.putStringValueInEditor(context, "sehri_time", model.getStrSeharTime());
            GeneralUtils.putStringValueInEditor(context, "iftar_time", model.getStrIftarTime());
        }
        else {
            viewHolder.layout.setBackgroundColor(context.getResources().getColor(R.color.header_color));
        }


    }

    @Override
    public int getItemCount() {
        return allProductsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMonth, tvHijriDay, tvSeharTime, tvIftareTime;
        LinearLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMonth = itemView.findViewById(R.id.tv_month);
            tvHijriDay = itemView.findViewById(R.id.tv_hijri);
            tvSeharTime = itemView.findViewById(R.id.tv_sehar);
            tvIftareTime = itemView.findViewById(R.id.tv_iftar);
            layout = itemView.findViewById(R.id.layout);

        }
    }

}

