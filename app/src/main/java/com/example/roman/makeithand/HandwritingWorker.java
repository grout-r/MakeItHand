package com.example.roman.makeithand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * Created by Roman on 20/05/2017.
 */

public class HandwritingWorker extends AsyncTask<String, String, String>
{
    HandwritingWorker(MainActivity activity)
    {
        super();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try
        {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("W0KW5SP449NP5CC0", "0WCAGJMVK1MKY1RE".toCharArray());
                }
            });

            Log.d("ICI", "Ici");
            URL url = new URL("https://api.handwriting.io/render/png?handwriting_id=8X3WQ4D800B0&text="+ URLEncoder.encode(params[1], "UTF-8"));
            File file = new File(activity.getExternalFilesDir(null) + "/" +  params[0] + "_" + Calendar.getInstance().get(Calendar.SECOND) + ".png");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            con.connect();
            Log.d("response code", String.valueOf(con.getResponseCode()));
            Log.d("response message", String.valueOf(con.getResponseMessage()));

            Log.d("path",file.getAbsolutePath());

            InputStream is = con.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buffer = new byte[4 * 1024]; // or other buffer size
            int read;

            while ((read = bis.read(buffer)) != -1)
            {
                fos.write(buffer, 0, read);
            }

            is.close();
            fos.close();


            return "ok";
        }

        catch (MalformedURLException e)
        {
            Log.d("MalformedURLException !", ""+e);
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            Log.d("IOException !", ""+e);
            e.printStackTrace();
            return null;
        }
    }

    MainActivity activity;
}
