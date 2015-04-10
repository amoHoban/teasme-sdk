package net.netm.apps.libs.teaseme;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.util.Base64;


import java.util.UUID;

/**
 * Created by ahoban on 27.03.15.
 */
public class TeaseMe {

    public final static String API_VERSION = "v1";

    public final static String API_NAMESPACE = "api";

    public final static String API_BASE_URL = "http://teaseradmin.pec.test.net-m.net/teaseradmin";

    public final static String API_PATH = "/screens";

    public final static String API_RENDER_COMMAND = "/render/";

    public final static String API_JSON_COMMAND = "/json/";

    public final static String API_TRACK_COMMAND ="/track/";


    private static final int HTTP_CLIENT_TIMEOUT_CONNECTION = 5000;

    private static final int HTTP_CLIENT_TIMEOUT_SOCKET = 5000;

    public static final String API_KEY = "apiKey" ;

    private static TeaseMe mInstance = null;
    private HttpClient httpClient;
    private Context context;
    private String apiKey;

    /**
     * private constructor, singleton
     *
     * @param context
     * @param apiKey
     */
    private TeaseMe(Context context, String apiKey) {
        this();
        this.context = context;
        this.apiKey = apiKey;
    }

    /**
     * general initialization
     */
    private TeaseMe() {

        HttpParams params = new BasicHttpParams();

        HttpConnectionParams.setConnectionTimeout(params, HTTP_CLIENT_TIMEOUT_CONNECTION);

        HttpConnectionParams.setSoTimeout(params, HTTP_CLIENT_TIMEOUT_SOCKET);

        SchemeRegistry schemeRegistry = new SchemeRegistry();

        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

        final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();

        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));

        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

        httpClient = new DefaultHttpClient(cm, params);
    }

    /**
     * @return renderUrl for screens, slash NOT included
     */
    public static String apiUrl() {

        return API_BASE_URL + "/" + API_NAMESPACE + "/" + API_VERSION;
    }

    /**
     * @return render Url for screens, slash NOT included
     */
    public static String webViewUrl() {

        return API_BASE_URL + API_PATH + API_RENDER_COMMAND;
    }

    /**
     * @return renderUrl for screens, slash included
     */
    public static String jsonUrl() {

        return API_BASE_URL + API_PATH + API_JSON_COMMAND;
    }

    /**
     * create new Instance if not available
     *
     * @param context
     * @param apiKey
     */
    public static void initialize(Context context, String apiKey) {

        validateApiKey(apiKey);

        if (mInstance == null) {
            mInstance = new TeaseMe(context, apiKey);
        }

    }

    private static void validateApiKey(String hash) {
        try {
            byte[] bytes = Base64.decode(hash,Base64.DEFAULT);
            UUID.fromString(new String(bytes));
        } catch(Exception exception) {
            throw new IllegalStateException("Invalid api key");
        }
    }

    /**
     * returns an instance, must be initialized previously
     *
     * @return
     */
    public static TeaseMe getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("TeaseMe not initialized!");
        }
        return mInstance;
    }

    /**
     *
     * @return true if requirements met
     */
    public boolean isInitialized() {
        return this.apiKey != null && this.context != null;
    }

    /**
     *
     * @return our httpClient
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     *
     * @param httpClientP set your own client
     */
    public void setHttpClient(HttpClient httpClientP) {
        httpClient = httpClientP;
    }

    /**
     *
     * @return always useful to have a context, eh?
     */
    public Context getContext() {
        return context;
    }


    public String getApiKey() {
        return apiKey;
    }
}
