package com.devwellupsolution.dev_cms.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devwellupsolution.dev_cms.Asynctask.SendCreateAnnouncementData;
import com.devwellupsolution.dev_cms.Model.Event;
import com.devwellupsolution.dev_cms.R;
import com.devwellupsolution.dev_cms.Utilities.Utility;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateAnnouncementActivity extends AppCompatActivity {
    private static final String TAG = "CreateAnnouncementActivity";
    private String username, password;
    Event event;
    TextView tvStartDate, tvEndDate;
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(findViewById(R.id.btn_StartDate_CreateAnnouncementActivity));
        }
    };
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(findViewById(R.id.btn_EndDate_CreateAnnouncementActivity));
        }
    };
    int btnStartDate = R.id.btn_StartDate_CreateAnnouncementActivity;
    int btnEndDate = R.id.btn_EndDate_CreateAnnouncementActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        tvStartDate = (TextView) findViewById(R.id.tv_StartDate_CreateAnnouncementActivity);
        tvEndDate = (TextView) findViewById(R.id.tv_EndDate_CreateAnnouncementActivity);
    }

    private void updateDateTextView(View view) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        if(view==findViewById(btnStartDate)){
            tvStartDate.setText(simpleDateFormat.format(calendar.getTime()));
        } else if(view==findViewById(btnEndDate)){
            tvEndDate.setText(simpleDateFormat.format(calendar.getTime()));
        } else {
            Log.i(TAG, "updateDateTextView: No such button");
        }

    }

    public void setDate(View view) {
        if (view == findViewById(R.id.btn_StartDate_CreateAnnouncementActivity)) {
            new DatePickerDialog(this, startDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == findViewById(R.id.btn_EndDate_CreateAnnouncementActivity)) {
            new DatePickerDialog(this, endDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    public void onUpload(View view) throws JSONException {
        String title = ((TextView) findViewById(R.id.et_Title_CreateAnnouncementActivity)).getText().toString();
        String message = ((EditText) findViewById(R.id.et_Message_CreateAnnouncementActivity)).getText().toString();
        String startDate = tvStartDate.getText().toString();
        String endDate = tvEndDate.getText().toString();

        if (title.isEmpty() || title.equals(null)) {
            Toast.makeText(this, "Please enter the Title.", Toast.LENGTH_SHORT).show();
        } else if (message.isEmpty() || message.equals(null)) {
            Toast.makeText(this, "Please enter the message.", Toast.LENGTH_SHORT).show();
        } else if(startDate.isEmpty() || startDate.equals(null) || startDate.equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select start date", Toast.LENGTH_SHORT).show();
        } else if(endDate.isEmpty() || endDate.equals(null) || startDate.equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select end date", Toast.LENGTH_SHORT).show();
        } else if(startDate.compareTo(endDate)>0) {
            Toast.makeText(this, "End date cannot be before start date", Toast.LENGTH_SHORT).show();
        } else {

            event = new Event();
            event.setTitle(title);
            event.setMessage(message);
            event.setStartDate(startDate);
            event.setEndDate(endDate);

            Log.i(TAG, "onUpload: title: " + event.getTitle() + " message: " + event.getMessage()
                    + " startDate: " + event.getStartDate() + " endDate: "+event.getEndDate()
                    + " JSON: "+event.getJSON());
            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendCreateAnnouncementData(CreateAnnouncementActivity.this, username, password).execute(event);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            } else {
                Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
