package net.netm.apps.libs.teaseMe.services;

import net.netm.apps.libs.teaseMe.models.Teaser;

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

}
