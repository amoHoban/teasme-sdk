package net.netm.apps.libs.teaseme.services.impl;

import net.netm.apps.libs.teaseme.models.Teaser;
import net.netm.apps.libs.teaseme.services.ItemLayoutMapper;
import android.content.Context;

import com.squareup.picasso.Transformation;

/**
 * Created by ahoban on 26.03.15.
 */
public class DefaultItemLayoutMapper implements ItemLayoutMapper {

    private Context context;

    public DefaultItemLayoutMapper(Context context) {
        this.context = context;
    }

    /**
     * {@inheritDoc}
     * @param item
     * @param position
     */
    @Override
    public Integer getItemLayoutId(Teaser item, int position) {
        return context.getResources().getIdentifier("default_teaser_list_item", "layout", context.getPackageName());

    }

    /**
     * {@inheritDoc}
     * @param item
     * @param position
     */
    @Override
    public Integer getImageViewId(Teaser item, int position) {
        return null;

    }

    /**
     * {@inheritDoc}
     * @param item
     * @param position
     */
    @Override
    public Integer getContentViewId(Teaser item, int position) {
        return null;

    }

    /**
     * {@inheritDoc}
     * @param item
     * @param position
     */
    @Override
    public Transformation getImageTransformationFor(Teaser item, int position) {
        return null;
    }

}
