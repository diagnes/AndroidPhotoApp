package com.photoshowapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Nefast on 15/04/2018.
 */

public class AsyncPicturesCreator extends AsyncTask<Object, Object, Bitmap> {

    private ImageView image;
    private String stringurl;
    private Bitmap bitmap;
    private ProgressDialog progressBar;
    private Context cx;

    public AsyncPicturesCreator(String strings, ImageView img){
        image = img;
        stringurl = strings;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Object... strings) {

        URL url = null;
        try {
            url = new URL(stringurl);
            HttpURLConnection cx = (HttpURLConnection) url.openConnection();
            InputStream is = cx.getInputStream();
            final Bitmap bm = BitmapFactory.decodeStream(is);
            bitmap = bm;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap imageView) {
        super.onPostExecute(imageView);
        image.setImageBitmap(bitmap);
    }

    protected ImageView handlevalue(ImageView image){
        return image;
    }
}

