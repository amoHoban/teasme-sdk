package net.netm.apps.libs.teaseMe.services.impl;

import net.netm.apps.libs.teaseMe.models.Teaser;
import net.netm.apps.libs.teaseMe.services.ItemLayoutMapper;
import android.content.Context;

/**
 * Created by ahoban on 26.03.15.
 */
public class DefaultItemLayoutMapper implements ItemLayoutMapper {

    private Context context;

    public DefaultItemLayoutMapper(Context context) {
        this.context = context;
    }

    @Override
    public Integer getItemLayoutId(Teaser item, int position) {
        return context.getResources().getIdentifier("default_teaser_list_item", "layout", context.getPackageName());

    }

    @Override
    public Integer getImageViewId(Teaser item, int position) {
        return null;

    }

    @Override
    public Integer getContentViewId(Teaser item, int position) {
        return null;

    }
}
