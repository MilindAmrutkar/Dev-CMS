package com.devwellupsolution.dev_cms.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.devwellupsolution.dev_cms.Asynctask.SendCustomerData;
import com.devwellupsolution.dev_cms.Model.Customer;
import com.devwellupsolution.dev_cms.R;
import com.devwellupsolution.dev_cms.Utilities.Utility;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EnterCustomerDetailsActivity extends AppCompatActivity {
    private static final String TAG = "EnterCustomerDetailsActivity";
    LinearLayout llAnniversary;
    String intentMobileNumber, intentEmail;
    Customer customer;
    String username, password;
    String isMarried;

    TextView tvPhoneNumber, tvJoiningDate, tvDOB, tvAnniversaryDate;
    Button btnBirthDate, btnAnniversaryDate, btnJoinDate;
    EditText etName, etEmail, etAddress, etCity, etState, etPincode, etPAN, etAadhaar, etLicense,
    etRemarks, etReligion;
    CheckBox cbIsMarried;

    Calendar calendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener joinDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(btnJoinDate);
        }
    };
    DatePickerDialog.OnDateSetListener birthDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(btnBirthDate);
        }
    };
    DatePickerDialog.OnDateSetListener anniversaryDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateDateTextView(btnAnniversaryDate);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_customer_details);
        customer = new Customer();
        customer = getIntent().getExtras().getParcelable("customer");
        intentMobileNumber = getIntent().getStringExtra("phone");
        intentEmail = getIntent().getStringExtra("email");
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
        getEachViewsIds();
        setValues();

    }

    private void getEachViewsIds() {
        tvPhoneNumber = (TextView) findViewById(R.id.tv_PhoneNumber_EnterCustomerDetailsActivity);
        tvJoiningDate = (TextView) findViewById(R.id.tv_JoiningDate_EnterCustomerDetailsActivity);
        tvDOB = (TextView) findViewById(R.id.tv_DOB_EnterCustomerDetailsActivity);
        tvAnniversaryDate = (TextView) findViewById(R.id.tv_AnniversaryDate_EnterCustomerDetailsActivity);
        etName = (EditText) findViewById(R.id.et_Name_EnterCustomerDetailsActivity);
        etAddress = (EditText) findViewById(R.id.et_Address_EnterCustomerDetailsActivity);
        etState = (EditText) findViewById(R.id.et_State_EnterCustomerDetailsActivity);
        etEmail = (EditText) findViewById(R.id.et_EmailAddress_EnterCustomerDetailsActivity);
        etAadhaar = (EditText) findViewById(R.id.et_AADHAAR_EnterCustomerDetailsActivity);
        etPincode = (EditText) findViewById(R.id.et_Pincode_EnterCustomerDetailsActivity);
        etPAN = (EditText) findViewById(R.id.et_PAN_EnterCustomerDetailsActivity);
        etLicense = (EditText) findViewById(R.id.et_License_EnterCustomerDetailsActivity);
        etRemarks = (EditText) findViewById(R.id.et_Remarks_EnterCustomerDetailsActivity);
        etReligion = (EditText) findViewById(R.id.et_Religion_EnterCustomerDetailsActivity);
        etCity = (EditText) findViewById(R.id.et_City_EnterCustomerDetailsActivity);
        cbIsMarried = (CheckBox) findViewById(R.id.cb_IsMarried_EnterCustomerDetailsActivity);
        btnBirthDate= (Button) findViewById(R.id.btn_DOB_EnterCustomerDetailsActivity);
        btnAnniversaryDate = (Button)findViewById(R.id.btn_Anniversary_EnterCustomerDetailsActivity);
        btnJoinDate = (Button) findViewById(R.id.btn_JoinDate_EnterCustomerDetailsActivity);
        llAnniversary = (LinearLayout) findViewById(R.id.ll_Anniversary_EnterCustomerDetailsActivity);
        cbIsMarried.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(cbIsMarried.isChecked()) {
                    llAnniversary.setVisibility(View.VISIBLE);
                    isMarried = "YES";
                } else {
                    llAnniversary.setVisibility(View.GONE);
                    isMarried = "NA";
                }
            }
        });
    }

    private void updateDateTextView(View view) {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.getDefault());
        if(view==btnJoinDate){
            tvJoiningDate.setText(simpleDateFormat.format(calendar.getTime()));
        } else if(view==btnAnniversaryDate){
            tvAnniversaryDate.setText(simpleDateFormat.format(calendar.getTime()));
        } else if(view==btnBirthDate){
            tvDOB.setText(simpleDateFormat.format(calendar.getTime()));
        } else {
            Log.i(TAG, "updateDateTextView: No such button");
        }

    }

    public void setDate(View view) {
        if (view == btnAnniversaryDate) {
            new DatePickerDialog(this, anniversaryDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == btnBirthDate) {
            new DatePickerDialog(this, birthDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (view == btnJoinDate) {
            new DatePickerDialog(this, joinDate, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private void setValues() {
        if(!intentEmail.isEmpty() || !intentEmail.equalsIgnoreCase(null)) {
            etEmail.setText(intentEmail);
        }
        if(!intentMobileNumber.isEmpty() || !intentMobileNumber.equalsIgnoreCase(null)) {
            tvPhoneNumber.setText(intentMobileNumber);
        }

        if(customer != null) {
            tvDOB.setText(customer.getDob());
            tvPhoneNumber.setText(customer.getPhone());
            tvJoiningDate.setText(customer.getJoinDate());
            tvAnniversaryDate.setText(customer.getAnniversaryDate());
            etName.setText(customer.getName());
            etCity.setText(customer.getCity());
            etEmail.setText(customer.getEmail());
            etAddress.setText(customer.getAddress());
            etState.setText(customer.getState());
            etPincode.setText(customer.getPincode());
            etPAN.setText(customer.getPancard());
            etAadhaar.setText(customer.getAadhaar());
            etLicense.setText(customer.getLicense());
            etRemarks.setText(customer.getRemark());
            etReligion.setText(customer.getReligion());
            if(customer.getMarriageStatus().equalsIgnoreCase("YES")) {
                cbIsMarried.setChecked(true);
                llAnniversary.setVisibility(View.VISIBLE);
                tvAnniversaryDate.setText(customer.getAnniversaryDate());
            }

        } else {
            Log.i(TAG, "setValues: New Customer");
        }
    }

    public void onUpload(View view) throws JSONException {
        String birthDate = tvDOB.getText().toString();
        String phoneNumber = tvPhoneNumber.getText().toString();
        String joiningDate = tvJoiningDate.getText().toString();
        String anniversaryDate = tvAnniversaryDate.getText().toString();
        String name = etName.getText().toString();
        String city = etCity.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        String state =  etState.getText().toString();
        String pincode = etPincode.getText().toString();
        String pan = etPAN.getText().toString();
        String aadhaar = etAadhaar.getText().toString();
        String license = etLicense.getText().toString();
        String remarks = etRemarks.getText().toString();
        String religion = etReligion.getText().toString();
        String marriageStatus = isMarried;

        if (phoneNumber.isEmpty() || phoneNumber.equals(null)) {
            Toast.makeText(this, "Please enter the Phone Number.", Toast.LENGTH_SHORT).show();
        } else if(joiningDate.isEmpty() || joiningDate.equals(null) || joiningDate.equalsIgnoreCase("date")) {
            Toast.makeText(this, "Please select joining date", Toast.LENGTH_SHORT).show();
        } else if (name.isEmpty() || name.equals(null)) {
            Toast.makeText(this, "Please enter the Name.", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty() || email.equals(null)) {
            Toast.makeText(this, "Please enter the Email.", Toast.LENGTH_SHORT).show();
        } else if (address.isEmpty() || address.equals(null)) {
            Toast.makeText(this, "Please enter the Address.", Toast.LENGTH_SHORT).show();
        } else if (city.isEmpty() || city.equals(null)) {
            Toast.makeText(this, "Please enter the City.", Toast.LENGTH_SHORT).show();
        } else if (state.isEmpty() || state.equals(null)) {
            Toast.makeText(this, "Please enter the state.", Toast.LENGTH_SHORT).show();
        } else if (pincode.isEmpty() || pincode.equals(null)) {
            Toast.makeText(this, "Please enter the pincode.", Toast.LENGTH_SHORT).show();
        } else {

            customer = new Customer();
            customer.setPhone(phoneNumber);
            customer.setJoinDate(joiningDate);
            customer.setName(name);
            customer.setEmail(email);
            customer.setDob(birthDate);
            customer.setMarriageStatus(marriageStatus);
            customer.setAnniversaryDate(anniversaryDate);
            customer.setAddress(address);
            customer.setCity(city);
            customer.setState(state);
            customer.setPincode(pincode);
            customer.setPancard(pan);
            customer.setAadhaar(aadhaar);
            customer.setLicense(license);
            customer.setReligion(religion);
            customer.setRemark(remarks);

            Log.i(TAG, "onUpload: name: " + customer.getName() + " anniversary date: " + customer.getAnniversaryDate()
                    + " City: " + customer.getCity() + " marriageStatus: "+customer.getMarriageStatus()
                    + " JSON: "+customer.getJSON());
            if (Utility.isNetworkAvailable(this)) {
                new AlertDialog.Builder(this)
                        .setTitle("Are you sure?")
                        .setMessage("All the details will be uploaded")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SendCustomerData(EnterCustomerDetailsActivity.this, username, password).execute(customer);

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
