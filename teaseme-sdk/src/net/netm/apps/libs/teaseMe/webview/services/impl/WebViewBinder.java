package net.netm.apps.libs.teaseMe.webview.services.impl;

import net.netm.apps.libs.teaseMe.TeaseMe;
import net.netm.apps.libs.teaseMe.exceptions.WebViewScreenLoadException;
import net.netm.apps.libs.teaseMe.handlers.HandlerRegistry;
import net.netm.apps.libs.teaseMe.handlers.impl.TeaserActionHandlerRegistry;
import net.netm.apps.libs.teaseMe.services.ScreenBinder;
import net.netm.apps.libs.teaseMe.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseMe.utils.BasicScreenConfiguration;
import net.netm.apps.libs.teaseMe.webview.WebViewScreenConfiguration;
import net.netm.apps.libs.teaseMe.webview.services.WebViewTeasersSource;
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

    private final HandlerRegistry handlerRegistry;

    private final WebViewTeasersSource teaserSource;

    private final WebViewScreenConfiguration configuration;

    private final WebView view;

    private WebViewBinder(Builder builder) {

        configuration = builder.configuration;

        context = configuration.getContext();

        handlerRegistry = builder.handlerRegistry;

        teaserSource = new DefaultWebviewTeasersSource(configuration.getScreenId(), configuration.getTemplateId(), configuration.getParams());

        view = (WebView) configuration.getView();

        this.bindView();
    }


    public void bindView() {

        WebSettings webSettings = view.getSettings();

        webSettings.setJavaScriptEnabled(true);

        view.addJavascriptInterface(new TeaseMeJavaScriptInterface(context, handlerRegistry), TeaseMeJavaScriptInterface.JAVASCRIPT_INTERFACE_NAME);
        
        GingerbreadJSFix fix = new GingerbreadJSFix();

        fix.fixWebViewJSInterface(view, new TeaseMeJavaScriptInterface(context, handlerRegistry), TeaseMeJavaScriptInterface.JAVASCRIPT_INTERFACE_NAME, "_gbjsfix:");
        
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

                    ((TeasersLoadedCallback) context).teasersErrored(teaserSource.getScreenId(), view,
                            new WebViewScreenLoadException("WebView failed to load screen.", errorCode, failingUrl, teaserSource.getScreenId()));

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
        private HandlerRegistry handlerRegistry;
        private final WebViewScreenConfiguration configuration;

        public Builder(WebViewScreenConfiguration configuration) {
            this.configuration = configuration;
        }


        /**
         * <p>add a handlerRegistry for handling teaser actions on your own,
         * see {@link net.netm.apps.libs.teaseMe.handlers.impl.TeaserActionHandlerRegistry#registerHandler(net.netm.apps.libs.teaseMe.handlers.ActionType, net.netm.apps.libs.teaseMe.handlers.ActionHandler)} TeaserActionHandlerRegistry</p>
         *
         * @param handlerRegistry
         * @return
         */
        public Builder withHandlerRegistry(HandlerRegistry handlerRegistry) {
            this.handlerRegistry = handlerRegistry;
            return this;
        }


        public WebViewBinder build() {

            if (this.handlerRegistry == null)
                this.handlerRegistry = new TeaserActionHandlerRegistry(configuration.getContext());

            return new WebViewBinder(this);
        }
    }
}
