package com.devwellupsolution.dev_cms.Asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.devwellupsolution.dev_cms.Utilities.SetURL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by OnesTech on 24/08/2017.
 */

public class FetchCustomerDetails extends AsyncTask<String, String, String> {
    private static String TAG = "FetchCustomerDetails";
    public FetchCustomerDetails.CustomerDetailsInterface customerDetailsInterface = null;
    Context context;
    String message;
    private ProgressDialog progressDialog;

    String username, password;



    public FetchCustomerDetails(Context context,  String username, String password) {
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("Fetching some information...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {

        BufferedReader reader = null;
        HttpURLConnection httpURLConnection = null;
        String searchValue = params[0];
        Log.i(TAG, "doInBackground: searchValue: "+searchValue);

        String data = "";
        try {

            String link = SetURL.FetchCustomerDetails;
            URL url = new URL(link);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream oStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(oStream, "UTF-8"));

            data = "";
            data += URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("search_value", "UTF-8")
                    + "=" + URLEncoder.encode(searchValue, "UTF-8");
            Log.i(TAG, "doInBackground: username: " + username + " password: " + password+ " search value: "+searchValue);

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            oStream.close();
            int responseCode = httpURLConnection.getResponseCode();
            Log.i(TAG, "doInBackground: responseCode: "+responseCode);
            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
                //sb.append(line);
            }
            Log.i(TAG, "doInBackground: sb: " + sb);

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        customerDetailsInterface.customerDetailsProcessFinish(s);
        Log.i(TAG, "onPostExecute: JSON: " + s);
    }

    public interface CustomerDetailsInterface {
        void customerDetailsProcessFinish(String output);
    }
}