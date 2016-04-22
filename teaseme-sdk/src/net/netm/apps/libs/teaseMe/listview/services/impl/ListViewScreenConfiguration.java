package net.netm.apps.libs.teaseme.listview.services.impl;

import android.app.Activity;
import android.view.View;

import net.netm.apps.libs.teaseme.utils.BasicScreenConfiguration;

import java.util.Map;

/**
 * Created by ahoban on 29.03.15.
 */
public class ListViewScreenConfiguration extends BasicScreenConfiguration {

    private String userAgent;

    /**
     * {@inheritDoc}

     * @param context
     * @param screenId
     * @param view
     */
    public ListViewScreenConfiguration(Activity context, Long screenId, View view) {
        super(context, screenId, view);
    }

    /**
     *
     * @param context the context
     * @param screenId the screend
     * @param view  the view to be bound
     * @param params set TeaserFilterParams's
     */
    public ListViewScreenConfiguration(Activity context, Long screenId, View view, Map<String,String> params) {
        super(context, screenId,view, params);
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }



}
