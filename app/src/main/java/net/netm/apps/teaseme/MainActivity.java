package net.netm.apps.teaseme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.netm.apps.libs.teaseme.TeaseMe;
import net.netm.apps.libs.teaseme.handlers.ActionHandler;
import net.netm.apps.libs.teaseme.listview.services.impl.ListViewBinder;
import net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration;
import net.netm.apps.libs.teaseme.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseme.webview.WebViewScreenConfiguration;
import net.netm.apps.libs.teaseme.webview.services.impl.WebViewBinder;

import java.util.Map;


public class MainActivity extends ActionBarActivity implements TeasersLoadedCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TeaseMe.initialize(this.getApplicationContext(), "", "");

        ListViewScreenConfiguration gridConfig = new ListViewScreenConfiguration(this, 14L, findViewById(R.id.gridView1));
        ListViewBinder binder = new ListViewBinder(gridConfig);

        /**
         * web view with custom click handler
         */

        WebViewScreenConfiguration web = new WebViewScreenConfiguration(this, 30L, findViewById(R.id.webView1), 5L);

        ActionHandler handler = new ActionHandler() {

            @Override
            public boolean canHandle(String actionType, String actionValue, Map<String, String> properties) {
                return true;
            }

            @Override
            public boolean handle(String actionType, String actionValue, Map<String, String> properties) {
                Toast.makeText(MainActivity.this, "MY TOAST " + actionValue, Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        WebViewBinder binder1 = new WebViewBinder.Builder(web).withCustomActionHandler(handler).build();

        /**
         * webview 2
         */

        /**
         * web view with custom click handler
         */

        WebViewScreenConfiguration web2 = new WebViewScreenConfiguration(this, 36L, findViewById(R.id.webView2), 6L);


        WebViewBinder binder2 = new WebViewBinder.Builder(web2).build();


        /**
         * Gridview with all custom options
         */
    /*
        Map<String, String> params = new HashMap<String, String>();

        params.put(TeaserFilterParameter.FSK.name(), "18");


        ListViewScreenConfiguration lv2 = new ListViewScreenConfiguration(this, 36L, findViewById(R.id.gridView1), params);

        ListViewBinder binder3 = new ListViewBinder.Builder(lv2).withCustomActionHandler(new ActionHandler() {
            @Override
            public boolean canHandle(String actionType, String actionValue, Map<String, String> properties) {
                return false;
            }

            @Override
            public boolean handle(String actionType, String actionValue, Map<String, String> properties) {
                Toast.makeText(MainActivity.this, "MY TOAST " + actionValue, Toast.LENGTH_SHORT).show();
                return true;
            }
        }).withImageTransformers(new ArrayList<Transformation>()).withItemLayoutMapper(new ItemLayoutMapper() {
            @Override
            public Integer getItemLayoutId(Teaser item, int position) {
                return R.layout.default_teaser_list_item;
            }

            @Override
            public Integer getImageViewId(Teaser item, int position) {
                return R.id.teaser_item_image;
            }

            @Override
            public Integer getContentViewId(Teaser item, int position) {
                return R.id.teaser_item_content;
            }

            @Override
            public Transformation getImageTransformationFor(Teaser item, int position) {
                if (item.getImageAspectRatio() > 1)
                    return new CropSquareTransformation();
                else if (position > 2)
                    return new CircleTransformation();
                return null;
            }
        }).build();
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void teasersLoaded(Long screenId, View AbsListViewWebView) {

    }

    @Override
    public void teasersErrored(Long screenId, View AbsListViewWebView, Throwable e) {

    }


}
