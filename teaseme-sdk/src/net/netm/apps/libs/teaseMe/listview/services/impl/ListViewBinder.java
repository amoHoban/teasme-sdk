package net.netm.apps.libs.teaseMe.listview.services.impl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import net.netm.apps.libs.teaseMe.components.TeaserListAdapter;
import net.netm.apps.libs.teaseMe.handlers.ActionHandler;
import net.netm.apps.libs.teaseMe.handlers.HandlerRegistry;
import net.netm.apps.libs.teaseMe.handlers.impl.TeaserActionHandlerRegistry;
import net.netm.apps.libs.teaseMe.models.FilteredScreen;
import net.netm.apps.libs.teaseMe.models.Teaser;
import net.netm.apps.libs.teaseMe.services.ItemLayoutMapper;
import net.netm.apps.libs.teaseMe.services.ScreenBinder;
import net.netm.apps.libs.teaseMe.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseMe.services.impl.DefaultItemLayoutMapper;
import net.netm.apps.libs.teaseMe.services.impl.TeaserScreensService;
import net.netm.apps.libs.teaseMe.utils.BasicScreenConfiguration;
import net.netm.apps.libs.teaseMe.utils.Utils;

/**
 * Created by ahoban on 26.03.15.
 */
public class ListViewBinder implements ScreenBinder<AbsListView> {


    private final TeaserScreensService teaserSource;
    private final LayoutInflater inflater;
    private final ItemLayoutMapper itemLayoutMapper;
    private final Context context;
    private final net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration configuration;
    private final AbsListView view;

    private final HandlerRegistry handlerRegistry;

    public ListViewBinder(net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration configuration) {
        this.configuration = configuration;
        this.view = (AbsListView) configuration.getView();
        this.context = configuration.getContext();
        this.teaserSource = new TeaserScreensService(context, configuration.getScreenId(), configuration.getParams(), configuration.getUserAgent());
        this.itemLayoutMapper = new DefaultItemLayoutMapper(context);
        this.inflater = LayoutInflater.from(context);
        this.handlerRegistry = new TeaserActionHandlerRegistry((Activity) context);
        this.bindView();
    }

    private ListViewBinder(Builder builder) {

        itemLayoutMapper = builder.itemLayoutMapper;
        configuration = builder.configuration;
        context = configuration.getContext();
        handlerRegistry = builder.handlerRegistry;
        view = (AbsListView) configuration.getView();
        teaserSource = new TeaserScreensService(context, configuration.getScreenId(), configuration.getParams(), configuration.getUserAgent());
        inflater = LayoutInflater.from(context);
        this.bindView();
    }

    public void bindView() {

        // Support both fragmentActivity and Activity, solving api level issues

        if (context instanceof FragmentActivity || Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
            addLoadersForOldApi(context, configuration);
        } else {
            addLoadersForNewApi(context, configuration);


        }

    }

    @Override
    public BasicScreenConfiguration getConfiguration() {
        return configuration;
    }


    public static final class Builder {

        private ItemLayoutMapper itemLayoutMapper;
        private final net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration configuration;
        private HandlerRegistry handlerRegistry;

        public Builder(net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration configuration) {
            this.configuration = configuration;
        }

        public Builder withItemLayoutMapper(ItemLayoutMapper itemLayoutMapper) {
            this.itemLayoutMapper = itemLayoutMapper;
            return this;
        }

        public Builder withHandlerRegistry(HandlerRegistry handlerRegistry) {
            this.handlerRegistry = handlerRegistry;
            return this;
        }


        public ListViewBinder build() {

            if (this.handlerRegistry == null)
                this.handlerRegistry = new TeaserActionHandlerRegistry(configuration.getContext());

            if (this.itemLayoutMapper == null)
                this.itemLayoutMapper = new DefaultItemLayoutMapper(configuration.getContext());

            return new ListViewBinder(this);
        }
    }


    private void createAdapter(final FilteredScreen screen) {

        TeaserListAdapter adapter = new TeaserListAdapter(screen, itemLayoutMapper, configuration);

        ((AdapterView) view).setAdapter(adapter);

        adapter.notifyDataSetChanged();

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActionHandler handler = null;

                Teaser item = null;

                try {
                    item = screen.getTeasers().get(position);

                    handlerRegistry.trackClick(item.getActionType(), item.getActionValue());

                    handlerRegistry.findHandlerFor(item.getActionType(), item.getActionValue()).handle(item.getActionType(), item.getActionValue());
                } catch (NullPointerException e) {

                    // request teaser by id again?

                }

            }
        });
    }

    private void notifyActivity(final FilteredScreen screen, final View v) {

        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {

                if (screen == null) {
                    ((TeasersLoadedCallback) context).teasersErrored(configuration.getScreenId(), v, teaserSource.getException());
                    return;
                }
                /**
                 * notifiy activity
                 */
                ((TeasersLoadedCallback) context).teasersLoaded(screen.getId(), v);


            }
        });
    }

    @TargetApi(11)
    private void addLoadersForNewApi(final Context context, final net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration configuration) {
        ((Activity) context).getLoaderManager().initLoader(Utils.safeLongToInt(configuration.getScreenId()), null, new android.app.LoaderManager.LoaderCallbacks<FilteredScreen>() {
            @Override
            public android.content.Loader<FilteredScreen> onCreateLoader(int id, Bundle args) {
                return new AsyncTaskLoader<FilteredScreen>(context) {
                    @Override
                    public FilteredScreen loadInBackground() {
                        return teaserSource.loadScreens();
                    }
                };
            }

            @Override
            public void onLoadFinished(android.content.Loader<FilteredScreen> loader, FilteredScreen data) {
                ListViewBinder.this.createAdapter(data);
                ListViewBinder.this.notifyActivity(data, view);
            }

            @Override
            public void onLoaderReset(android.content.Loader<FilteredScreen> loader) {

            }
        }).forceLoad();
    }

    private void addLoadersForOldApi(final Context context, final net.netm.apps.libs.teaseMe.listview.services.impl.ListViewScreenConfiguration configuration) {

        ((FragmentActivity) context).getSupportLoaderManager().initLoader(Utils.safeLongToInt(configuration.getScreenId()), null, new LoaderManager.LoaderCallbacks<FilteredScreen>() {
            @Override
            public Loader<FilteredScreen> onCreateLoader(int id, Bundle args) {

                return new android.support.v4.content.AsyncTaskLoader<FilteredScreen>(context) {

                    @Override
                    public FilteredScreen loadInBackground() {
                        return teaserSource.loadScreens();
                    }
                };
            }

            @Override
            public void onLoadFinished(Loader<FilteredScreen> loader, final FilteredScreen data) {
                ListViewBinder.this.createAdapter(data);
                ListViewBinder.this.notifyActivity(data, view);
            }

            @Override
            public void onLoaderReset(Loader<FilteredScreen> loader) {

            }
        }).forceLoad();
    }


}
