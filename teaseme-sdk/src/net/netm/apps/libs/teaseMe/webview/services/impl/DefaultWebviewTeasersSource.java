package net.netm.apps.libs.teaseme.webview.services.impl;

import android.util.Log;

import net.netm.apps.libs.teaseme.TeaseMe;
import net.netm.apps.libs.teaseme.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseme.utils.Utils;
import net.netm.apps.libs.teaseme.webview.services.WebViewTeasersSource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ahoban on 27.03.15.
 */
public class DefaultWebviewTeasersSource implements WebViewTeasersSource {


    private final Long screenId;
    private final Long templateId;
    private final Map<String, String> params = new HashMap<String, String>();

    private TeasersLoadedCallback teasersLoadedCallback;

    public DefaultWebviewTeasersSource(Long screenId, Long templateId, Map<String, String> params) {

        this.screenId = screenId;
        this.templateId = templateId;

        if (params != null)
            this.params.putAll(params);

        this.params.put(TeaseMe.API_KEY, TeaseMe.getInstance().getApiKey());
    }

    private static String getBasePath() {

        return TeaseMe.webViewUrl();
    }

    @Override
    public String getWebViewUrl() {

        String url = getBasePath() + screenId;

        if (templateId != null) {
            url += "?template=" + templateId;
        }

        Log.e("PARAMS", "PARAMS " + params.toString());

        String parameters = Utils.urlEncodeUTF8(params);

        if (templateId != null) url += "&" + parameters;

        else url += "?" + parameters;

        return url;


    }

    @Override
    public Long getScreenId() {
        return this.screenId;
    }


}
