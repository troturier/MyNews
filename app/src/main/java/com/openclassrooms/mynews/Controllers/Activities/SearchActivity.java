package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.openclassrooms.mynews.R;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog.OnDateSetListener dateListener;
    private DatePickerDialog.OnDateSetListener dateListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search Articles");

        startDate = findViewById(R.id.search_begin_date);
        endDate = findViewById(R.id.search_end_date);

        startDate.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener,
                        year,
                        month,
                        day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    SearchActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateListener,
                    year,
                    month,
                    day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        endDate.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener2,
                        year,
                        month,
                        day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            });

        endDate.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    SearchActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    dateListener2,
                    year,
                    month,
                    day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            });

        dateListener = (view, year, month, dayOfMonth) -> {
            String monthS = Integer.toString(month);
            String dayS = Integer.toString(dayOfMonth);
            if (month <10) {
                monthS = "0" + monthS;
            }
            if (dayOfMonth < 10){
                dayS = "0" + dayS;
            }
            startDate.setText(dayS + "/" + monthS + "/" + year);
        };

        dateListener2 = (view, year, month, dayOfMonth) -> {
            String monthS = Integer.toString(month);
            String dayS = Integer.toString(dayOfMonth);
            if (month <10) {
                monthS = "0" + monthS;
            }
            if (dayOfMonth < 10){
                dayS = "0" + dayS;
            }
            endDate.setText(dayS + "/" + monthS + "/" + year);
        };
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
