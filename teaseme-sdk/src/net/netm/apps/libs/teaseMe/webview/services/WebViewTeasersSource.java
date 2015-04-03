package net.netm.apps.libs.teaseme.webview.services;

/**
 * Created by ahoban on 26.03.15.
 */
public interface WebViewTeasersSource {

    /**
     * exposed method to always have the url of the webview
     *
     * @return the url of the webview
     */
    public String getWebViewUrl();


    /**
     * @return the screen id loaded in the webview
     */
    public Long getScreenId();

}
