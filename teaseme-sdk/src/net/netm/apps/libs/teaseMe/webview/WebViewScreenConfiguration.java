package net.netm.apps.libs.teaseme.webview;

import net.netm.apps.libs.teaseme.utils.BasicScreenConfiguration;
import android.app.Activity;
import android.view.View;

/**
 * Created by Ahmed Hoban<ahmed.hoban@net-m.de> on 29.03.15.
 *
 * Hold the configuration for teasers loaded into a webview
 *
 */
public class WebViewScreenConfiguration extends BasicScreenConfiguration {

    private Long templateId;

    /**
     *
     * @param context Activity
     * @param screenId the screen to be loaded as long
     * @param view the {@link android.webkit.WebView webView} to be used
     */
    public WebViewScreenConfiguration(Activity context, Long screenId, View view) {
        super(context, screenId,view);
    }

    /**
     * additional constructor with a templateId
     *
     * @param context (Fragment)Activity
     * @param screenId The screen id to be loaded as long
     * @param view the {@link android.webkit.WebView WebView} to be bound
     * @param templateId The template to be rendered
     */
    public WebViewScreenConfiguration(Activity context, Long screenId, View view, Long templateId) {
        super(context, screenId, view);
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
