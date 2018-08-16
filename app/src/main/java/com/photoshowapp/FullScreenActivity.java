package com.photoshowapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Nefast on 12/04/2018.
 */

public class FullScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_activity);

        Intent i = getIntent();
        String WebUrl = i.getExtras().getString("url");
        ImageView imageView = findViewById(R.id.fullscreenimg);
        AsyncPicturesCreator async = new AsyncPicturesCreator(WebUrl,imageView);
        async.execute();
    }
}
