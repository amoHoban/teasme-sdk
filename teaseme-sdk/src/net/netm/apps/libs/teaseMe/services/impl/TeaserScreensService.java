package net.netm.apps.libs.teaseme.services.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.netm.apps.libs.teaseme.TeaseMe;
import net.netm.apps.libs.teaseme.models.FilteredScreen;
import net.netm.apps.libs.teaseme.utils.UserAgentUtils;
import net.netm.apps.libs.teaseme.utils.Utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * Created by ahoban on 26.03.15.
 */
public class TeaserScreensService {

    private final Long requestedScreenId;
    private final Map<String, String> params;
    private final Gson gson;
    private final Context context;

    private String userAgent;
    private Exception exception;
    private FilteredScreen screen;
    private final TeaserScreensSqliteSource sqliteSource;

    final static String HEADER_LAST_SCREEN_UPDATE = "Last-Screen-Update";

    public TeaserScreensService(Context context, long screenId, Map<String, String> params) {
        this.requestedScreenId = screenId;
        this.params = params;
        this.userAgent = UserAgentUtils.getDefaultUserAgentString(context);
        this.context = context;
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        gson = builder.create();

        this.sqliteSource = new TeaserScreensSqliteSource(context);

    }

    public TeaserScreensService(Context context, long requestedScreenId, Map<String, String> params, String userAgent) {
        this(context, requestedScreenId, params);
        this.userAgent = userAgent;
    }


    public FilteredScreen loadScreens() {

        String url = buildJSONScreenUrl(requestedScreenId);

        FilteredScreen cachedVersion = getCachedVersionIfNoUpdate(url);

        if (cachedVersion != null) {
            Log.d(this.getClass().getPackage().getName(), "Found screen " + requestedScreenId + " in db, returning that one");
            return cachedVersion;
        }

        HttpGet get = new HttpGet(url);

        if (userAgent != null)
            get.setHeader("User-Agent", userAgent);

        Log.i(this.getClass().getPackage().getName(), "Trying to fetch screen from url " + url);

        try {

            HttpResponse response = getConfig().getHttpClient().execute(get);

            StringBuilder responseStrBuilder = Utils.responseToStringBuilder(response);

            JSONObject content = new JSONObject(responseStrBuilder.toString());

            JSONObject screenJSON = (JSONObject) content.get("content");

            this.storeToDb(screenJSON, requestedScreenId);

            Log.d(this.getClass().getPackage().getName(), "loaded content " + screenJSON.toString() + " url " + url);

            Type arrayListType = new TypeToken<FilteredScreen>() {
            }.getType();

            screen = gson.fromJson(screenJSON.toString(), arrayListType);

            Log.d(this.getClass().getPackage().getName(), "loaded screen " + screen.toString());

        } catch (IOException e) {
            this.exception = e;
            screen = null;
        } catch (JSONException j) {
            this.exception = j;
            screen = null;
        } finally {
            sqliteSource.close();
        }

        return screen;
    }


    private void storeToDb(JSONObject screen, long requestedScreenId) {
        this.sqliteSource.createScreen(screen, requestedScreenId);
    }


    private String buildJSONScreenUrl(Long screenId) {

        String url = TeaseMe.jsonUrl() + screenId;

        if (params == null || params.isEmpty()) return url;

        url += "?" + Utils.urlEncodeUTF8(params);

        return url;

    }


    public FilteredScreen getCachedVersionIfNoUpdate(String url) {

        FilteredScreen cachedVersion = null;

        String screenString = sqliteSource.getScreen(Utils.safeLongToInt(requestedScreenId));

        List<String> screens = sqliteSource.getAllScreens();

        Log.e(this.getClass().getPackage().getName(), "All Screens : " + screens);


        if (screenString != null) {

            cachedVersion = getGson().fromJson(screenString, FilteredScreen.class);

        } else {
            // nothing in database
            Log.d(this.getClass().getPackage().getName(), "Nothing found in database for screen " + requestedScreenId);
            return null;
        }

        // HEAD the screen and get last update to compare to local version

        HttpHead head = new HttpHead(url.replace(TeaseMe.API_JSON_COMMAND, "/"));

        Date remoteLastModifiedDate = new Date(0);

        if (userAgent != null)
            head.setHeader("User-Agent", userAgent);

        try {
            Log.d(this.getClass().getPackage().getName(), "Fetching remote modified date now");

            HttpResponse response = getConfig().getHttpClient().execute(head);

            Header[] headers = response.getHeaders(HEADER_LAST_SCREEN_UPDATE);

            if (headers.length > 0)
                remoteLastModifiedDate = new Date(Long.parseLong(headers[0].getValue()));

            if (cachedVersion.getAccumulatedLastModifiedDate().before(remoteLastModifiedDate))
            {
                Log.d(this.getClass().getPackage().getName(), "Cached version is too old, differene: " + (remoteLastModifiedDate.getTime() - cachedVersion.getAccumulatedLastModifiedDate().getTime()));
                sqliteSource.deleteScreen(requestedScreenId);
                return null;
            }

            return cachedVersion;

        } catch (IOException io) {
            return null;

        }
    }

    private TeaseMe getConfig() {

        return TeaseMe.getInstance();
    }

    public FilteredScreen getScreen() {
        return this.screen;
    }

    public Exception getException() {
        return exception;
    }

    private Gson getGson() {
        return gson;
    }
}
