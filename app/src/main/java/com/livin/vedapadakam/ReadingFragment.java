package com.livin.vedapadakam;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.livin.vedapadakam.database.DBConstants;
import com.livin.vedapadakam.database.DbManager;
import com.livin.vedapadakam.model.ReadingModel;

public class ReadingFragment extends Fragment {

    public LinearLayout container;
    public TextView heading;
    public LinearLayout updateLayout;
    public static int textsizeadder;
    public static ReadingFragment obj;
    public static String currentDate;
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        obj = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contain,
                                Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Context context = getActivity().getApplicationContext();

        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        textsizeadder = sharedPref.getInt(getString(R.string.text_size), 3);
        View rootView = inflater.inflate(R.layout.reading_main, contain, false);


        DbManager DBMObj = DbManager.getInstance();
        DBMObj.checkDataBase(getActivity().getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
            }
        });
        container = (LinearLayout)rootView.findViewById(R.id.container);
        updateLayout = (LinearLayout)rootView.findViewById(R.id.update_layout);
        heading = (TextView)rootView.findViewById(R.id.heading);
        obj = this;
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
        checkDate(formatter.format(date),false);
        return rootView;
    }


    final public void filldata(Date objDate,String date)
    {
        float SIZE_HEADER = 12 + 2 * textsizeadder;
        float SIZE_TEXT = 10 + 2 * textsizeadder;
        Activity activity = obj.getActivity();
        DbManager db = DbManager.getInstance();

        ArrayList<ReadingModel> models = db.getReading(activity.getApplicationContext(), date);
        if(updateLayout!=null)
            updateLayout.setVisibility(View.GONE);
        obj.container.removeAllViews();

        for (int i = 0; i < models.size(); i++) {

            ReadingModel r  = models.get(i);

            View header = activity.getLayoutInflater().inflate(R.layout.header, container, false);

            String headertext = r.getHeading();


            TextView heading = (TextView) header.findViewById(R.id.heading);
            heading.setTextSize(SIZE_HEADER);
            if("".equals(headertext)){
                heading.setVisibility(View.GONE);
            }

            TextView date_view = (TextView) header.findViewById(R.id.date);
            date_view.setTextSize(SIZE_HEADER);

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy");
            date_view.setText(formatter2.format(objDate));
            heading.setText(headertext);

            obj.container.addView(header);

            boolean ishtml = r.isSpecial();

            if(ishtml){
                TextView txtView = new TextView(activity.getApplicationContext());
                txtView.setText(Html.fromHtml(r.getR1()));
                txtView.setTextIsSelectable(true);
                txtView.setTextSize(SIZE_TEXT);
                txtView.setTextColor(activity.getResources().getColor(R.color.defaulttextcolor));
                obj.container.addView(txtView);
            }
            else {
                TextView head;
                TextView tail;
                TextView readingText;

                View first_reading = activity.getLayoutInflater().inflate(R.layout.first_reading, obj.container, false);
                readingText = (TextView) first_reading.findViewById(R.id.first_reading_text);
                readingText.setTextSize(SIZE_TEXT);
                readingText.setText(r.getR1());

                head = (TextView) first_reading.findViewById(R.id.first_reading_heading);
                head.setTextSize(SIZE_HEADER);

                tail = (TextView) first_reading.findViewById(R.id.first_reading_ending);
                tail.setTextSize(SIZE_TEXT);

                obj.container.addView(first_reading);

                View prathivachanam = activity.getLayoutInflater().inflate(R.layout.prathivachanam, obj.container, false);
                readingText = (TextView) prathivachanam.findViewById(R.id.prathivachanam_text);
                readingText.setText(r.getP());
                readingText.setTextSize(SIZE_TEXT);
                head = (TextView) prathivachanam.findViewById(R.id.prathivachanam_heading);
                head.setTextSize(SIZE_HEADER);

                obj.container.addView(prathivachanam);

                if (!"".equals(r.getR2()) && r.getR2() != null) {
                    View second_reading = activity.getLayoutInflater().inflate(R.layout.second_reading, obj.container, false);
                    readingText = (TextView) second_reading.findViewById(R.id.second_reading_text);
                    readingText.setText(r.getR2());
                    readingText.setTextSize(SIZE_TEXT);

                    head = (TextView) second_reading.findViewById(R.id.second_reading_heading);
                    head.setTextSize(SIZE_HEADER);

                    tail = (TextView) second_reading.findViewById(R.id.second_reading_ending);
                    tail.setTextSize(SIZE_TEXT);

                    TextView prathivachanamtext2 = (TextView) second_reading.findViewById(R.id.prathivachanam_text2);
                    prathivachanamtext2.setText(r.getP2());
                    prathivachanamtext2.setTextSize(SIZE_TEXT);

                    obj.container.addView(second_reading);

                }


                View gospel = activity.getLayoutInflater().inflate(R.layout.gospel, obj.container, false);
                readingText = (TextView) gospel.findViewById(R.id.gospel_text);
                readingText.setText(r.getS());
                readingText.setTextSize(SIZE_TEXT);

                head = (TextView) gospel.findViewById(R.id.gospel_heading);
                head.setTextSize(SIZE_HEADER);

                tail = (TextView) gospel.findViewById(R.id.gospel_ending);
                tail.setTextSize(SIZE_TEXT);

                obj.container.addView(gospel);
            }
        }
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {

            String date = day+"-"+(month+1)+"-"+year;
            obj.checkDate(date,true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void checkDate(String date, boolean withtoast)
    {
        Date min,max,objDate=null;
        SimpleDateFormat formatter = new SimpleDateFormat("d-M-yyyy");
        int safe = 0;
        try {

            min = formatter.parse("14-5-2016");
            max = formatter.parse("31-1-2018");
            objDate= formatter.parse(date);
            if((objDate.compareTo(min)>=0 && objDate.compareTo(max)<=0 )||objDate.compareTo(formatter.parse("9-5-2016"))==0)
            {
                safe =1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(safe == 1) {
            currentDate = date;
            filldata(objDate,date);
        }
        else if(withtoast)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a date between 14th May 2016 and 31st January 2018", Toast.LENGTH_SHORT).show();
        }
        else{
            addUpdatefragment();
        }
    }

    public void addUpdatefragment(){
        obj.container.removeAllViews();
        updateLayout.setVisibility(View.VISIBLE);
         Button btn = (Button) updateLayout.findViewById(R.id.btn_update);
         btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(Intent.ACTION_VIEW);
                 intent.setData(Uri.parse("market://details?id=com.livin.vedapadakam"));
                 startActivity(intent);
             }
         });

    }

    public void updateTextSize(int nextTextSize){
        textsizeadder = nextTextSize;
        if(currentDate != null)
            checkDate(currentDate,false);
    }

}
