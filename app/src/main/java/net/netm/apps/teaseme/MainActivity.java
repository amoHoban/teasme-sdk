package net.netm.apps.teaseme;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.netm.apps.libs.teaseMe.TeaseMe;
import net.netm.apps.libs.teaseMe.handlers.ActionHandler;
import net.netm.apps.libs.teaseMe.handlers.ActionType;
import net.netm.apps.libs.teaseMe.handlers.impl.TeaserActionHandlerRegistry;
import net.netm.apps.libs.teaseMe.listview.services.impl.ListViewBinder;
import net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration;
import net.netm.apps.libs.teaseMe.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseMe.webview.WebViewScreenConfiguration;
import net.netm.apps.libs.teaseMe.webview.services.impl.WebViewBinder;

import java.util.Map;


public class MainActivity extends ActionBarActivity implements TeasersLoadedCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TeaseMe.initialize(this.getApplicationContext(), "", "");

        ListViewScreenConfiguration gridConfig = new ListViewScreenConfiguration(this, 36L, findViewById(R.id.gridView1));
        ListViewBinder binder = new ListViewBinder(gridConfig);

        /**
         * web view with custom click handler
         */

        WebViewScreenConfiguration web = new WebViewScreenConfiguration(this, 30L, findViewById(R.id.webView1), 6L);

        TeaserActionHandlerRegistry handler = new TeaserActionHandlerRegistry(this);
        handler.registerHandler(ActionType.All, new ActionHandler() {
            @Override
            public boolean canHandle(String actionType) {
                return true;
            }

            @Override
            public void handle(String actionType, String actionValue) {

                Toast.makeText(MainActivity.this, "Tesst toast " + actionType + " " + actionValue, Toast.LENGTH_SHORT);
            }

            @Override
            public void handle(String actionType, String actionValue, Map<String, String> options) {
                Toast.makeText(MainActivity.this, "Tesst toast " + actionType + " " + actionValue + options.toString(), Toast.LENGTH_SHORT);

            }
        });
        WebViewBinder binder1 = new WebViewBinder.Builder(web).withHandlerRegistry(handler).build();


        /**
         * webview 2
         */

        /**
         * web view with custom click handler
         */

        WebViewScreenConfiguration web2 = new WebViewScreenConfiguration(this, 36L, findViewById(R.id.webView2), 6L);


        WebViewBinder binder2 = new WebViewBinder.Builder(web2).build();
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
