package net.netm.apps.libs.teaseme.utils;

import android.app.Activity;
import android.view.View;

import java.util.Map;

/**
 * Created by ahoban on 29.03.15.
 */
public class BasicScreenConfiguration {

    private final Activity context;

    private final Long screenId;

    private final View view;

    private Map<String, String> params;


    public BasicScreenConfiguration(Activity context, long screenId, View view) {
        this.context = context;
        this.screenId = screenId;
        this.view = view;

    }

    public BasicScreenConfiguration(Activity context, long screenId, View view, Map<String, String> params) {
        this(context, screenId, view);
        this.params = params;
    }


    public Long getScreenId() {
        return screenId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public View getView() {
        return view;
    }

    public Activity getContext() {
        return context;
    }
}
