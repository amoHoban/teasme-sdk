package net.netm.apps.libs.teaseme.listview.services.impl;

import com.squareup.picasso.Transformation;

import net.netm.apps.libs.teaseme.models.Teaser;

import java.util.List;

/**
 * Created by ahoban on 01.04.15.
 */
public interface ImageTransformer {

    public Transformation transformItem(Teaser teaer, int position, List<? extends Transformation> transformations);
}
