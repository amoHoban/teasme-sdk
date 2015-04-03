package net.netm.apps.libs.teaseme.handlers.impl;

import java.util.Map;

import net.netm.apps.libs.teaseme.handlers.ActionHandler;
import net.netm.apps.libs.teaseme.handlers.ActionType;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by ahoban on 27.03.15.
 */
public class UrlActionHandler implements ActionHandler {

    final Activity activity;

    public UrlActionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean canHandle(String actionType, String actionValue, Map<String, String> options) {
        return actionType != null && actionType.equalsIgnoreCase(ActionType.Link.name());
    }


    @Override
    public boolean handle(String actionType, String actionValue, Map<String, String> options) {

        Intent viewIntent = buildIntent(actionValue);

        for (Map.Entry<String, String> entry : options.entrySet()) {
            viewIntent.putExtra(entry.getKey(), entry.getValue());
        }

        Log.d(activity.getPackageName(), "Starting intent " + viewIntent);

        activity.startActivity(viewIntent);

        return true;

    }

    private Intent buildIntent(String actionValue) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(actionValue));
    }
}
