package com.example.roman.makeithand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Roman on 20/05/2017.
 */

public class HandwritingWorker extends AsyncTask<String, String, String>
{

    @Override
    protected String doInBackground(String... params) {
        try
        {
            URL url = new URL("");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.addRequestProperty("text", params[0]);
            con.addRequestProperty("handwriting_id", "8X3WQ4D800B0");
            con.connect();
            InputStream input = con.getInputStream();
            Bitmap bmp = BitmapFactory.decodeStream(input);
            return "ok";
        }

        catch (MalformedURLException e)
        { return null; }
        catch (IOException e)
        { return null; }
    }
}
