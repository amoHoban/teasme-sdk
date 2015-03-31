package net.netm.apps.libs.teaseMe.webview.services.impl;

import java.util.Map;

import net.netm.apps.libs.teaseMe.TeaseMe;
import net.netm.apps.libs.teaseMe.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseMe.utils.Utils;
import net.netm.apps.libs.teaseMe.webview.services.WebViewTeasersSource;

/**
 * Created by ahoban on 27.03.15.
 */
public class DefaultWebviewTeasersSource implements WebViewTeasersSource {


    private final Long screenId;
    private final Long templateId;
    private final Map<String, String> params;

    private TeasersLoadedCallback teasersLoadedCallback;

    public DefaultWebviewTeasersSource(Long screenId, Long templateId, Map<String, String> params) {

        this.screenId = screenId;
        this.templateId = templateId;
        this.params = params;
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

        if (params == null || params.isEmpty()) return url;

        String parameters = Utils.urlEncodeUTF8(params);

        if (templateId != null)
            url += "&" + parameters;

        else
            url += "?" + parameters;

        return url;


    }

    @Override
    public Long getScreenId() {
        return this.screenId;
    }


}
