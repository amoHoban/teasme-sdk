package net.netm.apps.libs.teaseme.listview.services.impl;

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

import com.squareup.picasso.Transformation;

import net.netm.apps.libs.teaseme.components.TeaserListAdapter;
import net.netm.apps.libs.teaseme.handlers.ActionHandler;
import net.netm.apps.libs.teaseme.handlers.HandlerRegistry;
import net.netm.apps.libs.teaseme.handlers.impl.TeaserActionHandlerRegistry;
import net.netm.apps.libs.teaseme.models.FilteredScreen;
import net.netm.apps.libs.teaseme.models.Teaser;
import net.netm.apps.libs.teaseme.services.ItemLayoutMapper;
import net.netm.apps.libs.teaseme.services.ScreenBinder;
import net.netm.apps.libs.teaseme.services.TeasersLoadedCallback;
import net.netm.apps.libs.teaseme.services.impl.DefaultItemLayoutMapper;
import net.netm.apps.libs.teaseme.services.impl.TeaserScreensService;
import net.netm.apps.libs.teaseme.utils.BasicScreenConfiguration;
import net.netm.apps.libs.teaseme.utils.Utils;

import java.util.List;

/**
 * Created by ahoban on 26.03.15.
 */
public class ListViewBinder implements ScreenBinder<AbsListView> {


    private final TeaserScreensService teaserSource;
    private final LayoutInflater inflater;
    private final ItemLayoutMapper itemLayoutMapper;
    private final Context context;
    private final net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration configuration;
    private final AbsListView view;

    /**
     * List if {@link Transformation} transformations to be applied to all images
     */
    private List<? extends Transformation> transformers;
    private HandlerRegistry handlerRegistry;
    private ActionHandler actionHandler;

    public ListViewBinder(net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration configuration) {
        this.configuration = configuration;
        this.view = (AbsListView) configuration.getView();
        this.context = configuration.getContext();
        this.teaserSource = new TeaserScreensService(context, configuration.getScreenId(), configuration.getParams(), configuration.getUserAgent());
        this.itemLayoutMapper = new DefaultItemLayoutMapper(context);
        this.inflater = LayoutInflater.from(context);
        this.handlerRegistry = new TeaserActionHandlerRegistry(configuration.getContext());
        this.bindView();
    }

    private ListViewBinder(Builder builder) {

        itemLayoutMapper = builder.itemLayoutMapper;
        configuration = builder.configuration;
        context = configuration.getContext();
        actionHandler = builder.actionHandler;
        view = (AbsListView) configuration.getView();
        teaserSource = new TeaserScreensService(context, configuration.getScreenId(), configuration.getParams(), configuration.getUserAgent());
        inflater = LayoutInflater.from(context);
        this.handlerRegistry = new TeaserActionHandlerRegistry(configuration.getContext());
        if (builder.imageTransformers != null)
            this.transformers = builder.imageTransformers;

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
        private final net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration configuration;
        private ActionHandler actionHandler;
        private List<? extends Transformation> imageTransformers;

        public Builder(net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration configuration) {
            this.configuration = configuration;
        }

        /**
         * <p>
         * add your own layout mapper to determine layout for all teaser layouts, teaser images and teaser content </p>
         * @param itemLayoutMapper
         * @return
         */
        public Builder withItemLayoutMapper(ItemLayoutMapper itemLayoutMapper) {
            this.itemLayoutMapper = itemLayoutMapper;
            return this;
        }

        /**
         * <p>
         * add a list of {@link com.squareup.picasso.Transformation} image transformations to be applied to all images</p>
         * @param transformers
         * @return
         */
        public Builder withImageTransformers(List<? extends Transformation> transformers) {
            this.imageTransformers = transformers;
            return this;
        }

        /**
         * <p>add a custom handler for handling teaser actions on your own, return true in the handler
         * to prevent other handlers from getting triggered</p>
         *
         **/
        public Builder withCustomActionHandler(ActionHandler actionHandler) {
            this.actionHandler = actionHandler;
            return this;
        }


        public ListViewBinder build() {

            if (this.itemLayoutMapper == null)
                this.itemLayoutMapper = new DefaultItemLayoutMapper(configuration.getContext());


            return new ListViewBinder(this);
        }
    }


    private void createAdapter(final FilteredScreen screen) {

        TeaserListAdapter adapter = new TeaserListAdapter(screen, itemLayoutMapper, configuration, transformers);

        ((AdapterView) view).setAdapter(adapter);

        adapter.notifyDataSetChanged();

        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Teaser item = null;

                item = screen.getTeasers().get(position);

                if (item == null)
                    return;

                handlerRegistry.trackClick(item.getActionType(), item.getActionValue(), item.getProperties());

                if (actionHandler != null) {
                    if (actionHandler.canHandle(item.getActionType(), item.getActionValue(), item.getProperties())) {

                        if (actionHandler.handle(item.getActionType(), item.getActionValue(), item.getProperties())) {
                            return;
                        }
                    }
                }

                handlerRegistry.findHandlerFor(item.getActionType(), item.getActionValue(), item.getProperties()).handle(item.getActionType(), item.getActionValue(), item.getProperties());

            }
        });
    }

    private void notifyActivity(final FilteredScreen screen, final View v) {

        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {

                if (screen == null) {
                    ((TeasersLoadedCallback) context).teasersErrorred(configuration.getScreenId(), v, teaserSource.getException());
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
    private void addLoadersForNewApi(final Context context, final net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration configuration) {
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

    private void addLoadersForOldApi(final Context context, final net.netm.apps.libs.teaseme.listview.services.impl.ListViewScreenConfiguration configuration) {

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
