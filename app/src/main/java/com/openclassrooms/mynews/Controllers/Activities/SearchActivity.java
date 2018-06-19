package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.openclassrooms.mynews.R;
import com.openclassrooms.mynews.Utils.MyApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Activity used for article search
 */
public class SearchActivity extends AppCompatActivity {

    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog.OnDateSetListener dateListener;
    private DatePickerDialog.OnDateSetListener dateListener2;
    private String sDate = "";
    private String eDate = "";
    private Date date;
    private TextInputEditText searchQuery;
    private CheckBox cb_arts;
    private CheckBox cb_politics;
    private CheckBox cb_business;
    private CheckBox cb_sports;
    private CheckBox cb_entrepreneurs;
    private CheckBox cb_travel;
    private String section = "type_of_material:News";
    private final List<CheckBox> cb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Setting up the return button and the activity title displayed in the action bar
        Objects.requireNonNull(Objects.requireNonNull(getSupportActionBar())).setDisplayHomeAsUpEnabled(true);

        // Finding the two EditText responsible for managing dates in the search activity
        startDate = findViewById(R.id.search_begin_date);
        endDate = findViewById(R.id.search_end_date);

        // Setting action when the startDate EditText is focused
        startDate.setOnFocusChangeListener((v, hasFocus) -> {
            if(hasFocus) {
                // Setting up a new Calendar object in order to retrieve the date of the day
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                // Will create a new DatePickerDialog object with the date of the day
                DatePickerDialog dialog = new DatePickerDialog(
                        SearchActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateListener,
                        year,
                        month,
                        day);
                // Set DatePickerDialog background transparent
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Same as setOnFocusChangeListener but this time when the EditText has already been modified
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
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            });

        // Will update the concerned EditText with the date value chosen by the user
        dateListener = (view, year, month, dayOfMonth) -> {
            String monthS = Integer.toString(month + 1);
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
            String monthS = Integer.toString(month + 1);
            String dayS = Integer.toString(dayOfMonth);
            if (month <10) {
                monthS = "0" + monthS;
            }
            if (dayOfMonth < 10){
                dayS = "0" + dayS;
            }
            endDate.setText(dayS + "/" + monthS + "/" + year);
        };

        searchQuery = findViewById(R.id.query_include);
        if (searchQuery == null){
            searchQuery = findViewById(R.id.search_query_term);
        }


        String inputPattern = "dd/MM/yyyy";
        String outputPattern = "yyyyMMdd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Button searchButton = findViewById(R.id.search_submit_button);
        searchButton.setOnClickListener(view -> {
            cb.clear();
            section = "type_of_material:News";
            eDate = "";
            sDate = "";

            cb_arts = findViewById(R.id.search_cb_arts);
            cb_business = findViewById(R.id.search_cb_business);
            cb_politics = findViewById(R.id.search_cb_politics);
            cb_sports = findViewById(R.id.search_cb_sports);
            cb_entrepreneurs = findViewById(R.id.search_cb_entrepreneurs);
            cb_travel = findViewById(R.id.search_cb_travel);

            cb.add(cb_arts);
            cb.add(cb_business);
            cb.add(cb_politics);
            cb.add(cb_sports);
            cb.add(cb_entrepreneurs);
            cb.add(cb_travel);

            int count = 0;

            for(int i = 0; i < cb.size(); i++){
                if(cb.get(i).isChecked()) {
                    if(count == 0) {
                        section = section + " AND news_desk:(" + cb.get(i).getText().toString();
                        count++;
                    }
                    else if(count > 0){
                        section = section + " OR " + cb.get(i).getText().toString();
                        count++;
                    }
                }
            }

            if(count > 0)
                section = section + ")";

            if(count==0){
                Toast.makeText(MyApplication.getAppContext(), "Please specify at least one category to search", Toast.LENGTH_LONG).show();
            }
            else if(searchQuery.getText().toString().isEmpty() || searchQuery.getText().toString().trim().length() == 0){
                Toast.makeText(MyApplication.getAppContext(), "Please enter at least one term in the search bar", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    date = inputFormat.parse(startDate.getText().toString());
                    sDate = outputFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    date = inputFormat.parse(endDate.getText().toString());
                    eDate = outputFormat.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(view.getContext(), SearchResultActivity.class);
                intent.putExtra("SearchQuery", searchQuery.getText().toString());
                intent.putExtra("start_date", sDate);
                intent.putExtra("end_date", eDate);
                intent.putExtra("section", section);
                view.getContext().startActivity(intent);
            }
        });

    }

    /**
     * Function required for the back button to return to the previous activity
     * @return True if succeeded
     */
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
