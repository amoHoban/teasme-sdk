package net.netm.apps.libs.teaseme.webview.services.impl;

import net.netm.apps.libs.teaseme.TeaseMe;
import net.netm.apps.libs.teaseme.exceptions.WebViewScreenLoadException;
import net.netm.apps.libs.teaseme.handlers.ActionHandler;
import net.netm.apps.libs.teaseme.handlers.HandlerRegistry;
import net.netm.apps.libs.teaseme.handlers.impl.TeaserActionHandlerRegistry;
import net.netm.apps.libs.teaseme.services.ScreenBinder;
import net.netm.apps.libs.teaseme.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseme.utils.BasicScreenConfiguration;
import net.netm.apps.libs.teaseme.webview.WebViewScreenConfiguration;
import net.netm.apps.libs.teaseme.webview.services.WebViewTeasersSource;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ahoban on 26.03.15.
 *
 * handles the binding of screens to a webview
 */
public class WebViewBinder implements ScreenBinder<WebView> {

	private final Context context;

    private final WebViewTeasersSource teaserSource;

    private final WebViewScreenConfiguration configuration;

    private final WebView view;

    private HandlerRegistry handlerRegistry;

    private ActionHandler actionHandler;

    private WebViewBinder(Builder builder) {

        configuration = builder.configuration;

        context = configuration.getContext();

        handlerRegistry = new TeaserActionHandlerRegistry(configuration.getContext());

        teaserSource = new DefaultWebviewTeasersSource(configuration.getScreenId(), configuration.getTemplateId(), configuration.getParams());

        view = (WebView) configuration.getView();

        actionHandler = builder.actionHandler;

        this.bindView();
    }


    public void bindView() {

        WebSettings webSettings = view.getSettings();

        webSettings.setJavaScriptEnabled(true);

        view.addJavascriptInterface(new TeaseMeJavaScriptInterface(context, handlerRegistry, actionHandler), TeaseMeJavaScriptInterface.JAVASCRIPT_INTERFACE_NAME);
        
        GingerbreadJSFix fix = new GingerbreadJSFix();

        fix.fixWebViewJSInterface(view, new TeaseMeJavaScriptInterface(context, handlerRegistry, actionHandler), TeaseMeJavaScriptInterface.JAVASCRIPT_INTERFACE_NAME, "_gbjsfix:");
        
        view.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                ((TeasersLoadedCallback) context).teasersLoaded(teaserSource.getScreenId(), view);
            }

            /**
             * override if this isnt a render url, we dont want to render anything else into this webview
             * use a custom actionHandler to manipulate the webview
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !url.contains(TeaseMe.API_BASE_URL);

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description,String failingUrl) {
                if (failingUrl.toLowerCase().contains(TeaseMe.webViewUrl().toLowerCase())) {

                    ((TeasersLoadedCallback) context).teasersErrorred(teaserSource.getScreenId(), view, new WebViewScreenLoadException("WebView failed to load screen.", errorCode, failingUrl, teaserSource.getScreenId()));

                }
            }

        });


        view.loadUrl(teaserSource.getWebViewUrl());

    }

    @Override
    public BasicScreenConfiguration getConfiguration() {
        return configuration;
    }


    public static final class Builder {
        private final WebViewScreenConfiguration configuration;
        private ActionHandler actionHandler;

        public Builder(WebViewScreenConfiguration configuration) {
            this.configuration = configuration;
        }


        /**
         * <p>add a custom handler for handling teaser actions on your own, return true in the handler
         * to prevent other handlers from getting triggered</p>
         *
         * @param actionHandler
         * @return
         */
        public Builder withCustomActionHandler(ActionHandler actionHandler) {
            this.actionHandler = actionHandler;
            return this;
        }


        public WebViewBinder build() {
            return new WebViewBinder(this);
        }
    }
}
