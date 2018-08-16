package com.photoshowapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Nefast on 15/04/2018.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> picturesURL;
    private ProgressDialog progressBar;

    public ImageAdapter(Context c, ArrayList<String> pic){
        mContext = c;
        picturesURL = pic;
        progressBar = new ProgressDialog(mContext);
        progressBar.setTitle(R.string.btnload_title);
        progressBar.setMessage(mContext.getText(R.string.btnload_title));
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
    }

    @Override
    public int getCount() {
        return picturesURL.size();
    }

    @Override
    public Object getItem(int pos) {
        return picturesURL.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        progressBar.show();
        ImageView img = new ImageView(mContext);
        String url = picturesURL.get(position);
        AsyncPicturesCreator async = new AsyncPicturesCreator(url,img);
        async.execute();
        img.setMaxHeight(290);
        img.setMaxWidth(250);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setLayoutParams(new GridView.LayoutParams(250, 290));
        img.setPadding(1, 1, 1, 1);
        progressBar.dismiss();
        return img;
    }
}
