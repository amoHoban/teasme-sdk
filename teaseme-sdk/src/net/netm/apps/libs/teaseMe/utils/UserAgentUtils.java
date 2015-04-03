package net.netm.apps.libs.teaseme.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Constructor;

/**
 * Created by ahoban on 26.03.15.
 */
public class UserAgentUtils {

    // You may uncomment next line if using Android Annotations library, otherwise just be sure to run it in on the UI thread
    public static String getDefaultUserAgentString(Context context) {
        if (Build.VERSION.SDK_INT >= 17) {
            return NewApiWrapper.getDefaultUserAgent(context);
        }

        try {
            Constructor<WebSettings> constructor = WebSettings.class.getDeclaredConstructor(Context.class, WebView.class);
            constructor.setAccessible(true);
            try {
                WebSettings settings = constructor.newInstance(context, null);
                return settings.getUserAgentString();
            } finally {
                constructor.setAccessible(false);
            }
        } catch (Exception e) {
            return new WebView(context).getSettings().getUserAgentString();
        }
    }

    @TargetApi(17)
    static class NewApiWrapper {
        static String getDefaultUserAgent(Context context) {
            return WebSettings.getDefaultUserAgent(context);
        }
    }
}
