package net.netm.apps.libs.teaseme.handlers.impl;

import android.app.Activity;

import com.google.gson.JsonObject;

import net.netm.apps.libs.teaseme.TeaseMe;
import net.netm.apps.libs.teaseme.handlers.ActionHandler;
import net.netm.apps.libs.teaseme.handlers.ActionType;
import net.netm.apps.libs.teaseme.handlers.HandlerRegistry;
import net.netm.apps.libs.teaseme.utils.BasicAsynkTask;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by ahoban on 27.03.15.
 */
public class TeaserActionHandlerRegistry implements HandlerRegistry {

    private Map<ActionType, ActionHandler> registeredHandlers;

    private Activity context;

    public TeaserActionHandlerRegistry(Activity context) {
        this.context = context;

        if (registeredHandlers == null)
            registeredHandlers = new HashMap<ActionType, ActionHandler>();

        registeredHandlers.put(ActionType.Link, new UrlActionHandler(context));
    }

    @Override
    public ActionHandler findHandlerFor(String actionType, String actionValue, Map<String, String> properties) {

        if (registeredHandlers.containsKey(ActionType.All))
            return registeredHandlers.get(ActionType.All);

        for (Map.Entry<ActionType, ActionHandler> entry : registeredHandlers.entrySet()) {
            if (entry.getValue().canHandle(actionType, actionValue, properties))
                return entry.getValue();
        }

        return new NoActionHandler();

    }


    public void registerHandler(ActionType type, ActionHandler handler) {
        this.registeredHandlers.put(type, handler);
    }

    @Override
    public void trackClick(final String actionValue, final String actionType, final Map<String, String> properties) {

        Callable<Void> callback = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                HttpPost post = new HttpPost(TeaseMe.API_BASE_URL + TeaseMe.API_TRACK_COMMAND);

                post.addHeader("content-type", "application/x-www-form-urlencoded");

                JsonObject object = new JsonObject();

                try {
                    object.addProperty("actionType", actionType);

                    object.addProperty("actionValue", actionValue);

                    for (Map.Entry<String, String> entry : properties.entrySet()) {
                        object.addProperty(entry.getKey(), entry.getValue());
                    }

                    StringEntity params = new StringEntity(object.toString());

                    post.setEntity(params);

                    new DefaultHttpClient().execute(post);
                } catch (UnsupportedEncodingException e1) {
                } catch (IOException e) {

                } finally {
                    TeaseMe.getInstance().getHttpClient().getConnectionManager().closeExpiredConnections();

                }
                return null;
            }
        };

        new BasicAsynkTask<Void>(callback).execute();


    }

}
