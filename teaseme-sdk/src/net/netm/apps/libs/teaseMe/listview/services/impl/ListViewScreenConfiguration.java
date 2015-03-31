package net.netm.apps.libs.teaseMe.listview.services.impl;

import android.app.Activity;
import android.view.View;

import net.netm.apps.libs.teaseMe.utils.BasicScreenConfiguration;

/**
 * Created by ahoban on 29.03.15.
 */
public class ListViewScreenConfiguration extends BasicScreenConfiguration {

    private String userAgent;

    public ListViewScreenConfiguration(Activity context, Long screenId, View view) {
        super(context, screenId,view);
    }

    public ListViewScreenConfiguration(Activity context, Long screenId, View view, String userAgent) {
        super(context, screenId,view);
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }



}
