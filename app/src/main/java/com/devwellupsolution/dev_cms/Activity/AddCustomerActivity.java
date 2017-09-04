package com.devwellupsolution.dev_cms.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.devwellupsolution.dev_cms.Asynctask.FetchCustomerDetails;
import com.devwellupsolution.dev_cms.Model.Customer;
import com.devwellupsolution.dev_cms.R;
import com.devwellupsolution.dev_cms.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCustomerActivity extends AppCompatActivity implements FetchCustomerDetails.CustomerDetailsInterface {
    private static final String TAG = "AddCustomerActivity";
    EditText etMobileNumber, etEmailAddress;
    String username, password;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        etMobileNumber = (EditText) findViewById(R.id.et_MobileNumber_AddCustomerActivity);
        etEmailAddress = (EditText) findViewById(R.id.et_EmailAddress_AddCustomerActivity);
        username = Utility.getPreferences(this, "username");
        password = Utility.getPreferences(this, "password");
    }

    public void openEnterCustomerDetails(View view) {
        String mobileNumber = etMobileNumber.getText().toString();
        String emailAddress = etEmailAddress.getText().toString();

        if(mobileNumber.isEmpty()&&emailAddress.isEmpty()) {
            Toast.makeText(this, "Please provide value for either Mobile number or Email Address", Toast.LENGTH_SHORT).show();
        } else {
            if (Utility.isNetworkAvailable(this)) {

                FetchCustomerDetails asyncTask = new FetchCustomerDetails(this, username, password);
                asyncTask.customerDetailsInterface = this;
                if(!mobileNumber.isEmpty()) {
                    asyncTask.execute(mobileNumber);
                } else {
                    asyncTask.execute(emailAddress);
                }
            } else {
                Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseCustomerDetailsJSON(String myJSON) {
        try {
            Log.i(TAG, "parseStockJSON: myJSON: "+myJSON);
            if(myJSON !=null && !myJSON.isEmpty()) {
                JSONObject jsonRoot = new JSONObject(myJSON);
                if(!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        customer = new Customer();
                        String name = jsonObject.get("name").toString();
                        Log.i(TAG, "parseStockJSON: Customer name: "+name);
                        customer.setName(jsonObject.get("name").toString());
                        customer.setAddress(jsonObject.get("address").toString());
                        customer.setPhone(jsonObject.get("phone").toString());
                        customer.setAadhaar(jsonObject.get("aadhaar").toString());
                        customer.setPancard(jsonObject.get("pancard").toString());
                        customer.setLicense(jsonObject.get("license").toString());
                        customer.setEmail(jsonObject.get("email").toString());
                        customer.setJoinDate(jsonObject.get("join_date").toString());
                        customer.setDob(jsonObject.get("dob").toString());
                        customer.setCity(jsonObject.get("city").toString());
                        customer.setState(jsonObject.get("state").toString());
                        customer.setPincode(jsonObject.get("pincode").toString());
                        customer.setMarriageStatus(jsonObject.get("marriage_status").toString());
                        customer.setAnniversaryDate(jsonObject.get("anniversary_date").toString());
                        customer.setReligion(jsonObject.get("religion").toString());
                        customer.setRemark(jsonObject.get("remark").toString());

                        Log.i(TAG, "parseStockJSON: customer.getCity(): "+customer.getCity());
                        //stockReportArrayList.add(stockReport);
                    }
                    Log.i(TAG, "parseStockJSON: message: "+jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "JSON response is empty", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void customerDetailsProcessFinish(String output) {
        parseCustomerDetailsJSON(output);
        Intent intent = new Intent(this, EnterCustomerDetailsActivity.class);
        intent.putExtra("customer",customer);
        intent.putExtra("email", etEmailAddress.getText().toString());
        intent.putExtra("phone", etMobileNumber.getText().toString());
        startActivity(intent);
    }


}
