package net.netm.apps.libs.teaseme.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by ahoban on 27.03.15.
 */
public class Utils {

    public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    urlEncodeUTF8(entry.getKey().toString()),
                    urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
    public static void startTask(AsyncTask asyncTask, String... params) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[]) params);
        else
            asyncTask.execute(params);
    }

    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public static StringBuilder responseToStringBuilder(HttpResponse response) throws IOException {

        HttpEntity entity = response.getEntity();

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));

        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;

        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        return responseStrBuilder;
    }

    public Activity contextToActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof Service) {
            throw new IllegalStateException("You passed a service instead of an ApplicationContext or Activity");
        }

        return null;
    }
}
