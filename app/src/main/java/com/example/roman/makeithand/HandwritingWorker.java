package com.example.roman.makeithand;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
 * This class extends the AsyncTask to create a custom one.
 * The purpose is to have a class that will launch a thread for a specific task.
 * The task is to communicate with the Handwriting.io API to generate a image containing a Handwritten string.
 * This is the core functionality of the application.
 */
class HandwritingWorker extends AsyncTask<String, String, Writing>
{
    /**
     * Constructor.
     * @param activity
     *          Take a reference from the activity where the task is launched.
     *          Useful for UI update.
     */
    HandwritingWorker(MainActivity activity)
    {
        super();
        this.activity = activity;
    }

    /**
     * Automatically called before the async task start. Called in the UI thread.
     * Set the loading bar to visible.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        activity.loading.setVisibility(View.VISIBLE);
    }

    /**
     * The task itself of the async task.
     * It set the default Authenticator to the credentials generated for us by Handwriting.io
     * Try to resolve the URL of Handwriting.io and connect to it. The parameter are passed with the "?" option in the URL.
     * Use the HttpURL input stream to fill a buffer who will be emptied in a FileOutputStream, opened with the file path destination.
     *
     * @param params
     *          Varags. params[0] is the title, params[1] is the value.
     * @return
     *          Return the new "Writing" object containing the parameter plus the path of the newly created file.
     */
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

    /**
     * Called automatically after the task. Called on the UI thread.
     * Check that the result of the task is not null.
     * If not,
     *      Adding it in database
     *      Refresh the view
     * If null,
     *      Display "something went wrong" in a Toast.
     * Anyway, set the loading bar to gone.
     * @param w
     *      Newly created writing
     */
    @Override
    protected void onPostExecute(Writing w) {
        super.onPostExecute(w);
        if (w != null)
        {
            activity.ctrl.addWriting(w.title, w.value, w.path);
            activity.refreshList();
        }
        else
        {
            Toast.makeText(activity, R.string.oops, Toast.LENGTH_LONG).show();
        }
        activity.loading.setVisibility(View.GONE);
    }

    private MainActivity activity;
}
