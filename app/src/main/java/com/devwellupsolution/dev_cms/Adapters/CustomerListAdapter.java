package com.devwellupsolution.dev_cms.Adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devwellupsolution.dev_cms.Activity.CustomerListActivity;
import com.devwellupsolution.dev_cms.Model.Customer;
import com.devwellupsolution.dev_cms.R;

import java.util.ArrayList;

/**
 * Created by OnesTech on 02/09/2017.
 */

public class CustomerListAdapter extends BaseAdapter implements View.OnClickListener {
    private static LayoutInflater inflater = null;
    private static final String TAG = "CustomerListAdapter";
    private ArrayList<Customer> customerArrayList;
    private Context context;
    private int PERMSISSION_REQUEST_CALL_CONST = 100;

    public CustomerListAdapter(Context context, ArrayList<Customer> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if (customerArrayList.size() <= 0) {
            return 1;
        } else {
            return customerArrayList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.customer_listview, null);
        }
        TextView name = vi.findViewById(R.id.tv_CustomerName_CustomerListView);
        TextView phone = vi.findViewById(R.id.tv_PhoneNumber_CustomerListView);
        ImageButton call = vi.findViewById(R.id.ib_Call_CustomerListView);
        ImageButton message = vi.findViewById(R.id.ib_Message_CustomerListView);
        Customer customer = customerArrayList.get(position);


        name.setText(customer.getName());
        phone.setText(customer.getPhone());
        message.setTag(customer.getPhone());
        call.setTag(customer.getPhone());

        call.setOnClickListener(this);
        message.setOnClickListener(this);

        return vi;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.ib_Call_CustomerListView:
                final String callNumber = (String) view.findViewById(R.id.ib_Call_CustomerListView).getTag();
                Log.i(TAG, "onClick: call: " + callNumber);

                final CharSequence options[] = new CharSequence[] {"CALL", "DIAL/SAVE"};
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose one");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "onClick: selected "+options[i]);
                        if(options[i] == options[0]) {
                            Log.i(TAG, "onClick: call selected");
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.fromParts("tel",callNumber,null));
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                                    != PackageManager.PERMISSION_GRANTED) {
                                Log.i(TAG, "onClick: Permissions are not yet granted ");

                                if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) view.getContext(),
                                        Manifest.permission.CALL_PHONE)) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    builder.setTitle("Need Call Permissions");
                                    builder.setMessage("This app needs call permissions to call Customers. Please grant the permissions");
                                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            ActivityCompat.requestPermissions((Activity) view.getContext(),
                                                    new String[] {Manifest.permission.CALL_PHONE}, PERMSISSION_REQUEST_CALL_CONST);
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();

                                } else {

                                    ActivityCompat.requestPermissions((Activity) view.getContext(),
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            PERMSISSION_REQUEST_CALL_CONST);
                                }

                                return;
                            } else {
                                Log.i(TAG, "onClick: Call intent started");
                                view.getContext().startActivity(callIntent);
                            }
                        } else {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.fromParts("tel",callNumber,null));
                            view.getContext().startActivity(intent);
                        }
                    }
                });
                builder.show();


                break;

            case R.id.ib_Message_CustomerListView:
                String msgNumber = (String) view.findViewById(R.id.ib_Message_CustomerListView).getTag();
                Log.i(TAG, "onClick: message: " + msgNumber);
                Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + msgNumber));
                context.startActivity(smsIntent);
                break;

            default:
                break;
        }
    }

    public void callCustomer() {
    }

}
