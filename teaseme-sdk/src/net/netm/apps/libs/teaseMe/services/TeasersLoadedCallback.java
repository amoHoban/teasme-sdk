package net.netm.apps.libs.teaseMe.services;

import android.view.View;

/**
 * Created by ahoban on 27.03.15.
 */
public interface TeasersLoadedCallback {

    /**
     * <p>triggered when teasers finished loading
     * in case of webview when the webview finished loading the url
     * in case of AbsListView when the adapter finished populating
     * the items from json </p>
     *
     * @param screenId id of the screen which was bound to the view
     * @param AbsListViewWebView parameter will be the loaded webview or the loaded AbsListView with populated see {@link net.netm.apps.libs.teaseMe.components.TeaserListAdapter}
     */
    public void teasersLoaded(Long screenId, View AbsListViewWebView);

    /**
     <p>triggered when teasers finished loading
     * in case of webview when the page has a bad http status, see {@link android.webkit.WebViewClient#onReceivedError(android.webkit.WebView, int, String, String)}
     * in case of AbsListView when the adapter finished populating
     * the items from json </p>
     *
     * @param screenId id of the screen which was bound to the view
     * @param AbsListViewWebView parameter will be the loaded webview or the loaded AbsListView with populated {@link net.netm.apps.libs.teaseMe.components.TeaserListAdapter}
     * @param e the cause of the error
     */
    public void teasersErrored(Long screenId, View AbsListViewWebView, Throwable e);


}
