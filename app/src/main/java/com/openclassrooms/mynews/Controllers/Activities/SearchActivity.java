package com.openclassrooms.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.openclassrooms.mynews.R;

import java.util.Calendar;

/**
 * Activity used for article search
 */
public class SearchActivity extends AppCompatActivity {

    private EditText startDate;
    private EditText endDate;
    private DatePickerDialog.OnDateSetListener dateListener;
    private DatePickerDialog.OnDateSetListener dateListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Setting up the return button and the activity title displayed in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

        // Will update the concerned EditText with the date value chosen by the user
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

        TextInputEditText searchQuery = findViewById(R.id.query_include);
        if (searchQuery == null){
            searchQuery = findViewById(R.id.search_query_term);
        }

        Button searchButton = findViewById(R.id.search_submit_button);
        TextInputEditText finalSearchQuery = searchQuery;
        searchButton.setOnClickListener(view -> {
            Log.d("Search", finalSearchQuery.toString());
            Intent intent = new Intent(view.getContext(), SearchResultActivity.class);
            intent.putExtra("SearchQuery", finalSearchQuery.getText().toString());
            view.getContext().startActivity(intent);
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
