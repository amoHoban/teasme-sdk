package net.netm.apps.libs.teaseme.services;

import com.squareup.picasso.Transformation;

import net.netm.apps.libs.teaseme.models.Teaser;

/**
 * Created by ahoban on 26.03.15.
 */
public interface ItemLayoutMapper {

    /**
     * return the layout id
     *
     * @param item     clicked Teaser item in the {@link android.widget.AbsListView} AbsListView
     * @param position positon in list
     * @return returns the layout id
     */
    public Integer getItemLayoutId(Teaser item, int position);

    /**
     * id of the imageView usually using R.id.yourResource
     *
     * @param item
     * @param position
     * @return
     */
    public Integer getImageViewId(Teaser item, int position);

    /**
     * if its not an image teaser, specify the layout for the content teaser here (e.g. a TextView)
     *
     * @param item
     * @param position
     * @return
     */
    public Integer getContentViewId(Teaser item, int position);

    /**
     * overrides other transformations!
     *
     * @param item     teaser containing image and its aspect ratio, so you can apply custom transformations based on aspect ratio
     * @param position
     */
    public Transformation getImageTransformationFor(Teaser item, int position);

}
