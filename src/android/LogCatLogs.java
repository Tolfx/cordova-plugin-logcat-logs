package org.apache.cordova.logcat;

import java.io.File;
import java.io.IOException;
import org.apache.cordova.CallbackContext;
import android.content.Context;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.os.Environment;
import android.app.Activity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import android.util.Log;

public class LogCatLogs extends CordovaPlugin
{

    private static final String LOG_TAG = "LogCatLogs";
    private static final String LOG_FILE_NAME = "logcat.txt";
    private Activity activity = null;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView)
    {
    	super.initialize(cordova, webView);
        activity = cordova.getActivity();
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
    {
        if (action.equals("getLogs"))
        {
            File logcatFile = createLogFile();

            if (logcatFile != null)
            {
                String logcat = getContent(logcatFile);
                callbackContext.success(logcat);
                return true;
            }

            callbackContext.error("Error creating logcat file");
            Log.e(LOG_TAG, "Error creating logcat file");

            return false;
        }

        return false;
    }

    private String getContent(File file)
    {
        String content = "";
        content = this.readFileToString(file.getAbsolutePath());
        return content;
    }

    private File createLogFile()
    {
        Context context = activity.getApplicationContext();
        String b = context.getFilesDir().getAbsolutePath();
        File outputFile = new File(b+"/"+LOG_FILE_NAME);
        try {
            Runtime.getRuntime().exec("logcat -f " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    public String readFileToString(String pathToFile)
    {
        byte[] buffer = readFileToByteArray(pathToFile);

        if (buffer != null)
            return new String(buffer);

        return null;
    }

    public byte[] readFileToByteArray(String pathToFile)
    {
        byte[] result = null;

        try
        {
            File file = new File(pathToFile);

            if (file.length() <= Integer.MAX_VALUE)
            {
                result = new byte[(int) file.length()];
                @SuppressWarnings("resource")
                RandomAccessFile raf = new RandomAccessFile(file, "r");

                raf.readFully(result);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}