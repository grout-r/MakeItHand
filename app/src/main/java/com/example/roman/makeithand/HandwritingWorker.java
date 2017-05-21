package com.example.roman.makeithand;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class HandwritingWorker extends AsyncTask<String, String, Writing>
{
    HandwritingWorker(MainActivity activity)
    {
        super();
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.loading.setVisibility(View.VISIBLE);
    }

    @Override
    protected Writing doInBackground(String... params) {
        try
        {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("W0KW5SP449NP5CC0", "0WCAGJMVK1MKY1RE".toCharArray());
                }
            });

            URL url = new URL("https://api.handwriting.io/render/png?handwriting_id=8X3WQ4D800B0&text="+ URLEncoder.encode(params[1], "UTF-8"));
            File file = new File(activity.getExternalFilesDir(null) + "/" +  params[0] + "_" + Calendar.getInstance().get(Calendar.SECOND) + ".png");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            InputStream is = con.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int read;

            while ((read = bis.read(buffer)) != -1)
            {
                fos.write(buffer, 0, read);
            }

            is.close();
            fos.close();

            return new Writing (-1, params[0], params[1], file.getAbsolutePath());
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

    @Override
    protected void onPostExecute(Writing w) {
        super.onPostExecute(w);
        if (w != null)
        {
            activity.ctrl.addWriting(w.title, w.value, w.path);
            activity.refeshList();
        }
        else
        {
            Toast.makeText(activity, "Something went wrong ...", Toast.LENGTH_LONG).show();
        }
        activity.loading.setVisibility(View.GONE);
    }

    private MainActivity activity;
}
