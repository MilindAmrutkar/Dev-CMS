package com.devwellupsolution.dev_cms.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.devwellupsolution.dev_cms.R;
import com.devwellupsolution.dev_cms.Utilities.Utility;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void openCreateAnnouncement(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, CreateAnnouncementActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void openAddCustomer(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, AddCustomerActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void openCustomerList(View view) {
        if (Utility.isNetworkAvailable(this)) {
            Intent intent = new Intent(this, CustomerListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Network issue. Please switch on your internet", Toast.LENGTH_SHORT).show();
        }
    }
}
