package com.moviejsonparsingusingparcelable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by aalishan on 2/11/16.
 */
public class ImageDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public ImageDownloadAsyncTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
        return mIcon;
    }

    protected void onPostExecute(Bitmap result) {
        if (result == null) {
        } else {
            bmImage.setImageBitmap(result);

        }

    }
}