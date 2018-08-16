package com.photoshowapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by Nefast on 12/04/2018.
 */

public class AsyncRestClient extends AsyncTask <ContentValues, Void, JSONObject> {
    protected String error;
    protected Context context;
    protected ProgressDialog progressDialog;

    private OnReceiveDataListener onReceiveDataListener;



    public void setOnReceiveDataListener(OnReceiveDataListener onReceiveDataListener) {
        this.onReceiveDataListener = onReceiveDataListener;
    }

    private void onReceiveData(JSONObject jsonObject){
        if(onReceiveDataListener != null){
            onReceiveDataListener.onReceiveData(jsonObject);
        }
    }

    public AsyncRestClient(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(R.string.btnload_title);
        progressDialog.setMessage(context.getText(R.string.btnload_title));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        error = new String();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //progressDialog.show();
    }

    @Override
    protected JSONObject doInBackground(ContentValues... contentValues) {

        JSONObject result    = new JSONObject();
        ContentValues params = contentValues[0];
        String query         = new String();
        boolean first        = true;
        for(String key:params.keySet()){
            if(!key.contains("HTTP_")){
                if(first){
                first = false;
            } else {
                query += "&";
            }
                try {
                    query += URLEncoder.encode(key,"UTF-8") + "=" + URLEncoder.encode(params.getAsString(key),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            URL url = new URL(params.getAsString("HTTP_URL"));

            if(params.getAsString("HTTP_METHOD").equals("GET") && !query.isEmpty()||
                    params.getAsString("HTTP_METHOD").equals("DELETE") && !query.isEmpty()
                    ){
                url = new URL(params.getAsString("HTTP_URL") + "?" + query);
            }

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(params.getAsString("HTTP_METHOD"));
            httpURLConnection.setRequestProperty("Accept",params.getAsString("HTTP_ACCEPT"));
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setUseCaches(true);

            if(params.getAsString("HTTP_METHOD").equals("PUT")||
                    params.getAsString("HTTP_METHOD").equals("POST") ||
                    params.getAsString("HTTP_METHOD").equals("PATCH")&&
                    !query.isEmpty()){

                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                DataOutputStream data = new DataOutputStream(httpURLConnection.getOutputStream());
                data.writeBytes(query);
                data.flush();
                data.close();
            }

            Log.e(">>>>>>>>",url.toString());

            InputStream inputStream = httpURLConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            String response = scanner.useDelimiter("\\A").next();

            scanner.close();
            inputStream.close();

            result = new JSONObject(response);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            error = context.getString(R.string.error_url);
        } catch (IOException e) {
            e.printStackTrace();
            error = context.getString(R.string.error_io);
        } catch (JSONException e) {
            e.printStackTrace();
            error = context.getString(R.string.error_json);
        }
        Log.e(">>>>>>>>",result.toString());
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        //progressDialog.dismiss();
        onReceiveData(jsonObject);
        if (!error.isEmpty()){
            Toast.makeText(context,error,Toast.LENGTH_LONG).show();
        }
    }
}
