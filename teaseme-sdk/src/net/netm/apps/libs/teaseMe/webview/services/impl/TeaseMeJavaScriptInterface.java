package net.netm.apps.libs.teaseMe.webview.services.impl;

import java.util.HashMap;
import java.util.Map;

import net.netm.apps.libs.teaseMe.handlers.ActionHandler;
import net.netm.apps.libs.teaseMe.handlers.HandlerRegistry;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;

/**
 * Created by ahoban on 27.03.15.
 */
public class TeaseMeJavaScriptInterface {

    public static final String JAVASCRIPT_INTERFACE_NAME = "TeaseMe";

    final Context mContext;

    final HandlerRegistry handlerRegistry;

    /**
     * Instantiate the interface and set the context
     */
    public TeaseMeJavaScriptInterface(Context c, HandlerRegistry hR) {
        mContext = c;
        handlerRegistry = hR;
    }


    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void openBrowser(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mContext.startActivity(i);
    }

    /**
     * webview will send handleEvent
     *
     * @param actionType
     * @param actionValue
     */
    @JavascriptInterface
    public void handleEvent(String actionType, String actionValue, String jsonOptions) {

        ActionHandler handler;

        if (jsonOptions == null || StringUtils.isEmpty(jsonOptions)) {

            handleEvent(actionType, actionValue);

        } else {

            Gson gson = new Gson();

            Map<String, String> map = new HashMap<String, String>();

            Map<String, String> options = gson.fromJson(jsonOptions, map.getClass());

            Log.d(this.getClass().getPackage().getName(), "Attempting to find a handler for click on teaser with actionType : " + actionType + " actionValue :" + actionValue);

            handlerRegistry.findHandlerFor(actionType, actionValue).handle(actionType, actionValue, options);
        }

    }

    @JavascriptInterface
    public void handleEvent(String actionType, String actionValue) {
        handlerRegistry.findHandlerFor(actionType, actionValue).handle(actionType, actionValue);

    }
}
