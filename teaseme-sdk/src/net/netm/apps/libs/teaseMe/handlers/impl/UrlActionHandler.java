package net.netm.apps.libs.teaseMe.handlers.impl;

import java.util.Map;

import net.netm.apps.libs.teaseMe.handlers.ActionHandler;
import net.netm.apps.libs.teaseMe.handlers.ActionType;
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
    public boolean canHandle(String actionType) {
        return actionType != null && actionType.equalsIgnoreCase(ActionType.Link.name());
    }

    @Override
    public void handle(String actionType, String actionValue) {

        Intent viewIntent = buildIntent(actionValue);

        try {
            activity.startActivity(viewIntent);
        }
        catch(Exception e) {
            Log.e(this.getClass().getPackage().getName(), "Could not start intent : " + e);
        }

    }

    @Override
    public void handle(String actionType, String actionValue, Map<String, String> options) {

        Intent viewIntent = buildIntent(actionValue);

        for (Map.Entry<String, String> entry : options.entrySet()) {
            viewIntent.putExtra(entry.getKey(), entry.getValue());
        }

        Log.d(activity.getPackageName(), "Starting intent " + viewIntent);

        activity.startActivity(viewIntent);

    }

    private Intent buildIntent(String actionValue) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(actionValue));
    }
}
