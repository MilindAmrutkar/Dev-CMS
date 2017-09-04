package com.devwellupsolution.dev_cms.Activity;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.devwellupsolution.dev_cms.Adapters.CustomerListAdapter;
import com.devwellupsolution.dev_cms.Asynctask.FetchCustomerDetails;
import com.devwellupsolution.dev_cms.Model.Customer;
import com.devwellupsolution.dev_cms.R;
import com.devwellupsolution.dev_cms.Utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class CustomerListActivity extends AppCompatActivity implements FetchCustomerDetails.CustomerDetailsInterface {
    private static String TAG = "CustomerListActivity";
    private ListView listView;
    CustomerListAdapter customerListAdapter;
    ImageButton ibCall, ibMessage;
    Customer customer = null;
    EditText etSearch;
    ArrayList<Customer> customerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        etSearch = (EditText) findViewById(R.id.et_Search_CustomerListActivity);
        listView = (ListView) findViewById(R.id.lv_CustomerList_CustomerListActivity);

        customerArrayList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                customer = customerArrayList.get(position);
                Log.i(TAG, "onItemClick: phone Number:  "+((TextView) findViewById(R.id.tv_PhoneNumber_CustomerListView)).getText().toString());
                if(Utility.isNetworkAvailable(getApplication())) {
                    Intent intent = new Intent(getApplicationContext(), CustomerListDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("customer", customer);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplication(), "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onSearch(View view) {
        String username = Utility.getPreferences(this, "username");
        String password = Utility.getPreferences(this, "password");

        if(Utility.isNetworkAvailable(this)) {
            Utility.hideKeyboard(view, this);
            String searchValue = etSearch.getText().toString();
            if(searchValue.equals(null) || searchValue.isEmpty()) {
                Toast.makeText(this, "Please enter some value to search for", Toast.LENGTH_SHORT).show();
            } else {
                FetchCustomerDetails asyncTask = new FetchCustomerDetails(this, username, password);
                asyncTask.customerDetailsInterface =this;
                asyncTask.execute(searchValue);
            }
        } else {
            Toast.makeText(this, "Network issue. Please switch on the internet.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void parseCustomerDetailsJSON(String myJSON) {
        customerArrayList.clear();
        try {
            Log.i(TAG, "parseCustomerDetailsJSON: myJSON: "+myJSON);
            if(myJSON != null && !myJSON.isEmpty()) {
                JSONObject jsonRoot =new JSONObject(myJSON);
                if(!jsonRoot.getBoolean("status")) {
                    JSONArray jsonArray = jsonRoot.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        customer = new Customer();
                        String name = jsonObject.get("name").toString();
                        Log.i(TAG, "parseCustomerDetailsJSON: Customer name: "+name);
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

                        customerArrayList.add(customer);

                    }
                    Log.i(TAG, "parseStockJSON: message: "+jsonRoot.get("message"));
                } else {
                    Toast.makeText(this, jsonRoot.getString("message"), Toast.LENGTH_SHORT).show();
                }

                if(customerArrayList != null && customerArrayList.size() > 0) {
                    customerListAdapter = new CustomerListAdapter(this, customerArrayList);
                    listView.setAdapter(customerListAdapter);
                } else {
                    listView.setAdapter(null);
                    Log.i(TAG, "parseCustomerDetailsJSON: No such record found");
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

    }
}
