package net.netm.apps.libs.teaseme.webview.services.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.netm.apps.libs.teaseme.handlers.ActionHandler;
import net.netm.apps.libs.teaseme.handlers.HandlerRegistry;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahoban on 27.03.15.
 */
public class TeaseMeJavaScriptInterface {

    public static final String JAVASCRIPT_INTERFACE_NAME = "TeaseMe";

    final Context mContext;

    final HandlerRegistry handlerRegistry;

    private ActionHandler actionHandler;

    /**
     * Instantiate the interface and set the context
     */
    public TeaseMeJavaScriptInterface(Context c, HandlerRegistry hR, ActionHandler aH) {
        mContext = c;
        handlerRegistry = hR;
        actionHandler = aH;
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
    public void sendEvent(String actionType, String actionValue, String jsonOptions) {

        ActionHandler handler;

        Map<String, String> options = new HashMap<String, String>();

        Gson gson = new Gson();

        Map<String, String> map = new HashMap<String, String>();

        Type mapType = new TypeToken<HashMap<String, String>>() {
        }.getType();

        try {

            options = gson.fromJson(jsonOptions, mapType);
        } catch (Exception e) {

        }

        Log.d(this.getClass().getPackage().getName(), "Attempting to find a handler for click on teaser with actionType : " + actionType + " actionValue :" + actionValue + options.toString());


        handlerRegistry.trackClick(actionType, actionValue, options);

        if (actionHandler != null) {
            if (actionHandler.canHandle(actionType, actionValue, options)) {

                if (actionHandler.handle(actionType, actionValue, options)) {
                    return;
                }
            }
        }
        handlerRegistry.findHandlerFor(actionType, actionValue, options).handle(actionType, actionValue, options);


    }

    @JavascriptInterface
    public void sendEvent(String actionType, String actionValue) {
        Log.e("SOMETHING ekse is", actionType);

        this.sendEvent(actionType,actionValue, "{}");
    }

    @JavascriptInterface
    public void sendEvent(String something) {
        Log.e("SOMETHING is", something);
        Toast.makeText(mContext, "SOMETHIIIING " + something, Toast.LENGTH_SHORT);
    }
}
